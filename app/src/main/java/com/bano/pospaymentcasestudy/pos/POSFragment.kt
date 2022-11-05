package com.bano.pospaymentcasestudy.pos

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.api.request.QRForSale
import com.bano.pospaymentcasestudy.base.observeOnce
import com.bano.pospaymentcasestudy.customerInfo.CustomerInfoFragment
import com.bano.pospaymentcasestudy.databinding.FragmentPosBinding
import com.bano.pospaymentcasestudy.main.ViewModelFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Fragment to receive payment amount and display QR code
 */
class POSFragment : Fragment() {
    lateinit var viewModel: POSViewModel
    lateinit var binding: FragmentPosBinding

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

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        )[POSViewModel::class.java]

        payButton = binding.buttonPay
        goToInfoButton = binding.buttonGoToInfo

        setupPayButton()
        setupGoToInfoButton()
    }

    private fun setupPayButton() {
        payButton.setOnClickListener(View.OnClickListener {
            val enteredAmount = binding.editTextAmount.text.toString()
            if(TextUtils.isEmpty(enteredAmount)) {
                binding.editTextAmount.error = "Lütfen tutar giriniz"
            }
            else {
                activateLoading()
                viewModel.qrImage.observeOnce(viewLifecycleOwner, androidx.lifecycle.Observer { bmp ->
                    binding.qrImage.setImageBitmap(bmp)
                    Toast.makeText(activity?.applicationContext, "QR Kod Alındı", Toast.LENGTH_LONG).show()
                    deactivateLoading()
                })
                viewModel.getQRCodeForSale(QRForSale(enteredAmount.toInt()))
            }
        })
    }

    private fun setupGoToInfoButton() {
        goToInfoButton.setOnClickListener(View.OnClickListener {
            val customerInfoFragment = CustomerInfoFragment.newInstance(
                viewModel.receiptAmount.value!!,
                viewModel.qrString.value!!
            )
            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace(((view as ViewGroup).parent as View).id, customerInfoFragment)
            }
        })
    }

    /**
     * Disables buttons and shows progress bar
     */
    private fun activateLoading() {
        payButton.isEnabled = false
        payButton.isClickable = false

        goToInfoButton.isEnabled = false
        goToInfoButton.isClickable = false

        binding.progressBar.visibility = View.VISIBLE
    }

    /**
     * Enables buttons and hides progress bar
     */
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