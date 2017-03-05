package marczak.pl.slowatrudne;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Project "SlowaTrudne"
 * <p>
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

public abstract class NextObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
