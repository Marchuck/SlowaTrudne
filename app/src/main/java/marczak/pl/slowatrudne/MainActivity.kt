package marczak.pl.slowatrudne

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import marczak.pl.slowatrudne.hardwords.HardWordsFragment

class MainActivity : AppCompatActivity() {

    var bestMatchingWorld: Subject<String> = PublishSubject.create<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main, HardWordsFragment.newInstance())
                .commitAllowingStateLoss()

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 200) {
//            if (resultCode == Activity.RESULT_OK) {
//                val result = data?.getStringExtra(RecognizeSpeechActivity.TAG)
//                bestMatchingWorld.onNext(result)
//                return
//            }
//        }
//        bestMatchingWorld.onNext("")
//    }
}
