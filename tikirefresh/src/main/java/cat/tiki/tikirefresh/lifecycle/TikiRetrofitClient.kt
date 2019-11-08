package cat.tiki.tikirefresh

import cat.tiki.tikirefresh.lifecycle.HttpsUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TikiRetrofitClient {

    fun getRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
//                .add(LocalDateTimeAdapter())
//                .add(MultipleFormatsDateAdapter())
                .build()
        return Retrofit.Builder()
                .baseUrl(HTTPS_API_HOST)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(TikiLiveDataCallAdapterFactory())
                .callFactory(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {

        val socketFactory = HttpsUtils.getSslSocketFactory(null, null, null)
        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor()
            .sslSocketFactory(socketFactory.sSLSocketFactory, socketFactory.trustManager)
            .build()
        return okHttpClient
    }


    companion object {
        private var HTTPS_API_HOST = "https://api.zaozuo.com"
    }
}