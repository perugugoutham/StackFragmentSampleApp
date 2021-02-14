package com.goutham.perugu.stackfragment.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.goutham.perugu.stackfragment.R
import java.lang.RuntimeException

class StackFragmentsHolder : Fragment(),
    BackPressListener,
    InteractionListener {

    private var fragmentsList: ArrayList<CustomStackFragment> = arrayListOf()

    fun addFragment(fragment: CustomStackFragment) {
        fragmentsList.add(fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stack_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentsList.size < 2 || fragmentsList.size > 4){
            throw RuntimeException("Should be provided with at least 2 and maximum 4 fragments")
        }
        populateFirstPage()
        val ctaButton = view.findViewById<Button>(R.id.next_page)
        ctaButton.setOnClickListener {
            navigateToNext()
        }
    }

    private fun populateFirstPage() {
        val customStackFragment = fragmentsList[0]
        val fragObject = childFragmentManager.findFragmentByTag(customStackFragment.getFragTag())
        if (fragObject == null) {
            launchFragment(customStackFragment, false)
            customStackFragment.setInteractionListener(this)
        }
    }

    private fun launchFragment(customStackFragment: CustomStackFragment, addToBackStack: Boolean) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.bottom_top,
            R.anim.top_bottom,
            R.anim.bottom_top,
            R.anim.top_bottom
        )
        fragmentTransaction.add(
            R.id.parent,
            customStackFragment,
            customStackFragment.getFragTag()
        )
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(customStackFragment.getFragTag())
        }
        fragmentTransaction.commit()
        customStackFragment.onStateChanged(true)
    }

    private fun navigateToNext() {
        for (index in 1 until fragmentsList.size) {
            val customStackFragment = fragmentsList[index]
            val fragObject = childFragmentManager.findFragmentByTag(customStackFragment.getFragTag())
            if (fragObject == null) {
                launchFragment(customStackFragment, true)

                val previousFragment = fragmentsList[index - 1]
                previousFragment.onStateChanged(false)
                previousFragment.setInteractionListener(this)

                return
            }
        }

    }

    override fun onBackPressed() {
        fragmentsList.forEachIndexed { index, customStackFragment ->
            val fragObject = childFragmentManager.findFragmentByTag(customStackFragment.getFragTag())
            if (fragObject == null) {
                if (index != 0) {
                    val previousFragment = fragmentsList[index - 1]
                    previousFragment.onStateChanged(true)
                }
            }
        }
    }

    override fun dismissOthers(currentFragmentTag: String) {
        var isFound = false
        fragmentsList.forEach {
            if (!isFound) {
                if (it.getFragTag() == currentFragmentTag) {
                    isFound = true
                    it.onStateChanged(true)
                }
            } else {
                childFragmentManager.popBackStack()
            }
        }
    }

}

interface InteractionListener {
    fun dismissOthers(currentFragmentTag: String)
}
