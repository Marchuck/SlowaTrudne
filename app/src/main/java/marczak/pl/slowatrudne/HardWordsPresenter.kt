package marczak.pl.slowatrudne

import io.reactivex.disposables.Disposable

/**
 * Project "SlowaTrudne"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

class HardWordsPresenter(internal var view: HardWordsView?) : BasePresenter() {

    internal var findWordDisposable: Disposable? = null;


    override fun destroy() {
        //todo:
        if (findWordDisposable != null && !findWordDisposable!!.isDisposed) {
            findWordDisposable!!.dispose()
        }
    }


    fun findHardWord(word: String) {
        view?.startLoad()

        SjpDictionaryWrapper.findMeanings(word)
                .doOnSubscribe { d -> findWordDisposable = d; }
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
}
