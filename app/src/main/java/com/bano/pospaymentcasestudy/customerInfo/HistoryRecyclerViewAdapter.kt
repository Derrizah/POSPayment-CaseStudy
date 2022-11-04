package com.bano.pospaymentcasestudy.customerInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bano.pospaymentcasestudy.R
import com.bano.pospaymentcasestudy.databinding.ListHistoryItemBinding
import com.bano.pospaymentcasestudy.db.payment.Payment

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

    fun setList(payments: List<Payment>) {
        paymentList.clear()
        paymentList.addAll(payments)
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }
}

class HistoryViewHolder(private val binding: ListHistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(payment: Payment) {
        binding.amountTextView.text = payment.amount.toString()
        binding.dateTextView.text = payment.dateTime.toString()
    }
}