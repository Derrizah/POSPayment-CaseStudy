package com.bano.pospaymentcasestudy.customerInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bano.pospaymentcasestudy.base.observeOnce
import com.bano.pospaymentcasestudy.databinding.FragmentCustomerInfoBinding

private const val RECEIPT_AMOUNT = "RECEIPT_AMOUNT"
private const val QR_DATA = "QR_DATA"

class CustomerInfoFragment : Fragment() {

    lateinit var viewModel: CustomerInfoViewModel
    private lateinit var binding: FragmentCustomerInfoBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerInfoBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            CustomerInfoViewModelFactory(requireActivity().applicationContext)
        )[CustomerInfoViewModel::class.java]

        initRecyclerView()
        binding.textAmount.text = "Ödenecek Tutar: " + receiptAmount.toString()
        binding.qrImage.setImageBitmap(viewModel.getQRBitmap(qrData!!))

        binding.buttonProceed.setOnClickListener(View.OnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonProceed.isClickable = false
            binding.buttonProceed.isEnabled = false
            viewModel.paymentComplete.observeOnce(viewLifecycleOwner, Observer {
                if (it) {
                    binding.checkboxImage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })
            viewModel.proceedPayment(qrData!!, receiptAmount!!)
        })
    }

    private fun initRecyclerView() {
        binding.historyRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity().applicationContext)
        adapter = HistoryRecyclerViewAdapter()
        binding.historyRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment POSFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            CustomerInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECEIPT_AMOUNT, param1)
                    putString(QR_DATA, param2)
                }
            }
    }
}