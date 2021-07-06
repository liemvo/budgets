package com.vad.budgets.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.MainActivity
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    val mainActivityScreen = MainActivityScreen()

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun defaultSelectedTab() {
        mainActivityScreen {
            categoriesView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText("Categories") }
            }
            transactionsView {
                isDisplayed()
                isSelected()
                hasDescendant { withText("Transactions") }
            }
            budgetsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText("Budgets") }
            }
            actionView {
                hasDescendant { withText("Transactions") }
            }
        }
    }

    @Test
    fun selectCategoriesTab() {
        mainActivityScreen {
            categoriesView.perform { click() }

            categoriesView {
                isDisplayed()
                isSelected()
                hasDescendant { withText("Categories") }
            }
            transactionsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText("Transactions") }
            }
            budgetsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText("Budgets") }
            }
            actionView {
                hasDescendant { withText("Categories") }
            }
        }
    }

    @Test
    fun selectBudgetsTab() {
        mainActivityScreen {
            budgetsView.perform { click() }

            categoriesView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText("Categories") }
            }
            transactionsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText("Transactions") }
            }
            budgetsView {
                isDisplayed()
                isSelected()
                hasDescendant { withText("Budgets") }
            }
            actionView {
                hasDescendant { withText("Budgets") }
            }
        }
    }
}