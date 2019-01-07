package hotels.zumme.my.hotels.mvp


import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import hotels.zumme.my.hotels.api.HotelService
import hotels.zumme.my.hotels.models.request.RequestGetCityList
import hotels.zumme.my.hotels.models.request.RequestGetSearchId
import hotels.zumme.my.hotels.models.response.ResponseHotelsSagests
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*



@InjectViewState
class SearchHotelPresenter : MvpPresenter<SearchHotelView>() {

    private val dateFormat = "dd.MM.yyyy"
    private val sdf: SimpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
    private lateinit var arrivalDate: Date
    private lateinit var departureDate: Date
    private var cityId: String? = null
    private lateinit var citiesObservable: Observable<ResponseHotelsSagests>

    fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        arrivalDate = calendar.time
        calendar.add(Calendar.DATE, ONE_DAY)
        departureDate = calendar.time
    }

    fun getArrivalDate(): Date {
        return arrivalDate
    }

    fun getDepartureDate(): Date {
        return departureDate
    }

    fun getFormattedArrivalDate(): String {
        return sdf.format(arrivalDate)
    }

    fun getFormattedDepartureDate(): String {
        return sdf.format(departureDate)
    }

    fun setArrivalTime(newArrivalDate: Date) {
        arrivalDate = newArrivalDate
        if (arrivalDate.after(departureDate) || arrivalDate == departureDate) {
            departureDate.time = arrivalDate.time + ONE_DAY_MILISECONDS
        }
    }

    fun setDepartureTime(newDepartureDate: Date) {
        departureDate = newDepartureDate
        if (arrivalDate.after(departureDate) || arrivalDate == departureDate) {
            departureDate.time = arrivalDate.time
        }
    }

    fun getCityList(query: String) {
        citiesObservable = HotelService.create().getCityList(RequestGetCityList(search = query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        citiesObservable.subscribe(
                { result -> viewState.showCitiesList(result.suggestions) },
                { error -> viewState.showErrorMessage(error.message) }
        )
    }

    override fun onDestroy() {
            citiesObservable.unsubscribeOn(Schedulers.io())
    }

    fun setCityId(cityId: Long) {
        this.cityId = cityId.toString()
    }

    fun getSearchID() {
        val dateFormatter = SimpleDateFormat(SEARCH_DATE_FORMAT, Locale.US)
        HotelService.create().getSearchId(RequestGetSearchId(place_id = cityId,
                checkin_date = dateFormatter.format(arrivalDate),
                checkout_date = dateFormatter.format(departureDate))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> viewState.startHotelListActivity(result.search_id) },
                        { error -> viewState.showErrorMessage(error.message) }
                )
    }

    companion object {
        const val DEFAULT_CITY_ID = 0L
        const val ONE_DAY_MILISECONDS: Long = 1000 * 60 * 60 * 24
        private const val ONE_DAY = 1
        private const val SEARCH_DATE_FORMAT = "yyyy-MM-dd"
    }
}