package cat.tiki.tikirefresh

import retrofit2.Response

internal const val UNKNOWN_CODE = -1

sealed class TikiApiResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): TikiApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                ApiErrorResponse(response.code(), response.errorBody()?.string()?:response.message())
            }
        }

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(errorCode, error.message ?: "Unknown Error!")
        }
    }
}

class  ApiEmptyResponse<T> : TikiApiResponse<T>()
data class ApiErrorResponse<T>(val errorCode: Int, val errorMessage: String): TikiApiResponse<T>()
data class ApiSuccessResponse<T>(val body: T): TikiApiResponse<T>()