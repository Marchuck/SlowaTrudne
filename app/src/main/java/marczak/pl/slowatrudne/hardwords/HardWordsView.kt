package marczak.pl.slowatrudne.hardwords

/**
 * Project "SlowaTrudne"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

interface HardWordsView {

    fun startLoad()

    fun endLoad()

    fun showError()

    fun showHardWordMeaning(hardWord: String, meanings: List<String>);
}
