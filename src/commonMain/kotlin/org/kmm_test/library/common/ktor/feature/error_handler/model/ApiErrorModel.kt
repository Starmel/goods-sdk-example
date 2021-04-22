package org.kmm_test.library.common.ktor.feature.error_handler.model

class ApiErrorModel(val statusCode: Int, val apiErrorMessage: String) : Error()