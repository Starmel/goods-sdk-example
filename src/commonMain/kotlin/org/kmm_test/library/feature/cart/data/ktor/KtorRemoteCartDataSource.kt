package org.kmm_test.library.feature.cart.data.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.kmm_test.library.feature.cart.data.RemoteCartDataSource
import org.kmm_test.library.feature.cart.data.ktor.model.KtorRemoteCartModel
import org.kmm_test.library.feature.cart.data.model.LocalCartProduct
import org.kmm_test.library.feature.cart.data.model.RemoteCartModel

class KtorRemoteCartDataSource(
    private val client: HttpClient
) : RemoteCartDataSource {

    override suspend fun getRemoteCart(): RemoteCartModel {
        val call = client.get<HttpStatement> {
            url { path("remote_cart") }
        }
        val remoteModel = Json.decodeFromString(KtorRemoteCartModel.serializer(), call.receive())
        return RemoteCartModel(
            productsCount = remoteModel.productsCount,
            totalPrice = remoteModel.totalPrice
        )
    }

    override suspend fun syncProducts(localProducts: List<LocalCartProduct>): RemoteCartModel {
        return client.get<KtorRemoteCartModel> {
            url {
                path("sync_cart")
                parameter("products", localProducts.joinToString(";") { "name=${it.name}" })
            }
        }.let {
            RemoteCartModel(
                productsCount = it.productsCount,
                totalPrice = it.totalPrice
            )
        }
    }
}