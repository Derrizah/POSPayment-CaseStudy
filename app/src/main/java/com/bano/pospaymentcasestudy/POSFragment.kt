package com.bano.pospaymentcasestudy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.databinding.FragmentPosBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class POSFragment : Fragment() {
    lateinit var viewModel: PaymentViewModel
    private lateinit var binding: FragmentPosBinding

    // TODO: Rename and change types of parameters
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

        viewModel = ViewModelProvider(requireActivity(), PaymentViewModelFactory(requireActivity()))[PaymentViewModel::class.java]

        binding.btnPay.setOnClickListener(View.OnClickListener {
            it.isEnabled = false
            it.isClickable = false
            viewModel.qrImage.observeOnce(viewLifecycleOwner, androidx.lifecycle.Observer{ bmp ->
                binding.qrImage.setImageBitmap(bmp)
                it.isEnabled = true
                it.isClickable = true
            })
            viewModel.getQRCodeForSale(QRForSale(binding.etxtAmount.text.toString().toInt()))
        })

        binding.btnGoToInfo.setOnClickListener(View.OnClickListener {
            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace(binding.root.id, CustomerInfoFragment())
            }
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
        fun newInstance(param1: String, param2: String) =
            POSFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}