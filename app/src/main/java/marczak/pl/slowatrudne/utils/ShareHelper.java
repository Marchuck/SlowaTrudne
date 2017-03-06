package marczak.pl.slowatrudne.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Project "SlowaTrudne"
 * <p>
 * Created by Lukasz Marczak
 * on 06.03.2017.
 */

public class ShareHelper {

    public static void share(Context c, String word, String meaning) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Czy wiedziałeś że...\n" + word + "\nto\n" + meaning;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "#SlowaTrudne");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Udostępnij przez"));
    }
}
