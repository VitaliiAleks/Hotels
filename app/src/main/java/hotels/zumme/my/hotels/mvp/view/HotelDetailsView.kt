package hotels.zumme.my.hotels.mvp

import com.arellomobile.mvp.MvpView
import hotels.zumme.my.hotels.models.hotel.Price

interface HotelDetailsView :MvpView{

    fun showErrorMessage(error: String?)

    fun showHotelName(hotelName: String)

    fun showHotelRate(hotelRate: Int)

    fun showHotelPhotos(photosList: List<String>)

    fun showPrice(priceList: List<Price>)

    fun showServices(service: String)

    fun showDescription(description: String)

    fun showLocation(location: String)
}