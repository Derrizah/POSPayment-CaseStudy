package com.bano.pospaymentcasestudy.pos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.api.QRForSale
import com.bano.pospaymentcasestudy.base.observeOnce
import com.bano.pospaymentcasestudy.customerInfo.CustomerInfoFragment
import com.bano.pospaymentcasestudy.databinding.FragmentPosBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class POSFragment : Fragment() {
    lateinit var viewModel: POSViewModel
    private lateinit var binding: FragmentPosBinding

    private lateinit var payButton: Button
    private lateinit var goToInfoButton: Button

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPosBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), POSViewModelFactory())[POSViewModel::class.java]

        payButton = binding.buttonPay
        goToInfoButton = binding.buttonGoToInfo

        payButton.setOnClickListener(View.OnClickListener {
            activateLoading()
            viewModel.qrImage.observeOnce(viewLifecycleOwner, androidx.lifecycle.Observer{ bmp ->
                binding.qrImage.setImageBitmap(bmp)
                deactivateLoading()
            })
            viewModel.getQRCodeForSale(QRForSale(binding.editTextAmount.text.toString().toInt()))
        })

        goToInfoButton.setOnClickListener(View.OnClickListener {
            val customerInfoFragment = CustomerInfoFragment.newInstance(viewModel.receiptAmount.value!!, viewModel.qrString.value!!)
            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace(((view as ViewGroup).parent as View).id, customerInfoFragment)
            }
        })
    }
    private fun activateLoading() {
        payButton.isEnabled = false
        payButton.isClickable = false

        goToInfoButton.isEnabled = false
        goToInfoButton.isClickable = false

        binding.progressBar.visibility = View.VISIBLE
    }
    private fun deactivateLoading() {
        payButton.isEnabled = true
        payButton.isClickable = true

        goToInfoButton.isEnabled = true
        goToInfoButton.isClickable = true

        binding.progressBar.visibility = View.INVISIBLE
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
        fun newInstance(param1: String, param2: String) =
            POSFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}