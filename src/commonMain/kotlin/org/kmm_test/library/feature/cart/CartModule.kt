package org.kmm_test.library.feature.cart

import org.kmm_test.library.feature.cart.data.CartRepository
import org.kmm_test.library.feature.cart.data.LocalCartDataSource
import org.kmm_test.library.feature.cart.data.RemoteCartDataSource
import org.kmm_test.library.feature.cart.data.in_memory.InMemoryLocalCartDataSource
import org.kmm_test.library.feature.cart.data.ktor.KtorRemoteCartDataSource
import org.kmm_test.library.feature.cart.use_case.AddProductUseCase
import org.kmm_test.library.feature.cart.use_case.GetProductsUseCase
import org.kmm_test.library.feature.cart.use_case.GetRemoteCartUseCase
import org.kmm_test.library.feature.cart.use_case.SyncLocalProductsUseCase
import org.kmm_test.library.sdk.GoodsSDK
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import kotlin.native.concurrent.ThreadLocal

val cartModule = DI.Module("cartModule") {

    // Data Source
    bind<LocalCartDataSource>() with singleton { InMemoryLocalCartDataSource() }
    bind<RemoteCartDataSource>() with provider { KtorRemoteCartDataSource(instance()) }

    // Repository
    bind<CartRepository>() with provider { CartRepository(instance(), instance()) }

    // Use Case
    bind<AddProductUseCase>() with provider { AddProductUseCase(instance()) }
    bind<GetProductsUseCase>() with provider { GetProductsUseCase(instance()) }
    bind<GetRemoteCartUseCase>() with provider { GetRemoteCartUseCase(instance()) }
    bind<SyncLocalProductsUseCase>() with provider { SyncLocalProductsUseCase(instance()) }
}

@ThreadLocal
object CartModule {

    val addProduct: AddProductUseCase
        get() = GoodsSDK.di.instance()

    val getProducts: GetProductsUseCase
        get() = GoodsSDK.di.instance()

    val getRemoteCart: GetRemoteCartUseCase
        get() = GoodsSDK.di.instance()

    val syncLocalProducts: SyncLocalProductsUseCase
        get() = GoodsSDK.di.instance()
}

val GoodsSDK.cart: CartModule
    get() = CartModule