package org.kmm_test.library.common.ktor

import io.ktor.client.engine.*

expect open class HttpEngineFactory constructor() {

    fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig>
}