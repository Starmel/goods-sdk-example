package org.kmm_test.example.ready_tests

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.kmm_test.library.feature.cart.cart
import org.kmm_test.library.sdk.GoodsSDK
import kotlin.test.assertEquals

class SimpleTest {

    @Test
    fun test(): Unit = runBlocking {

        // ----------------------------- Prepare -----------------------------

        GoodsSDK.setup()

        // -----------------------------   Do    -----------------------------

        GoodsSDK.cart.addProduct("Колбаска", 2000)
        val products = GoodsSDK.cart.getProducts()

        // -----------------------------  Check  -----------------------------

        val localCartProduct = products.first()
        assertEquals("Колбаска", localCartProduct.name)
        assertEquals(2000, localCartProduct.price)
    }
}