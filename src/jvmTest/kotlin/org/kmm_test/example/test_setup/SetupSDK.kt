package org.kmm_test.example.test_setup

import io.mockk.mockk
import org.kmm_test.library.sdk.DITestSetup
import org.kmm_test.library.sdk.GoodsSDK
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider

fun setupSDK(diSetup: DITestSetup) {
    GoodsSDK.setupTestDependencies = diSetup
    GoodsSDK.setup()
}

internal inline fun <reified T : Any> DI.MainBuilder.override(crossinline constructor: () -> T) {
    bind<T>(overrides = true) with provider { constructor.invoke() }
}

internal inline fun <reified T : Any> DI.MainBuilder.overrideMock(crossinline constructor: T.() -> Unit) {
    bind<T>(overrides = true) with provider {
        mockk(block = constructor)
    }
}