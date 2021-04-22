package org.kmm_test.library.common.ktor.feature.error_handler

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.kmm_test.library.common.ktor.feature.error_handler.model.KtorApiErrorModel

class ApiErrorHandler {

    @KtorExperimentalAPI
    companion object Feature : HttpClientFeature<Unit, ApiErrorHandler> {

        override val key: AttributeKey<ApiErrorHandler> = AttributeKey("ApiErrorHandler")

        override fun prepare(block: Unit.() -> Unit): ApiErrorHandler = ApiErrorHandler()

        override fun install(feature: ApiErrorHandler, scope: HttpClient) {

            scope.feature(HttpSend)?.intercept { call, httpRequestBuilder ->
                if (call.response.status == HttpStatusCode.BadRequest) {
                    try {
                        val ktorApiErrorModel = Json.decodeFromString(
                            deserializer = KtorApiErrorModel.serializer(),
                            string = call.response.readText()
                        )
                        throw ktorApiErrorModel.toApiErrorModel()

                    } catch (parseException: Exception) {
                        // Proceed error if required
                        throw parseException
                    }
                }
                call
            }
        }
    }
}
