package com.goutham.perugu.stackfragment.framework

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


abstract class CustomStackFragment: Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            if (view.layoutParams.height != getPeekHeight()){
                view.layoutParams.height = getPeekHeight()
                view.requestLayout()
            }
        }
    }

    abstract fun getPeekHeight(): Int

    abstract fun getFragTag(): String

    abstract fun onStateChanged(isExpanded: Boolean)

    abstract fun setInteractionListener(interactionListener: InteractionListener)

}

interface BackPressListener{
    fun onBackPressed()
}