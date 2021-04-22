package org.kmm_test.library.feature.cart.use_case

import org.kmm_test.library.feature.cart.data.CartRepository
import org.kmm_test.library.feature.cart.data.model.LocalCartProduct

class AddProductUseCase(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(name: String, price: Int) {
        cartRepository.addProduct(LocalCartProduct(name, price))
    }
}