package marczak.pl.slowatrudne.recognition

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log

/**
 * Project "TrudneSlowo"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

class RecognizeSpeechActivity : SpeechRecognizingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val word = if (intent == null) "Speak now" else intent.action

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, word)

        recognize(intent)
    }

    override fun speechNotAvailable() {
        Log.d(TAG, "speechNotAvailable: ")
    }

    override fun directSpeechNotAvailable() {
        Log.d(TAG, "directSpeechNotAvailable: ")
    }

    override fun languageCheckResult(languageToUse: String) {
        Log.d(TAG, "languageCheckResult() called with: languageToUse = [$languageToUse]")
    }

    override fun receiveWhatWasHeard(heard: List<String>, confidenceScores: FloatArray) {

        Log.d(TAG, "receiveWhatWasHeard: ")

        for (i in confidenceScores.indices) {
            Log.w(TAG, "receiveWhatWasHeard: " + heard[i] + " : " + confidenceScores[i])

        }
        val returnIntent = Intent()
        returnIntent.putExtra(TAG, heard[0])
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun recognitionFailure(errorCode: Int) {
        Log.e(TAG, "recognitionFailure: " + errorCode)
        finish()
    }

    companion object {

        val TAG = RecognizeSpeechActivity::class.java.simpleName
    }
}
