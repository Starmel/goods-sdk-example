package org.kmm_test.library.feature.cart.data.ktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class KtorRemoteCartModel(
    @SerialName("products_size")
    val productsCount: Int,
    @SerialName("total_price_penny")
    val totalPrice: Int
)