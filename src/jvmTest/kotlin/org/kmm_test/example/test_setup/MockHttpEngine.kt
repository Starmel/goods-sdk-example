package org.kmm_test.example.test_setup

import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import org.kmm_test.library.common.ktor.HttpEngineFactory
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider

class RespondBuilder {

    val pathHandlerMap = mutableMapOf<String, MockRequestHandler>()

    fun addHandler(path: String, handler: MockRequestHandler) {
        pathHandlerMap[path] = handler
    }
}

fun DI.MainBuilder.mockHttp(respondBuilder: RespondBuilder.() -> Unit) {

    val builder = RespondBuilder()
        .apply(respondBuilder)

    bind<HttpEngineFactory>(overrides = true) with provider {
        object : HttpEngineFactory() {
            override fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> {
                return MockEngine.config {
                    addHandler { request ->
                        val handlers = builder.pathHandlerMap.filter {
                            request.url.fullPath.contains(it.key)
                        }
                        assert(handlers.size == 1)
                        val handler = handlers.entries.first().value
                        handler(request)
                    }
                }
            }
        }
    }
}