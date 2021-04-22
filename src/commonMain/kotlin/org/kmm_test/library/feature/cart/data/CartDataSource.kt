package org.kmm_test.library.feature.cart.data

import org.kmm_test.library.feature.cart.data.model.LocalCartProduct
import org.kmm_test.library.feature.cart.data.model.RemoteCartModel

interface RemoteCartDataSource {

    suspend fun getRemoteCart(): RemoteCartModel
    suspend fun syncProducts(localProducts: List<LocalCartProduct>): RemoteCartModel
}

interface LocalCartDataSource {

    fun getLocalProducts(): List<LocalCartProduct>
    fun setLocalProducts(products: List<LocalCartProduct>)
}
