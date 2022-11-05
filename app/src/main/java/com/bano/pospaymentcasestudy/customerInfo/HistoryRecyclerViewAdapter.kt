package com.bano.pospaymentcasestudy.customerInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bano.pospaymentcasestudy.R
import com.bano.pospaymentcasestudy.databinding.ListHistoryItemBinding
import com.bano.pospaymentcasestudy.db.payment.Payment
import java.text.DecimalFormat
import java.util.Collections

/**
 * Adapt data from payments table and show them in a recycler view
 */
class HistoryRecyclerViewAdapter : RecyclerView.Adapter<HistoryViewHolder>() {
    private val paymentList = ArrayList<Payment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListHistoryItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_history_item, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(paymentList[position])
    }

    /**
     * Set list of payments
     */
    fun setList(payments: List<Payment>) {
        paymentList.clear()
        paymentList.addAll(payments)
        paymentList.reverse()
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }
}

/**
 * Handles how each item of payment is shown
 */
class HistoryViewHolder(private val binding: ListHistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(payment: Payment) {
        binding.amountTextView.text =
            DecimalFormat("#.##").format(payment.amount.toDouble()/100).toString()  + " TL"
        binding.dateTextView.text = payment.dateTime
    }
}