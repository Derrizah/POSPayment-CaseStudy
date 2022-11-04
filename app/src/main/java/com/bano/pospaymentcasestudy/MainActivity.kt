package com.bano.pospaymentcasestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.api.PaymentService
import com.bano.pospaymentcasestudy.databinding.ActivityMainBinding
import com.bano.pospaymentcasestudy.pos.POSFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<POSFragment>(binding.fragmentContainerView.id)
        }
    }
}