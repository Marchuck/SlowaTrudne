package marczak.pl.slowatrudne.social;

import android.content.Intent;

import io.reactivex.annotations.Experimental;

/**
 * Created by Lukasz Marczak on 08.03.17.
 */
@Experimental
public class FaceBar implements Socialable {
    @Override
    public String packageName() {
        return null;
    }

    @Override
    public Intent createIntent() {
        return null;
    }
}
