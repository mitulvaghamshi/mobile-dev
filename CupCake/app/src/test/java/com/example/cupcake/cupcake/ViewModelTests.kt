package com.example.cupcake.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: OrderViewModel

    @Before
    fun setup() {
        viewModel = OrderViewModel()
    }

    @Test
    fun quantity_twelve_cupcakes() {
        viewModel.setQuantity(12)
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun price_six_cupcakes_with_same_day_delivery() {
        viewModel.setQuantity(6)
        viewModel.price.observeForever {}
        assertEquals(6, viewModel.quantity.value)
        assertEquals("$15.00", viewModel.price.value)
    }

    @Test
    fun price_six_cupcakes_with_other_day_delivery() {
        viewModel.setQuantity(6)
        viewModel.setDate(viewModel.dateOptions[2])
        viewModel.price.observeForever {}
        assertEquals(6, viewModel.quantity.value)
        assertEquals("$12.00", viewModel.price.value)
    }

    @Test
    fun set_vanilla_flavor() {
        assertEquals(true, viewModel.hasNoFlavorSet())
        viewModel.setFlavor("Vanilla")
        assertEquals("Vanilla", viewModel.flavor.value)
        assertEquals(false, viewModel.hasNoFlavorSet())
    }

    @Test
    fun cancel_order() {
        viewModel.setQuantity(12)
        viewModel.setFlavor("Vanilla")
        viewModel.setDate(viewModel.dateOptions[2])
        viewModel.price.observeForever {}
        assertEquals("$24.00", viewModel.price.value)

        viewModel.resetOrder()
        assertEquals(0, viewModel.quantity.value)
        assertEquals("$0.00", viewModel.price.value)
        assertEquals("", viewModel.flavor.value)
        assertEquals(viewModel.dateOptions[0], viewModel.date.value)
    }
}
