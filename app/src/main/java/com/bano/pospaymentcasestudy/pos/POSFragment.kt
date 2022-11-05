package com.bano.pospaymentcasestudy.pos

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.commit
import com.bano.pospaymentcasestudy.base.BaseFragment
import com.bano.pospaymentcasestudy.base.observeOnce
import com.bano.pospaymentcasestudy.customerInfo.CustomerInfoFragment
import com.bano.pospaymentcasestudy.databinding.FragmentPosBinding

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
        openSoftKeyboard()
    }

    /**
     * Adds functionality to Pay Button
     */
    private fun setupPayButton() {
        payButton.setOnClickListener(View.OnClickListener {
            val enteredAmount = binding.editTextAmount.text.toString()
            if (TextUtils.isEmpty(enteredAmount)) {
                binding.editTextAmount.error = "Lütfen tutar giriniz"
            } else {
                activateLoading()
                viewModel.qrImage.observeOnce(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { bmp ->
                        binding.qrImage.setImageBitmap(bmp)
                        Toast.makeText(
                            activity?.applicationContext,
                            "QR Kod Alındı",
                            Toast.LENGTH_LONG
                        ).show()
                        deactivateLoading()
                    })
                viewModel.getQRCodeForSale(enteredAmount.toFloat())
            }
        })
    }

    /**
     * Adds functionality to Go To Info Button
     */
    private fun setupGoToInfoButton() {
        goToInfoButton.setOnClickListener(View.OnClickListener {
            val customerInfoFragment = CustomerInfoFragment.newInstance(
                viewModel.receiptAmount.value!!.toFloat() / 100,
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

    /**
     * Requests focus on EditText and opens soft keyboard
     */
    private fun openSoftKeyboard() {
        binding.editTextAmount.requestFocus()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
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