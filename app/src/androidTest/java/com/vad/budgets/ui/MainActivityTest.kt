package com.vad.budgets.ui

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.internal.BaselineLayout
import com.vad.budgets.MainActivity
import com.vad.budgets.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun defaultSelectedTab() {
        val scenario = activityScenarioRule.scenario

        onView(withId(R.id.navigation_categories)).check(matches(isNotSelected()))
        onView(withId(R.id.navigation_transactions)).check(matches(isSelected()))
        onView(withId(R.id.navigation_budgets)).check(matches(isNotSelected()))

//        onTextViewWithParentId(R.id.navigation_categories).check(matches(withText("Categories")))
//        onTextViewWithParentId(R.id.navigation_transactions).check(matches(withText("Transactions")))
//        onTextViewWithParentId(R.id.navigation_budgets).check(matches(withText("Budgets")))
        verifyTitle("Transactions")
    }

    private fun verifyTitle(text: String) {
        onTextViewWithParentId(R.id.tool_bar).check(matches(withText(text)))
    }

    private fun onBaselineLayoutWithParent(id: Int) = onViewWithParentId(id, BaselineLayout::class.java)

    private fun onTextViewWithParentId(id: Int) = onViewWithParentId(id, TextView::class.java)

    private fun <T: View> onViewWithParentId(id: Int, type: Class<T>) = onView(allOf(instanceOf(type), withParent(withId(id))))
}