package com.vad.budgets.ui.category

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vad.budgets.MainActivity
import com.vad.budgets.R
import com.vad.budgets.ui.MainActivityScreen
import kotlinx.coroutines.selects.select
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Currency

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
    
        categoriesScreen {
            categoriesRecyclerView.isNotDisplayed()
            noCategoryTextView.isDisplayed()
            addMenu.perform { click() }
        }
    }

    @Test
    fun navigateToCategoryDetails() {
        mainActivityScreen {
            categoriesView.perform { click() }
            hasTitle(R.string.title_categories)
        }
        categoriesScreen.addMenu.click()

        mainActivityScreen.hasTitle(R.string.title_add_category)
        /*categoryDetailsScreen{
            currencySpinner.open()
            currencySpinner.childAt<CurrencyItem>(0){
                titleText.hasText("NZD")
            }
            currencySpinner.childAt<CurrencyItem>(1){
                titleText.hasText("USD")
            }
        }*/
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
                childAt<CurrencyItem>(1){
                    click()
                }
            }
            statusSwitch.click()
            
            saveMenu.click()
            progress {
                isDisplayed()
            }
            categoriesScreen {
                categoriesRecyclerView.isDisabled()
                noCategoryTextView.isNotDisplayed()
            }
        }
    }
}
