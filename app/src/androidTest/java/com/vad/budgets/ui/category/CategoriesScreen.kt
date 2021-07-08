package com.vad.budgets.ui.category

import android.view.View
import androidx.test.espresso.DataInteraction
import com.vad.budgets.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.list.KAdapterItem
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.switch.KSwitch
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

class CategoriesScreen : Screen<CategoriesScreen>() {
    val categoriesRecyclerView: KRecyclerView =
        KRecyclerView({ withId(R.id.categories) }, itemTypeBuilder = {
            itemType(::CategoryItem)
        })
    val noCategoryTextView = KTextView { withText(R.string.no_categories_message) }
    val addMenu = KView { withId(R.id.action_add) }
}

class CategoryItem(parent: Matcher<View>) : KRecyclerItem<CategoryItem>(parent) {
    val nameTextView = KTextView { withId(R.id.category_name) }
    val statusSwitch = KSwitch { withId(R.id.status_switch) }
    val defaultValue = KTextView { withId(R.id.default_value) }
}

class CategoryDetailsScreen : Screen<CategoryDetailsScreen>() {
    val categoryNameEditText = KEditText { withId(R.id.category_name) }
    val defaultValueEditText = KEditText { withId(R.id.category_default) }
    val currencySpinner = KSpinner(
        { withId(R.id.currency_spinner) },
        itemTypeBuilder = { itemType(::CurrencyItem) }
    )

    val statusSwitch = KSwitch { withId(R.id.status_switch) }
    val progress = KProgressBar{ withId(R.id.progress_circular)}
    
    val saveMenu = KView { withId(R.id.action_save) }
}

class CurrencyItem(data: DataInteraction) : KAdapterItem<CurrencyItem>(data) {
    val titleText = KTextView { withId(R.id.title) }
}
