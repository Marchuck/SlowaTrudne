package marczak.pl.slowatrudne.utils

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View

import marczak.pl.slowatrudne.R


/**
 * Created by Lukasz Marczak on 03.02.17.
 */
class FloatyBehaviour : CoordinatorLayout.Behavior<View> {

    constructor() {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        return dependency is Snackbar.SnackbarLayout && child!!.id == R.id.text2speech
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        val translationY = Math.min(0f, dependency!!.translationY - dependency.height)
        child!!.translationY = translationY
        return true
    }

    companion object {

        val TAG = FloatyBehaviour::class.java.simpleName
    }
}