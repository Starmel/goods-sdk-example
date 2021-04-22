package org.kmm_test.library.sdk

import org.kmm_test.library.common.ktor.ktorModule
import org.kmm_test.library.feature.cart.cartModule
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object GoodsSDK {

    internal val di: DirectDI
        get() = requireNotNull(_di)
    private var _di: DirectDI? = null

    // Used by unit-tests
    internal var setupTestDependencies: DITestSetup? = null

    fun setup() {
        _di = DI {
            importAll(
                ktorModule,
                cartModule,
            )
            setupTestDependencies?.invoke(this)
        }.direct
    }
}

typealias DITestSetup = (DI.MainBuilder.() -> Unit)