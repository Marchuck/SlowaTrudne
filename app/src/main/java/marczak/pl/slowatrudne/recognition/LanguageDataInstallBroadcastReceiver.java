package marczak.pl.slowatrudne.recognition;

/**
 * Project "TrudneSlowo"
 * <p>
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * helps track the status of downloading the language data when the app requires
 * it. It stores the status in a SharedPreferences field. Apps should check its
 * current value by using the static methods.
 *
 * @author Greg Milette &#60;<a
 *         href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
 */
public class LanguageDataInstallBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "LanguageDataInstallBroadcastReceiver";

    private static final String PREFERENCES_NAME = "installedLanguageData";

    private static final String WAITING_PREFERENCE_NAME =
            "WAITING_PREFERENCE_NAME";

    private static final Boolean WAITING_DEFAULT = false;

    public LanguageDataInstallBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(
                TextToSpeech.Engine.ACTION_TTS_DATA_INSTALLED)) {
            Log.d(TAG, "language data preference: " + intent.getAction());
            // clear waiting state
            setWaiting(context, false);
        }
    }

    /**
     * check if the receiver is waiting for a language data install
     */
    public static boolean isWaiting(Context context) {
        SharedPreferences preferences;
        preferences =
                context.getSharedPreferences(PREFERENCES_NAME,
                        Context.MODE_WORLD_READABLE);
        boolean waiting =
                preferences
                        .getBoolean(WAITING_PREFERENCE_NAME, WAITING_DEFAULT);
        return waiting;
    }

    /**
     * start waiting by setting a flag
     */
    public static void setWaiting(Context context, boolean waitingStatus) {
        SharedPreferences preferences;
        preferences =
                context.getSharedPreferences(PREFERENCES_NAME,
                        Context.MODE_WORLD_WRITEABLE);
        Editor editor = preferences.edit();
        editor.putBoolean(WAITING_PREFERENCE_NAME, waitingStatus);
        editor.commit();
    }
}

// /**
// * register this receiver, calling this method is only necessary if you
// * didn't create the receiver via the manifest
// *
// * @param context
// */
// public void register(Context context)
// {
// Log.d(TAG, "registered a receiver!!");
// context.registerReceiver(this, new IntentFilter(
// TextToSpeech.Engine.ACTION_TTS_DATA_INSTALLED));
// }
//
// /**
// * unregister itself, also clear the shared preference
// */
// public void unregister(Context context)
// {
// context.unregisterReceiver(this);
// }
