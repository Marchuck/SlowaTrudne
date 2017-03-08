package marczak.pl.slowatrudne.social;

import android.content.Intent;

import io.reactivex.annotations.Experimental;

/**
 * Created by Lukasz Marczak on 08.03.17.
 */
@Experimental
public interface Socialable {

    String packageName();

    Intent createIntent();


}
