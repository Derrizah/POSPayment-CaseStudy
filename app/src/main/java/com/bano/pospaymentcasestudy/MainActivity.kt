package com.bano.pospaymentcasestudy

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.databinding.ActivityMainBinding

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

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<POSFragment>(binding.fragmentContainerView.id)
        }
    }
}