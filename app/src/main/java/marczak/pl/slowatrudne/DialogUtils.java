package marczak.pl.slowatrudne;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

/**
 * Project "SlowaTrudne"
 * <p>
 * Created by Lukasz Marczak
 * on 06.03.2017.
 */

public class DialogUtils {

    public static void showIfNeeded(final Activity s) {

        boolean shouldShow = sharedPrefs(s).getBoolean("SHOW", true);

        if (shouldShow) new AlertDialog.Builder(s)
                .setTitle("O co tu chodzi?")
                .setMessage("Smyrnij w dół by wylosować, lub podyktuj trudne słowo (mikrofon)")
                .setNeutralButton("Nie pokazuj więcej", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefs(s).edit().putBoolean("SHOW", false).apply();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static SharedPreferences sharedPrefs(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp;
    }
}
