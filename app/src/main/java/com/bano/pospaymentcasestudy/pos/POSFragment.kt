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
import com.bano.pospaymentcasestudy.base.BaseFragment
import com.bano.pospaymentcasestudy.base.observeOnce
import com.bano.pospaymentcasestudy.customerInfo.CustomerInfoFragment
import com.bano.pospaymentcasestudy.databinding.FragmentPosBinding
import com.bano.pospaymentcasestudy.main.ViewModelFactory

/**
 * Fragment to receive payment amount and display QR code
 */
class POSFragment : BaseFragment<FragmentPosBinding, POSViewModel>() {

    private lateinit var payButton: Button
    private lateinit var goToInfoButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
         * this fragment.
         *
         * @return A new instance of fragment POSFragment.
         */
        @JvmStatic
        fun newInstance() = POSFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPosBinding {
        return FragmentPosBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): Class<POSViewModel> =
        POSViewModel::class.java
}