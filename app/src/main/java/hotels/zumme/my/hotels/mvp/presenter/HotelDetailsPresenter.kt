package hotels.zumme.my.hotels.mvp


import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import hotels.zumme.my.hotels.api.HotelService
import hotels.zumme.my.hotels.models.response.ResponseHotelDetails
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class HotelDetailsPresenter : MvpPresenter<HotelDetailsView>() {

    private lateinit var observerHotelDetails: Observable<ResponseHotelDetails>

    fun getHotelDetails(searchId: String, hotelId: Int) {
        observerHotelDetails = HotelService.create().getHotelDetails(searchId, hotelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        observerHotelDetails.subscribe(
                        { result ->
                            with(viewState) {
                                showHotelName(result.details.name)
                                showHotelRate(result.details.stars)
                                showHotelPhotos(result.details.images)
                                showServices(servicesInColumn(result.details.amenities))
                                showDescription(result.details.description)
                                showLocation(result.details.location)
                                getHotelPrice(searchId, hotelId)
                            }
                        },
                        { error -> viewState.showErrorMessage(error.message) }
                )
    }


    private fun getHotelPrice(searchId: String, hotelId: Int) {
        HotelService.create().getRoomPrices(searchId, hotelId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> viewState.showPrice(result.prices) },
                        { error -> viewState.showErrorMessage(error.message) }
                )
    }

    private fun servicesInColumn(amenities: List<String>): String {
        var services = ""
        for (amenity in amenities) services = services.plus("- $amenity \n")
        return services
    }

}