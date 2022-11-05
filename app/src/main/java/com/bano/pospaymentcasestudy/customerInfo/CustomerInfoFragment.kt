package com.bano.pospaymentcasestudy.customerInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bano.pospaymentcasestudy.base.BaseFragment
import com.bano.pospaymentcasestudy.base.observeOnce
import com.bano.pospaymentcasestudy.databinding.FragmentCustomerInfoBinding
import com.bano.pospaymentcasestudy.pos.POSFragment
import com.bano.pospaymentcasestudy.main.ViewModelFactory
import java.text.DecimalFormat

private const val RECEIPT_AMOUNT = "RECEIPT_AMOUNT"
private const val QR_DATA = "QR_DATA"

/**
 * Fragment to approve payments and display payment history
 */
class CustomerInfoFragment : BaseFragment<FragmentCustomerInfoBinding, CustomerInfoViewModel>() {

    private lateinit var adapter: HistoryRecyclerViewAdapter

    private var receiptAmount: Int? = null
    private var qrData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receiptAmount = it.getInt(RECEIPT_AMOUNT)
            qrData = it.getString(QR_DATA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        binding.textAmount.text = "Ödenecek Tutar:\n" + DecimalFormat("#.##").format(receiptAmount!!.toDouble()/100) + " TL"
        binding.qrImage.setImageBitmap(viewModel.getQRBitmap(qrData!!))

        binding.buttonProceed.setOnClickListener(View.OnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonProceed.isClickable = false
            binding.buttonProceed.isEnabled = false
            viewModel.paymentComplete.observeOnce(viewLifecycleOwner, Observer {
                if (it) {
                    binding.checkboxImage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE

                    Toast.makeText(activity?.applicationContext, "Ödeme Onaylandı", Toast.LENGTH_LONG).show()
                }
            })
            viewModel.proceedPayment(qrData!!, receiptAmount!!)
        })

        binding.buttonGoToPos.setOnClickListener(View.OnClickListener {
            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace(((view as ViewGroup).parent as View).id, POSFragment())
            }
        })
    }

    /**
     * Initialize recycler view with adapter
     */
    private fun initRecyclerView() {
        binding.historyRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity().applicationContext)
        adapter = HistoryRecyclerViewAdapter()
        binding.historyRecyclerView.adapter = adapter
        displayPaymentHistory()
    }

    /**
     * Observe and display payment history
     */
    private fun displayPaymentHistory() {
        viewModel.payments.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param amount RECEIPT_AMOUNT.
         * @param qrString QR_DATA.
         * @return A new instance of fragment CustomerInfoFragment.
         */
        @JvmStatic
        fun newInstance(amount: Int, qrString: String) =
            CustomerInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECEIPT_AMOUNT, amount)
                    putString(QR_DATA, qrString)
                }
            }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCustomerInfoBinding {
        return FragmentCustomerInfoBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): Class<CustomerInfoViewModel> =
        CustomerInfoViewModel::class.java
}