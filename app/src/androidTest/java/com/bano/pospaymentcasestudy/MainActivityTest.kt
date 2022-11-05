package com.bano.pospaymentcasestudy

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bano.pospaymentcasestudy.R
import com.bano.pospaymentcasestudy.main.MainActivity
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DecimalFormat


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testPOSFragmentFlow() {
        onView(withId(R.id.edit_text_amount)).perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.button_pay)).perform(ViewActions.click())
        onView(withId(R.id.edit_text_amount)).check(ViewAssertions.matches(Matchers.not(withText(""))))
        onView(withId(R.id.button_go_to_info)).perform(ViewActions.click())
    }

    @Test
    fun testCustomerInfoFragmentReceiptAmount() {
        val stringToBeTyped = "100"
        val stringToBeShown = DecimalFormat("#.##").format(stringToBeTyped.toDouble()/100)
        onView(withId(R.id.edit_text_amount)).perform(ViewActions.typeText(stringToBeTyped), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.button_pay)).perform(ViewActions.click())
        Thread.sleep(1500);
        onView(withId(R.id.button_go_to_info)).perform(ViewActions.click())
        Thread.sleep(1500);
        onView(withId(R.id.text_amount)).check(ViewAssertions.matches(withText(containsString(stringToBeShown))))
    }

    @Test
    fun testCustomerInfoFragmentPaymentHistory() {
        val stringToBeTyped = "123"
        val stringToBeShown = DecimalFormat("#.##").format(stringToBeTyped.toDouble()/100)
        onView(withId(R.id.edit_text_amount)).perform(ViewActions.typeText(stringToBeTyped), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.button_pay)).perform(ViewActions.click())
        Thread.sleep(1500);
        onView(withId(R.id.button_go_to_info)).perform(ViewActions.click())
        Thread.sleep(1500);
        onView(withId(R.id.button_proceed)).perform(ViewActions.click())
        Thread.sleep(1500);
        onView(withId(R.id.history_recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText(containsString(stringToBeShown))))))
    }
}

fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?>? {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}