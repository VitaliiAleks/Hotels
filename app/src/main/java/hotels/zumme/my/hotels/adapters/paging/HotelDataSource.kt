package hotels.zumme.my.hotels.adapters.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import hotels.zumme.my.hotels.api.HotelService
import hotels.zumme.my.hotels.models.hotel.HotelListItem
import hotels.zumme.my.hotels.mvp.HotelsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit



class HotelDataSource(private val searchId: String, private var mHotelListView: HotelsListView) : PositionalDataSource<HotelListItem>() {

    private var page: Int = 0
    private val networkLoadStatus: MutableLiveData<LoadStatus> = MutableLiveData()

    fun getNetworkState(): MutableLiveData<LoadStatus> {
        return networkLoadStatus
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<HotelListItem>) {
        getFirstListOfHotels(searchId, callback)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<HotelListItem>) {
        networkLoadStatus.postValue(LoadStatus.LOADING)
        HotelService.create().getHotels(searchId, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            when {
                                result.hotels.isNotEmpty() -> {
                                    callback.onResult(result.hotels)
                                    page++
                                }
                            }
                            networkLoadStatus.postValue(LoadStatus.DONE)
                        },
                        { error ->
                            networkLoadStatus.postValue(LoadStatus.ERROR)
                            mHotelListView.showErrorMessage(error.message) }
                )
    }


    //The server is looking for hotels, so it can not give them the first time
    private fun getFirstListOfHotels(searchId: String, callback: LoadInitialCallback<HotelListItem>) {
        networkLoadStatus.postValue(LoadStatus.LOADING)
        HotelService.create().getHotels(searchId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
                .subscribe(
                        { result ->
                            when {
                                result.hotels.isEmpty() && result.status == STATUS_COMPLETE ->{
                                    networkLoadStatus.postValue(LoadStatus.DONE)
                                    mHotelListView.showNotFoundMessage()
                                }

                                result.hotels.isNotEmpty() -> {
                                    networkLoadStatus.postValue(LoadStatus.DONE)
                                    callback.onResult(result.hotels, page)
                                    page++
                                }
                                result.status == STATUS_PENDING && result.hotels.isEmpty() -> {
                                    getFirstListOfHotels(searchId, callback)
                                }
                            }

                        },
                        { error ->
                            networkLoadStatus.postValue(LoadStatus.ERROR)
                            mHotelListView.showErrorMessage(error.message) }
                )

    }

    companion object {
        private const val STATUS_COMPLETE = "complete"
        private const val STATUS_PENDING = "pending"
        private const val DELAY_MILLISECONDS = 3000L
    }
}