package org.kmm_test.library.feature.cart.use_case

import org.kmm_test.library.feature.cart.data.CartRepository
import org.kmm_test.library.feature.cart.data.model.LocalCartProduct

class GetProductsUseCase(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(): List<LocalCartProduct> {
        return cartRepository.getProducts()
    }
}