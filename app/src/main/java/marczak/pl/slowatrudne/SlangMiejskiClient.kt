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

object SlangMiejskiClient {

    val TAG = SlangMiejskiClient::class.java.simpleName

    fun findMeanings(hardWord: String): Observable<List<String>> {
        return Observable.create { emitter ->


            val c = Jsoup.connect("http://www.miejski.pl/slowo-" + hardWord)

            val doc = c.get()

            val summaries = doc.getElementsByClass("summary");
            val examples = doc.getElementsByClass("przyklad");

            if (summaries == null || summaries.size == 0) {
                emitter.onNext(arrayListOf(""))
                emitter.onComplete()
            } else {

                val meanings = ArrayList<String>()

                for (element in summaries) {
                    meanings.add(element.html())
                }
                if (!examples.isEmpty()) {
                    meanings.add("");
                    meanings.add("przykłady:");

                    for (element in examples) {
                        meanings.add(element.html())
                    }
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

}
