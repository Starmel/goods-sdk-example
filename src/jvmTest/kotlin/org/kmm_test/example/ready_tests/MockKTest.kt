package org.kmm_test.example.ready_tests

import io.mockk.coEvery
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.kmm_test.example.test_setup.override
import org.kmm_test.example.test_setup.overrideMock
import org.kmm_test.example.test_setup.setupSDK
import org.kmm_test.library.feature.cart.cart
import org.kmm_test.library.feature.cart.data.LocalCartDataSource
import org.kmm_test.library.feature.cart.data.RemoteCartDataSource
import org.kmm_test.library.feature.cart.data.in_memory.InMemoryLocalCartDataSource
import org.kmm_test.library.feature.cart.data.model.LocalCartProduct
import org.kmm_test.library.feature.cart.data.model.RemoteCartModel
import org.kmm_test.library.sdk.GoodsSDK
import kotlin.test.assertEquals

class MockKTest {

    @Test
    fun mockExample(): Unit = runBlocking {

        // ----------------------------- Prepare -----------------------------

        setupSDK {
            overrideMock<RemoteCartDataSource> {
                coEvery { syncProducts(eq(listOf(LocalCartProduct("example", 1)))) } returns RemoteCartModel(
                    productsCount = 1, totalPrice = 200
                )
            }
        }

        // -----------------------------   Do    -----------------------------

        GoodsSDK.cart.addProduct("example", 1)

        val remoteCart = GoodsSDK.cart.syncLocalProducts()

        // -----------------------------  Check  -----------------------------

        assertEquals(1, remoteCart.productsCount)
        assertEquals(200, remoteCart.totalPrice)
    }

    @Test
    fun spyExample(): Unit = runBlocking {

        // ----------------------------- Prepare -----------------------------

        val localCartDataSource = spyk(InMemoryLocalCartDataSource(), recordPrivateCalls = true)

        setupSDK {
            override<LocalCartDataSource> { localCartDataSource }
        }

        // -----------------------------   Do    -----------------------------

        GoodsSDK.cart.addProduct("example", 1)
        val products = GoodsSDK.cart.getProducts()

        // -----------------------------  Check  -----------------------------


        verify {
            localCartDataSource.setLocalProducts(any())
            localCartDataSource["logProducts"].invoke(products)
            localCartDataSource setProperty "hasLogged" value true

            // Not working for private calls:
            // localCartDataSource["logProducts"].invoke(any())
            // localCartDataSource invoke "products" invoke products
        }
    }

    @Test
    fun slotExample(): Unit = runBlocking {

        // ----------------------------- Prepare -----------------------------

        setupSDK {
            overrideMock<RemoteCartDataSource> {
                val productsSlot = slot<List<LocalCartProduct>>()
                coEvery { syncProducts(capture(productsSlot)) } answers {
                    RemoteCartModel(
                        productsCount = 1, totalPrice = productsSlot.captured.first().price * 2
                    )
                }
            }
        }

        // -----------------------------   Do    -----------------------------

        GoodsSDK.cart.addProduct("example", 100)

        val remoteCart = GoodsSDK.cart.syncLocalProducts()

        // -----------------------------  Check  -----------------------------

        assertEquals(1, remoteCart.productsCount)
        assertEquals(200, remoteCart.totalPrice)
    }
}