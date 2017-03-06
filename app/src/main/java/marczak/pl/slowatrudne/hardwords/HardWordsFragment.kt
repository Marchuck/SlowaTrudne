package marczak.pl.slowatrudne.hardwords

import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import com.tbruyelle.rxpermissions2.RxPermissions
import marczak.pl.slowatrudne.MainActivity
import marczak.pl.slowatrudne.R
import marczak.pl.slowatrudne.recognition.RecognizeSpeechActivity

/**
 * SlowaTrudne
 *
 *
 * Created by lukasz
 *
 *
 * Since 05.03.2017
 */
class HardWordsFragment : Fragment(), HardWordsView {

    val rxPermissions by lazy { RxPermissions(activity) }

    internal var presenter: HardWordsPresenter? = null

    fun btnClicked() {
        rxPermissions.request(RECORD_AUDIO)
                .filter { granted -> granted }
                .flatMap {
                    val intent = Intent(activity, RecognizeSpeechActivity::class.java)
                    startActivityForResult(intent, 200)
                    parentActivity().bestMatchingWorld
                }.distinct()
                .map { s -> presenter?.findHardWord(s) }
                .subscribe { s -> Log.d(TAG, s.toString()) }

    }

    var progressBar: View? = null
    var word: TextView? = null
    var hardWordMeanings: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_hard_words, container, false)
        presenter = HardWordsPresenter(this)

        hardWordMeanings = view.findViewById(R.id.hardWordMeanings) as TextView
        word = view.findViewById(R.id.hardWord) as TextView
        progressBar = view.findViewById(R.id.progressBar)

        val btn = view.findViewById(R.id.text2speech)
        btn.setOnClickListener { btnClicked() }

        val swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            swipeRefreshLayout.postDelayed({
                presenter?.requestNewHardWord()
                swipeRefreshLayout.isRefreshing = false
            }, 500)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data?.getStringExtra(RecognizeSpeechActivity.TAG)
                parentActivity().bestMatchingWorld.onNext(result)
                return
            }
        }
        parentActivity().bestMatchingWorld.onNext("")
    }

    override fun startLoad() {
        progressBar?.post {
            progressBar?.visibility = VISIBLE;
            progressBar?.alpha = 0f;
            progressBar?.animate()
                    ?.alpha(1f)
                    ?.setDuration(300)
                    ?.start()
        }
    }

    override fun endLoad() {
        progressBar?.post {
            progressBar?.animate()
                    ?.alpha(0f)
                    ?.setDuration(300)
                    ?.start()
        }
    }

    override fun showError() {
        if (view != null)
            Snackbar.make(view!!, "Internet Error occurred", Snackbar.LENGTH_SHORT).show()
    }

    override fun showHardWordMeaning(hardWord: String, meanings: List<String>) {
        word?.post {
            word?.scaleX = 0f
            word?.scaleY = 0f
            word?.text = hardWord

            word?.animate()
                    ?.setDuration(300)
                    ?.scaleX(1f)
                    ?.scaleY(1f)
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.start()

            val sb = StringBuilder()
            for (s in meanings) {
                sb.append(s).append("\n")
            }
            hardWordMeanings?.scaleY=0f
            hardWordMeanings?.scaleX=0f
            hardWordMeanings?.text = sb.toString()

            hardWordMeanings?.animate()
                    ?.setStartDelay(100)
                    ?.setDuration(300)
                    ?.scaleX(1f)
                    ?.scaleY(1f)
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.start()
        }
    }

    companion object {
        val TAG = HardWordsFragment::class.java.simpleName

        fun newInstance(): HardWordsFragment {
            val fragment = HardWordsFragment()
            return fragment
        }
    }

    fun parentActivity(): MainActivity {
        return activity as MainActivity
    }
}
