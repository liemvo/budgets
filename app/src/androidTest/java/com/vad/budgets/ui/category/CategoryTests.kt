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
    fun verifyEmptyCategoriesScreen() {
        mainActivityScreen {
            categoriesView.perform { click() }
            hasTitle(R.string.title_categories)
        }
    }

    @Test
    fun addNewCategory() {
        mainActivityScreen {
            categoriesView.perform { click() }
            hasTitle(R.string.title_categories)
        }
        categoriesScreen.addMenu.click()

        mainActivityScreen.hasTitle(R.string.title_add_category)

        categoryDetailsScreen {
            progress.isNotDisplayed()
            categoryNameEditText.typeText("Financial")
            defaultValueEditText.typeText("450.0")
            currencySpinner {
                open()
                childAt<CurrencyItem>(1) {
                    click()
                }

                hasText("USD")
            }

            statusSwitch.click()
            saveMenu.click()

            categoriesScreen {
                categoriesRecyclerView {
                    isVisible()
                    hasSize(1)

                    childAt<CategoryItem>(0) {
                        nameTextView.hasText("Financial")
                        statusSwitch.isNotSelected()
                        defaultValue.hasText("450 USD")
                    }
                }
                noCategoryTextView.isGone()
            }
        }
    }
}
