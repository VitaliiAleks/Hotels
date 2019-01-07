package hotels.zumme.my.hotels.api

import hotels.zumme.my.hotels.extensions.HotelConstants
import hotels.zumme.my.hotels.models.request.RequestGetCityList
import hotels.zumme.my.hotels.models.request.RequestGetSearchId
import hotels.zumme.my.hotels.models.response.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface HotelService {

    @POST("suggest")
    fun getCityList(@Body requestGetCityList: RequestGetCityList): Observable<ResponseHotelsSagests>

    @POST("hotel/search")
    fun getSearchId(@Body requestGetSearchId: RequestGetSearchId): Observable<ResponseSearchId>

    @GET("hotel/fetch")
    fun getHotels(@Query("search_id") searchId: String,
                  @Query("page") page: Int): Observable<ResponseHotels>

    @GET("hotel/details")
    fun getHotelDetails(@Query("search_id") searchId: String,
                        @Query("hotel_id") hotelId: Int): Observable<ResponseHotelDetails>

    @GET("hotel/prices")
    fun getRoomPrices(@Query("search_id") searchId: String,
                      @Query("hotel_id") hotelId: Int): Observable<ResponsePrice>

    companion object {
        fun create(): HotelService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HotelConstants.BASE_URL)
                    .build()

            return retrofit.create(HotelService::class.java)
        }
    }
}