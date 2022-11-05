package com.bano.pospaymentcasestudy.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.bano.pospaymentcasestudy.base.BaseActivity
import com.bano.pospaymentcasestudy.databinding.ActivityMainBinding
import com.bano.pospaymentcasestudy.pos.POSFragment

/**
 * Shows fragments and opens POSFragment initially
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<POSFragment>(binding.fragmentContainerView.id)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}