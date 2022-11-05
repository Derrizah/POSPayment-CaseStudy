package com.bano.pospaymentcasestudy

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bano.pospaymentcasestudy.pos.POSViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class POSViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: POSViewModel

    @Mock
    lateinit var receiptAmountObserver: Observer<Int>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = POSViewModel()
            .apply { this.inject(mock(Context::class.java)) }
    }

    @Test
    fun verifyReceiptAmount() {
        val expectedAmount = 100

        viewModel.receiptAmount.observeForever(receiptAmountObserver)
        viewModel.getQRCodeForSale(expectedAmount.toFloat())
        Mockito.verify(receiptAmountObserver).onChanged(expectedAmount * 100)
    }
}