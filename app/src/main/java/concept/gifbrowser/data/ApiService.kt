package concept.gifbrowser.data

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Giphy API retrofit service
 */
interface ApiService {

    @GET("v1/gifs/trending")
    fun getImages(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<Response<GifResponse>>

}