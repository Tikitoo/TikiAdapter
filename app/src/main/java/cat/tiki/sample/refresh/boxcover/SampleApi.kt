package cat.tiki.sample.refresh.boxcover

import androidx.lifecycle.LiveData
import cat.tiki.tikirefresh.TikiApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yifa Liang on 2019-11-07.
 */
interface SampleApi {
    @GET("/app/subject")
    fun getTopicList(@Query("page") page:Int = 1,
                     @Query("tagId")tagId:Int): LiveData<TikiApiResponse<KotlinSubject>>


}