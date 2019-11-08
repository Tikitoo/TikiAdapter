package cat.tiki.tikirefresh

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class TikiLiveDataCallAdapterFactory: CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val observableType =
            getParameterUpperBound(0, returnType as ParameterizedType) as? ParameterizedType
                ?: throw IllegalArgumentException("resource must be parameterized")
        return TikiLiveDataCallAdapter<Any>(getParameterUpperBound(0, observableType))
    }
}