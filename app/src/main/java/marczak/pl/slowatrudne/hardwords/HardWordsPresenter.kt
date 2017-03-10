package marczak.pl.slowatrudne.hardwords

import android.os.Handler
import android.provider.ContactsContract
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import marczak.pl.slowatrudne.utils.BasePresenter
import marczak.pl.slowatrudne.utils.NextObserver
import marczak.pl.slowatrudne.SjpDictionaryClient
import marczak.pl.slowatrudne.SlangMiejskiClient
import marczak.pl.slowatrudne.data.DataSource
import java.util.*

/**
 * Project "SlowaTrudne"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

class HardWordsPresenter(internal var view: HardWordsView?) : BasePresenter() {

    internal val dataSource by lazy { DataSource() }

    internal var findWordDisposable: Disposable? = null

    override fun destroy() {
        //todo:
        if (findWordDisposable != null && !findWordDisposable!!.isDisposed) {
            findWordDisposable!!.dispose()
        }
    }

    fun findHardWord(word: String) {
        view?.startLoad()

        Observable.zip<List<String>, List<String>, List<String>>(
                SjpDictionaryClient.findMeanings(word).onErrorReturn { arrayListOf("") },
                SlangMiejskiClient.findMeanings(word).onErrorReturn { arrayListOf("") },
                BiFunction<List<String>, List<String>, List<String>> { t1, t2 ->
                    val l = ArrayList<String>()
                    l.addAll(t1)
                    l.addAll(t2)
                    return@BiFunction l
                }
        ).doOnSubscribe { d -> findWordDisposable = d; }
                .doFinally { view?.endLoad() }
                .compose(applySchedulers())
                .subscribeWith(object : NextObserver<List<String>>() {
                    override fun onNext(meanings: List<String>) {
                        view?.showHardWordMeaning(word, meanings)
                    }

                    override fun onError(e: Throwable) {
                        view?.showError()
                    }
                })
    }

    fun requestNewHardWord() {
        view?.startLoad()
        Handler().post {

            val index = dataSource.randomIndex()
            val word = dataSource.hardWords[index]
            val meaning = dataSource.hardWordsMeanings[index]

            view?.showHardWordMeaning(word, arrayListOf(meaning))
            view?.endLoad()
        }
    }

}
