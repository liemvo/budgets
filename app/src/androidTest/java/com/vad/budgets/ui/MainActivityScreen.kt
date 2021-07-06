package com.vad.budgets.ui

import com.vad.budgets.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

class MainActivityScreen : Screen<MainActivityScreen>() {
    val categoriesView = KView { withId(R.id.navigation_categories) }
    val transactionsView = KView { withId(R.id.navigation_transactions) }
    val budgetsView = KView { withId(R.id.navigation_budgets) }

    val actionView = KView { withId(R.id.tool_bar) }
}