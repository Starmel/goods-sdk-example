package org.kmm_test.library.feature.cart.use_case

import org.kmm_test.library.feature.cart.data.CartRepository
import org.kmm_test.library.feature.cart.data.model.RemoteCartModel

class SyncLocalProductsUseCase(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(): RemoteCartModel {
        return cartRepository.syncLocalProductsCart()
    }
}