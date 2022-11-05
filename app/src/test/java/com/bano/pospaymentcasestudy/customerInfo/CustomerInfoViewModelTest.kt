package com.bano.pospaymentcasestudy.customerInfo

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bano.pospaymentcasestudy.api.OSYService
import com.bano.pospaymentcasestudy.api.request.PostPayment
import com.bano.pospaymentcasestudy.api.response.PaymentResponse
import com.bano.pospaymentcasestudy.db.payment.Payment
import com.bano.pospaymentcasestudy.db.payment.PaymentRepository
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class CustomerInfoViewModelTest {
    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

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

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CustomerInfoViewModel()
            .apply { this.inject(mock(Context::class.java)) }
        viewModel.lastInsertedPayment.observeForever(paymentsObserver)
        viewModel.lastResponse.observeForever(observer)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun verifyPaymentAdded(){
        runTest {
            val mockQRData =
                "00020153039495403100800201810200821912-01-2018 12:43:24830481-48608800-100#8712AT00000000018901184034178844secureqrsigniturewillbehereinthenearfuture1="
            val mockAmount = 100
            val mockSessionID = "12345"
            val fakePayment = Payment(0, mockAmount, Date().toString(), mockSessionID, mockQRData)

            Mockito.`when`(repo.insert(fakePayment)).then {
                fakePayment
            }
            viewModel.addLastPayment(fakePayment.amount, fakePayment.sessionID, fakePayment.qrData)
            Mockito.verify(paymentsObserver).onChanged(fakePayment)
        }
    }

    @Test
    fun verifyPaymentResponse() {
        val mockQRData =
            "00020153039495403100800201810200821912-01-2018 12:43:24830481-48608800-100#8712AT00000000018901184034178844secureqrsigniturewillbehereinthenearfuture1="
        val expectedAmount = 100

//        val fakeResult = mock(PaymentResponse::class.java)
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

class RxImmediateSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}