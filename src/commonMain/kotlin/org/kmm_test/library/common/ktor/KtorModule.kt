package org.kmm_test.library.common.ktor

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import io.ktor.util.*
import org.kmm_test.library.common.ktor.feature.error_handler.ApiErrorHandler
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@KtorExperimentalAPI
internal val ktorModule = DI.Module(
    name = "ktorModule",
    init = {

        bind<HttpEngineFactory>() with singleton { HttpEngineFactory() }

        bind<HttpClient>() with singleton {

            val engine = instance<HttpEngineFactory>().createEngine()

            HttpClient(engine) {

                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }

                install(ApiErrorHandler)

                defaultRequest {
                    url {
                        this.host = "example.com"
                        this.protocol = URLProtocol.HTTP
                    }
                }
            }
        }
    }
)