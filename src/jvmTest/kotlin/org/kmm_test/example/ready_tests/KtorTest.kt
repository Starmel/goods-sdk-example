package org.kmm_test.example.ready_tests

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.kmm_test.example.test_setup.mockHttp
import org.kmm_test.example.test_setup.setupSDK
import org.kmm_test.library.common.ktor.feature.error_handler.model.ApiErrorModel
import org.kmm_test.library.feature.cart.cart
import org.kmm_test.library.sdk.GoodsSDK
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class KtorTest {

    @Test
    fun successful_handle_error(): Unit = runBlocking {

        // ----------------------------- Prepare -----------------------------

        setupSDK {
            mockHttp {
                addHandler("remote_cart") {
                    respondError(
                        HttpStatusCode.BadRequest,/*language=json*/
                        """
                        {
                          "code" : 900,
                          "message" : "unavailable"
                        }
                    """.trimIndent()
                    )
                }
            }
        }

        // -----------------------------   Do    -----------------------------

        val throwable = assertFails {
            GoodsSDK.cart.getRemoteCart()
        }

        // -----------------------------  Check  -----------------------------

        assertTrue(throwable is ApiErrorModel)
        assertEquals(throwable.apiErrorMessage, "unavailable")
        assertEquals(throwable.statusCode, 900)
    }

    @Test
    fun successful_get_response(): Unit = runBlocking {

        // ----------------------------- Prepare -----------------------------

        setupSDK {
            mockHttp {
                addHandler("remote_cart") {
                    /*language=json*/
                    respond(
                        """
                        {
                          "products_size" : 900,
                          "total_price_penny" : 1000
                        }
                    """.trimIndent(),
                    )
                }
            }
        }

        // -----------------------------   Do    -----------------------------

        val remoteCart = GoodsSDK.cart.getRemoteCart()

        // -----------------------------  Check  -----------------------------

        assertEquals(900, remoteCart.productsCount)
        assertEquals(1000, remoteCart.totalPrice)
    }
}