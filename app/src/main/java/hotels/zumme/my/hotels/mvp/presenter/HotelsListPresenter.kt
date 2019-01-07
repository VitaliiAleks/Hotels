package hotels.zumme.my.hotels.mvp


import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import hotels.zumme.my.hotels.adapters.paging.HotelDataSource
import hotels.zumme.my.hotels.adapters.paging.LoadStatus
import hotels.zumme.my.hotels.adapters.paging.MainThreadExecutor
import hotels.zumme.my.hotels.models.hotel.HotelListItem

@InjectViewState
class HotelsListPresenter : MvpPresenter<HotelsListView>() {

    private lateinit var hotelDataSource: HotelDataSource

    fun createHotelsPagedList(searchId: String): PagedList<HotelListItem>{

          hotelDataSource = HotelDataSource(searchId, viewState)

          hotelDataSource.getNetworkState()

          val config = PagedList.Config.Builder()
                  .setEnablePlaceholders(false)
                  .setPageSize(10)
                  .build()

          return PagedList.Builder(hotelDataSource, config)
                  .setFetchExecutor(MainThreadExecutor())
                  .setNotifyExecutor(MainThreadExecutor())
                  .build()
      }

    fun getNetworkStateLiveData(): MutableLiveData<LoadStatus>{
        return hotelDataSource.getNetworkState()
    }
}