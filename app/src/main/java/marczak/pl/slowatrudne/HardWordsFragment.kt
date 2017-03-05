package marczak.pl.slowatrudne

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView

import com.tbruyelle.rxpermissions2.RxPermissions

import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*

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
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showHardWordMeaning(hardWord: String, meanings: List<String>) {
        word?.text = hardWord
        wordMeanings?.text = meanings.toString()
    }

    @BindView(R.id.text2speech)
    internal var btn: FloatingActionButton? = null

    @BindView(R.id.progressBar)
    internal var progressBar: View? = null

    @BindView(R.id.hardWord)
    internal var word: TextView? = null

    @BindView(R.id.hardWordMeanings)
    internal var wordMeanings: TextView? = null

    internal var bestMatchingWorld: Subject<String> = PublishSubject.create<String>()

    internal var presenter: HardWordsPresenter? = null;

    internal var permissions: RxPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_hard_words, container, false)
        ButterKnife.bind(this, view)
        presenter = HardWordsPresenter(this)
        return view
    }

    companion object {
        val TAG = HardWordsFragment::class.java.simpleName

        fun newInstance(): HardWordsFragment {
            val fragment = HardWordsFragment()
            return fragment
        }
    }

}
