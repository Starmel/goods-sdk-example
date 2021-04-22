package org.kmm_test.library.common.ktor

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual open class HttpEngineFactory actual constructor() {

    actual open fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}