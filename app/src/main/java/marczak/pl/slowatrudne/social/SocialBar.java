package marczak.pl.slowatrudne.social;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Experimental;

/**
 * Created by Lukasz Marczak on 08.03.17.
 */
@Experimental
public class SocialBar {
    List<? super Socialable> socialables;

    private SocialBar(List<? super Socialable> s) {
        socialables = s;
    }

    public static class Builder {

        List<? super Socialable> socialables = new ArrayList<>();

        public Builder add(Socialable socialable) {
            socialables.add(socialable);
            return this;
        }

        public SocialBar build() {
            return new SocialBar(socialables);
        }
    }


}
