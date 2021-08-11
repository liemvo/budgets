package com.vad.budgets.ui.common.diff


import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CommonDiffUtilTest {
    private data class DiffModel(override val diffIdentify: Int, val name: String) : DiffInterface

    @Test
    fun hasTheSameItem() {
        val model1 = DiffModel(1, "hello")
        val model2 = DiffModel(1, "abc")
        val diffUtil = CommonDiffUtil(listOf(model1), listOf(model2))
        val result = diffUtil.areItemsTheSame(0, 0)
        assertTrue(result)
    }

    @Test
    fun hasNotTheSameItem() {
        val model1 = DiffModel(1, "hello")
        val model2 = DiffModel(2, "abc")
        val diffUtil = CommonDiffUtil(listOf(model1), listOf(model2))
        val result = diffUtil.areItemsTheSame(0, 0)
        assertFalse(result)
    }

    @Test
    fun hasTheSameContent() {
        val model1 = DiffModel(1, "hello")
        val model2 = DiffModel(1, "hello")
        val diffUtil = CommonDiffUtil(listOf(model1), listOf(model2))
        val result = diffUtil.areContentsTheSame(0, 0)
        assertTrue(result)
    }

    @Test
    fun hasNotTheSameContent() {
        val model1 = DiffModel(1, "hello")
        val model2 = DiffModel(1, "abc")
        val diffUtil = CommonDiffUtil(listOf(model1), listOf(model2))
        val result = diffUtil.areContentsTheSame(0, 0)
        assertFalse(result)
    }
}
