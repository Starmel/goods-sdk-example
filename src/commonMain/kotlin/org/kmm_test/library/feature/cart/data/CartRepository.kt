package org.kmm_test.library.feature.cart.data

import org.kmm_test.library.feature.cart.data.model.LocalCartProduct
import org.kmm_test.library.feature.cart.data.model.RemoteCartModel

class CartRepository(
    private val localCartDataSource: LocalCartDataSource,
    private val remoteCartDataSource: RemoteCartDataSource
) {

    fun addProduct(localCartProduct: LocalCartProduct) {
        val products = localCartDataSource.getLocalProducts() + localCartProduct
        localCartDataSource.setLocalProducts(products)
    }

    fun getProducts(): List<LocalCartProduct> {
        return localCartDataSource.getLocalProducts()
    }

    suspend fun getRemoteCart(): RemoteCartModel {
        return remoteCartDataSource.getRemoteCart()
    }

    suspend fun syncLocalProductsCart(): RemoteCartModel {
        val localProducts = localCartDataSource.getLocalProducts()
        return remoteCartDataSource.syncProducts(localProducts)
    }
}