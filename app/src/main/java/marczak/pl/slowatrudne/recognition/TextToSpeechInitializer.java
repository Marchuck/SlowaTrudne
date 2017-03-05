package marczak.pl.slowatrudne.recognition;

/**
 * Project "TrudneSlowo"
 * <p>
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

import java.util.Locale;

/**
 * Helps construct an initalized {@link TextToSpeech}
 *
 * @author Greg Milette &#60;<a href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
 */
public class TextToSpeechInitializer implements OnInitListener {
    private static final String TAG = "TextToSpeechInitializer";

    private TextToSpeech tts;

    private TextToSpeechStartupListener callback;

    private Context context;
    Locale locale;

    /**
     * creates by checking {@link TextToSpeech#isLanguageAvailable(Locale)}
     */
    public TextToSpeechInitializer(Context context, final Locale loc,
                                   TextToSpeechStartupListener callback) {
        this.callback = callback;
        this.context = context;
        createTextToSpeech(loc);
    }

    /**
     * creating a TTS
     *
     * @param locale {@link Locale} for the desired language
     */
    private void createTextToSpeech(final Locale locale) {
        Log.d(TAG, "createTextToSpeech: ");
        this.locale = locale;
        tts = new TextToSpeech(context, this);
    }

    /**
     * perform the initialization checks after the
     * TextToSpeech is done
     */
    private void setTextToSpeechSettings( Locale locale) {
        Locale defaultOrPassedIn = locale;
        if (locale == null) {
            defaultOrPassedIn = Locale.getDefault();
        }
//        defaultOrPassedIn  = Locale.ENGLISH;
//        locale  = Locale.ENGLISH;
        // check if language is available
        switch (tts.isLanguageAvailable(defaultOrPassedIn)) {
            case TextToSpeech.LANG_AVAILABLE:
            case TextToSpeech.LANG_COUNTRY_AVAILABLE:
            case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
                Log.d(TAG, "SUPPORTED");
                tts.setLanguage(locale);
                callback.onSuccessfulInit(tts);
                break;
            case TextToSpeech.LANG_MISSING_DATA:
                Log.d(TAG, "MISSING_DATA");
                // check if waiting, by checking
                // a shared preference
                if (LanguageDataInstallBroadcastReceiver
                        .isWaiting(context)) {
                    Log.d(TAG, "waiting for data...");
                    callback.onWaitingForLanguageData();
                } else {
                    Log.d(TAG, "require data...");
                    callback.onRequireLanguageData();
                }
                break;
            case TextToSpeech.LANG_NOT_SUPPORTED:
                Log.d(TAG, "NOT SUPPORTED");
                callback.onFailedToInit();
                break;
        }
    }

    public void setOnUtteranceCompleted(OnUtteranceCompletedListener whenDoneSpeaking) {
        int result = tts.setOnUtteranceCompletedListener(whenDoneSpeaking);
        if (result == TextToSpeech.ERROR) {
            Log.e(TAG, "failed to add utterance listener");
        }
    }

    /**
     * helper method to display a dialog if waiting for a download
     */
    public void installLanguageData(Dialog ifAlreadyWaiting) {
        boolean alreadyDidThis = LanguageDataInstallBroadcastReceiver
                .isWaiting(context);
        if (alreadyDidThis) {
            // give the user the ability to try again..
            // throw exception here?
            ifAlreadyWaiting.show();
        } else {
            installLanguageData();
        }
    }

    public void installLanguageData() {
        // set waiting for the download
        LanguageDataInstallBroadcastReceiver.setWaiting(context, true);

        Intent installIntent = new Intent();
        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        context.startActivity(installIntent);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            setTextToSpeechSettings(locale);
        } else {
            Log.e(TAG, "error creating text to speech");
            callback.onFailedToInit();
        }
    }

    public void destroy() {
        if (tts != null) tts.shutdown();
    }
}
