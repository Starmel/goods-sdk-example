package org.kmm_test.library.common.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual open class HttpEngineFactory actual constructor() {

    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}