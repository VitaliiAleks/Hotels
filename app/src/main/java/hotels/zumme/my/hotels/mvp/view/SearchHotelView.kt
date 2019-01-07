package hotels.zumme.my.hotels.mvp

import com.arellomobile.mvp.MvpView
import hotels.zumme.my.hotels.models.response.ResponseHotelsSearchPlace

interface SearchHotelView :MvpView {

    fun showErrorMessage(error: String?)

    fun showCitiesList(citiesList: List<ResponseHotelsSearchPlace>?)

    fun startHotelListActivity(searchId: String)
}