package com.bano.pospaymentcasestudy

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bano.pospaymentcasestudy.api.request.PostPayment
import com.bano.pospaymentcasestudy.api.response.PaymentResponse
import com.bano.pospaymentcasestudy.customerInfo.CustomerInfoViewModel
import com.bano.pospaymentcasestudy.db.payment.Payment
import com.bano.pospaymentcasestudy.db.payment.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CustomerInfoViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CustomerInfoViewModel

    @Mock
    lateinit var repo: PaymentRepository

    @Mock
    lateinit var observer: Observer<PaymentResponse>

    @Mock
    lateinit var paymentsObserver: Observer<Payment>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CustomerInfoViewModel()
            .apply { this.inject(mock(Context::class.java)) }
        viewModel.lastResponse.observeForever(observer)
    }

    @Test
    fun verifyPaymentAdd(){
        val amount = 100
        val session = "12345"
        val qr = "54321"
        val expectedPayment = Payment(
            0,
            amount,
            SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(Calendar.getInstance().time),
            session,
            qr
        )
        viewModel.lastInsertedPayment.observeForever(paymentsObserver)
        viewModel.addIntoRepo(amount, session, qr)
        Mockito.verify(paymentsObserver).onChanged(expectedPayment)
    }

    @Test
    fun verifyPaymentResponse() {
        val mockQRData =
            "00020153039495403100800201810200821912-01-2018 12:43:24830481-48608800-100#8712AT00000000018901184034178844secureqrsigniturewillbehereinthenearfuture1="
        val expectedAmount = 100.0f

        val fakeResult = Response.success(mock(PaymentResponse::class.java))
        val fakeResponse = MutableLiveData<Response<PaymentResponse>>()
        fakeResponse.value = fakeResult

        runBlocking {
            launch {
                Mockito.`when`(viewModel.osyService.postPayment(PostPayment(qrData = mockQRData)))
                    .then {
                        fakeResponse.value
                        println("fake response returned")
                    }
                viewModel.proceedPayment(mockQRData, expectedAmount)
                Mockito.verify(observer).onChanged(fakeResult.body())
            }
        }
    }
}