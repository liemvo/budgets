package com.vad.budgets.ui.category

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.MainActivity
import com.vad.budgets.R
import com.vad.budgets.ui.MainActivityScreen
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryTests {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    val mainActivityScreen = MainActivityScreen()

    val categoriesScreen = CategoriesScreen()

    val categoryDetailsScreen = CategoryDetailsScreen()

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun navigateToCategoryDetails() {
        mainActivityScreen {
            categoriesView.perform { click() }
            hasTitle(R.string.title_categories)
        }

        categoriesScreen {
            categoriesRecyclerView.isNotDisplayed()
            noCategoryTextView.isDisplayed()
            addMenu.perform { click() }
        }

        mainActivityScreen.hasTitle(R.string.title_add_category)
    }
}
