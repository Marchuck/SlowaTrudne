package marczak.pl.slowatrudne.utils

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Project "SlowaTrudne"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

abstract class NextObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onComplete() {

    }
}
