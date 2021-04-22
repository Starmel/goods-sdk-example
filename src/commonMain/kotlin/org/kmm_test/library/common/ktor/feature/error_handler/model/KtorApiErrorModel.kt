package org.kmm_test.library.common.ktor.feature.error_handler.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class KtorApiErrorModel(
    @SerialName("code")
    val statusCode: Int,
    @SerialName("message")
    val message: String
) {

    fun toApiErrorModel(): ApiErrorModel {
        return ApiErrorModel(statusCode, message)
    }
}