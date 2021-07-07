package com.vad.budgets.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.MainActivity
import com.vad.budgets.R
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
                hasDescendant { withText(R.string.title_categories) }
            }
            transactionsView {
                isDisplayed()
                isSelected()
                hasDescendant { withText(R.string.title_transactions) }
            }
            budgetsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText(R.string.title_budgets) }
            }
            hasTitle(R.string.title_transactions)
        }
    }

    @Test
    fun selectCategoriesTab() {
        mainActivityScreen {
            categoriesView.perform { click() }

            categoriesView {
                isDisplayed()
                isSelected()
                hasDescendant { withText(R.string.title_categories) }
            }
            transactionsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText(R.string.title_transactions) }
            }
            budgetsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText(R.string.title_budgets) }
            }
            hasTitle(R.string.title_categories)
        }
    }

    @Test
    fun selectBudgetsTab() {
        mainActivityScreen {
            budgetsView.perform { click() }

            categoriesView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText(R.string.title_categories) }
            }
            transactionsView {
                isDisplayed()
                isNotSelected()
                hasDescendant { withText(R.string.title_transactions) }
            }
            budgetsView {
                isDisplayed()
                isSelected()
                hasDescendant { withText(R.string.title_budgets) }
            }
            hasTitle(R.string.title_budgets)
        }
    }
}