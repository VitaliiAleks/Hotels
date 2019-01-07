package hotels.zumme.my.hotels.ui.fragments

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.adapters.HotelPhotosRecyclerViewAdapter
import hotels.zumme.my.hotels.adapters.HotelPriceRecyclerViewAdapter
import hotels.zumme.my.hotels.adapters.decoration.MarginItemDecoration
import hotels.zumme.my.hotels.models.hotel.Price
import hotels.zumme.my.hotels.mvp.HotelDetailsPresenter
import hotels.zumme.my.hotels.mvp.HotelDetailsView
import kotlinx.android.synthetic.main.fragment_hotel_details.*
import kotlinx.android.synthetic.main.hotel_details_amenity_layout.view.*
import kotlinx.android.synthetic.main.hotel_details_description_layout.view.*
import kotlinx.android.synthetic.main.hotel_details_location_layout.view.*
import kotlinx.android.synthetic.main.hotel_details_price_layout.view.*
import kotlinx.android.synthetic.main.hotel_details_top_bar.view.*


class HotelDetailsFragment : MvpAppCompatFragment(), HotelDetailsView {

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mHotelDetailsPresenter: HotelDetailsPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun provideHotelDetailsPresenter() = HotelDetailsPresenter()

    private lateinit var searchId: String
    private var hotelId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            searchId = it.getString(ARG_SEARCH_ID)
            hotelId = it.getInt(ARG_HOTEL_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hotel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHotelDetailsPresenter.getHotelDetails(searchId, hotelId)
    }

    override fun showHotelName(hotelName: String) {
        container.hotel_name_text_view.text = hotelName
    }

    override fun showHotelRate(hotelRate: Int) {
        container.hotel_rate_bar.rating = hotelRate.toFloat()
    }

    override fun showHotelPhotos(photosList: List<String>) {
        val hotelPhotosRecyclerView: RecyclerView = container.hotels_details_preview_photos
        with(hotelPhotosRecyclerView){
            addItemDecoration(MarginItemDecoration(
                    resources.getDimension(R.dimen.hotel_details_recycler_view_padding).toInt()))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HotelPhotosRecyclerViewAdapter(photosList)
        }
    }

    override fun showPrice(priceList: List<Price>) {
        container.progress_bar_load_prices.visibility = View.GONE
        val hotelPricesRecyclerView: RecyclerView = container.hotels_prices_recycler_view
        ViewCompat.setNestedScrollingEnabled(hotelPricesRecyclerView, false)
        with(hotelPricesRecyclerView){
            addItemDecoration(MarginItemDecoration(
                    resources.getDimension(R.dimen.hotel_details_recycler_view_padding).toInt()))
            layoutManager = LinearLayoutManager(context)
            adapter = HotelPriceRecyclerViewAdapter(priceList)
        }
    }

    override fun showServices(service: String) {
        container.amenities.text = service
    }

    override fun showDescription(description: String) {
        container.description_text_view.text = description
    }

    override fun showLocation(location: String) {
        container.street_text_view.text = location
    }

    override fun showErrorMessage(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val ARG_SEARCH_ID = "search_id"
        const val ARG_HOTEL_ID = "hotel_id"

        @JvmStatic
        fun newInstance(searchId: String, hotelId: Int) =
                HotelDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_SEARCH_ID, searchId)
                        putInt(ARG_HOTEL_ID, hotelId)
                    }
                }
    }
}
