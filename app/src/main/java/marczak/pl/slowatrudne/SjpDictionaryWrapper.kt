package marczak.pl.slowatrudne

import android.util.Log

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

/**
 * Project "TrudneSlowo"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

object SjpDictionaryWrapper {

    val TAG = SjpDictionaryWrapper::class.java.simpleName

    fun findMeanings(hardWord: String): Observable<List<String>> {
        return Observable.create { emitter ->


            val c = Jsoup.connect("http://sjp.pl/" + hardWord)

            val doc = c.get()
            val content = doc.outerHtml()
            Log.d(TAG, content)
            val el = content.split("<p style=\"margin: .5em 0; font-size: medium; max-width: 32em; \">".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val meanings = ArrayList<String>()
            for (i in 1..el.size - 1) {
                val bar = el[i]
                val meaning = bar.split("</p>".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                meanings.add(meaning.replace("<br>".toRegex(), "\n"))
            }

            if (emitter.isDisposed) {
                //no-op
            } else {
                emitter.onNext(meanings)
                emitter.onComplete()

            }
        }
    }

}
