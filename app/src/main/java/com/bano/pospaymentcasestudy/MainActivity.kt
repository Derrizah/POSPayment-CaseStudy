package com.bano.pospaymentcasestudy

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var paymentService: PaymentService
    lateinit var viewModel: PaymentViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val factory = PaymentViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[PaymentViewModel::class.java]
        viewModel.init()
        viewModel.qrImage.observe(this, androidx.lifecycle.Observer{
            binding.qrImage.setImageBitmap(it)
        })

        binding.btnPay.setOnClickListener(View.OnClickListener {
            viewModel.getQRCodeForSale(QRForSale(binding.etxtAmount.text.toString().toInt()))
        })
    }
}