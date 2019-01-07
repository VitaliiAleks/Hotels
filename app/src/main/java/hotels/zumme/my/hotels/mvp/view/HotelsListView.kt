package hotels.zumme.my.hotels.mvp

import com.arellomobile.mvp.MvpView
import hotels.zumme.my.hotels.models.hotel.HotelListItem

interface HotelsListView :MvpView{

    fun showErrorMessage(error: String?)

    fun showNotFoundMessage()

    interface IHotelClickListener {
        fun hotelClicked(item: HotelListItem)
    }
}