package com.goutham.perugu.stackfragsampleapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.goutham.perugu.stackfragment.framework.CustomStackFragment
import com.goutham.perugu.stackfragment.framework.InteractionListener

class Frag2Bottom: CustomStackFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plansRecyclerView = view.findViewById<RecyclerView>(R.id.recomended_plans_recycler_view)
        val recommendedPlansAdapter = RecommendedPlansAdapter()
        plansRecyclerView.adapter = recommendedPlansAdapter
        recommendedPlansAdapter.submitList(generateDummyList())

    }

    private fun generateDummyList(): ArrayList<RecommendedPlans> {
        val dummyList = arrayListOf<RecommendedPlans>()
        dummyList.add(RecommendedPlans(2375, 5, true))
        dummyList.add(RecommendedPlans(2537, 9, false))
        dummyList.add(RecommendedPlans(8935, 4, false))
        return dummyList
    }

    override fun getPeekHeight(): Int {
        return requireView().measuredHeight - resources.getDimension(R.dimen.collapse_view_height).toInt()
    }

    override fun getFragTag(): String {
        return "FRAG2"
    }

    override fun setInteractionListener(interactionListener: InteractionListener) {
        view?.post {
            val collapseContainer = requireView().findViewById<ViewGroup>(R.id.collapse_state_container)
            collapseContainer.setOnClickListener {
                interactionListener.dismissOthers(getFragTag())
            }
        }
    }

    override fun onStateChanged(isExpanded: Boolean) {
        view?.post {
            val collapseContainer = requireView().findViewById<ViewGroup>(R.id.collapse_state_container)
            val expandContainer = requireView().findViewById<ViewGroup>(R.id.expand_state_container)
            if (!isExpanded){
                collapseContainer.visibility = View.VISIBLE
                expandContainer.visibility = View.GONE
            }else {
                collapseContainer.visibility = View.GONE
                expandContainer.visibility = View.VISIBLE
            }
        }

    }
}

class RecommendedPlansAdapter(): RecyclerView.Adapter<RecommendedPlansAdapter.RecommendedPlansViewHolder>() {

    private var recommendedList: List<RecommendedPlans> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedPlansViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommended_plan_layout, parent, false)
        return RecommendedPlansViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommendedList.size
    }

    fun submitList(recommendedList: List<RecommendedPlans>){
        this.recommendedList = recommendedList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecommendedPlansViewHolder, position: Int) {
        val recommendedPlan = recommendedList[position]
        val context = holder.itemView.context
        holder.priceTextView.text = context.getString(R.string.recommended_price, recommendedPlan.priceRupees)
        holder.periodTextView.text = context.getString(R.string.recommended_period, recommendedPlan.periodMonths)
        val colorCodes = context.resources.getStringArray(R.array.recommended_card_colors)
        holder.priceCard.setCardBackgroundColor(Color.parseColor(colorCodes[position]))
    }

    class RecommendedPlansViewHolder(view: View): RecyclerView.ViewHolder(view){
        val priceTextView = view.findViewById<TextView>(R.id.price)
        val periodTextView = view.findViewById<TextView>(R.id.period)
        val seeCalculation = view.findViewById<TextView>(R.id.calculations)
        val priceCard = view.findViewById<CardView>(R.id.prices_card)
    }


}

data class RecommendedPlans(val priceRupees: Int, val periodMonths: Int, val isRecommended: Boolean)