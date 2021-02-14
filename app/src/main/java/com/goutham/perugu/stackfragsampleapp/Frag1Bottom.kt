package com.goutham.perugu.stackfragsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goutham.perugu.stackfragment.framework.CustomStackFragment
import com.goutham.perugu.stackfragment.framework.InteractionListener

class Frag1Bottom: CustomStackFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment1_layout, container, false)
    }

    override fun getPeekHeight(): Int {
        return requireView().measuredHeight
    }

    override fun getFragTag(): String {
        return "FRAG1"
    }

    override fun onStateChanged(isExpanded: Boolean) {
        view?.post {
            val collapseViewGroup = requireView().findViewById<ViewGroup>(R.id.collapse_state_container)
            val expandViewGroup = requireView().findViewById<ViewGroup>(R.id.expand_state_container)
            if (!isExpanded){
                collapseViewGroup.visibility = View.VISIBLE
                expandViewGroup.visibility = View.GONE
            }else {
                collapseViewGroup.visibility = View.GONE
                expandViewGroup.visibility = View.VISIBLE
            }
        }

    }

    override fun setInteractionListener(interactionListener: InteractionListener) {
        view?.post {
            val collapseViewGroup = requireView().findViewById<ViewGroup>(R.id.collapse_state_container)
            collapseViewGroup.setOnClickListener {
                interactionListener.dismissOthers(getFragTag())
            }
        }
    }

}