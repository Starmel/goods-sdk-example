package org.kmm_test.library.feature.cart.data.in_memory

import org.kmm_test.library.feature.cart.data.LocalCartDataSource
import org.kmm_test.library.feature.cart.data.model.LocalCartProduct

class InMemoryLocalCartDataSource : LocalCartDataSource {

    private var products = emptyList<LocalCartProduct>()
    private var hasLogged = false

    override fun getLocalProducts(): List<LocalCartProduct> {
        return products
    }

    override fun setLocalProducts(products: List<LocalCartProduct>) {
        this.products = products.also(this::logProducts)
    }

    private fun logProducts(products: List<LocalCartProduct>) {
        println(products)
        hasLogged = true
    }
}