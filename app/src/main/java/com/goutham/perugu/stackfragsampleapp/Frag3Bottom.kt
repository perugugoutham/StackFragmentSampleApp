package com.goutham.perugu.stackfragsampleapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goutham.perugu.stackfragment.framework.CustomStackFragment
import com.goutham.perugu.stackfragment.framework.InteractionListener
import com.squareup.picasso.Picasso

class Frag3Bottom : CustomStackFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment3_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accountsRecyclerView = view.findViewById<RecyclerView>(R.id.accounts_recycler_view)
        val accountsAdapter = AccountsAdapter()
        accountsRecyclerView.adapter = accountsAdapter
        accountsAdapter.submitList(generateDummyValues())

    }

    private fun generateDummyValues(): ArrayList<BankAccounts> {
        val dummyList = arrayListOf<BankAccounts>()
        dummyList.add(BankAccounts("HDFC Bank", "12369869136", "https://www.hdfcbank.com/static/features/%5BBBHOST%5D/theme-nb-hdfc/favicon.ico"))
        dummyList.add(BankAccounts("SBI Bank", "97128354379", "https://www.onlinesbi.com/favicon.ico"))
        return dummyList
    }

    override fun getPeekHeight(): Int {
        return requireView().measuredHeight - (2*resources.getDimension(R.dimen.collapse_view_height)).toInt()
    }

    override fun getFragTag(): String {
        return "FRAG3"
    }

    override fun onStateChanged(isExpanded: Boolean) {

    }

    override fun setInteractionListener(interactionListener: InteractionListener) {

    }
}

class AccountsAdapter(): RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    private var accountsList: List<BankAccounts> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.accounts_layout, parent, false)
        return AccountsViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return accountsList.size
    }

    fun submitList(accountList: List<BankAccounts>){
        this.accountsList = accountList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account = accountsList[position]
        holder.accountNumberTextView.text = account.accountNumber
        holder.bankNameTextView.text = account.bankName
        Picasso.get()
            .load(account.bankLogoUrl)
            .into(holder.bankLogoIMageView)
    }

    open class AccountsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val bankNameTextView: TextView = view.findViewById(R.id.bank_name)
        val accountNumberTextView: TextView = view.findViewById(R.id.account_number)
        val bankLogoIMageView: ImageView = view.findViewById(R.id.bank_image)
    }
}

data class BankAccounts(val bankName: String, val accountNumber: String, val bankLogoUrl: String)