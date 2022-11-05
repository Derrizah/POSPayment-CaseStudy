package com.bano.pospaymentcasestudy.pos

import com.bano.pospaymentcasestudy.R
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class POSFragmentTest {

    @Test
    fun testPOSFragment() {
        // The "fragmentArgs" arguments are optional.
//        val fragmentArgs = bundleOf("numElements" to 0)
        with(launchFragmentInContainer<POSFragment>()) {
            onFragment { fragment ->
            onView(withId(fragment.binding.editTextAmount.id))
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard())
            onView(withId(fragment.binding.buttonPay.id)).perform(ViewActions.click())
        }}
    }
}