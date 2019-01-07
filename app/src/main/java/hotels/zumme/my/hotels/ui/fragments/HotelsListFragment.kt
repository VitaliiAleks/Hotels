package hotels.zumme.my.hotels.ui.fragments

import android.arch.paging.PagedList
import android.content.Intent
import android.os.Bundle
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
import hotels.zumme.my.hotels.adapters.HotelsListRecyclerViewAdapter
import hotels.zumme.my.hotels.adapters.decoration.MarginItemDecoration
import hotels.zumme.my.hotels.models.hotel.HotelListItem
import hotels.zumme.my.hotels.mvp.HotelsListPresenter
import hotels.zumme.my.hotels.mvp.HotelsListView
import hotels.zumme.my.hotels.ui.activities.HotelDetailsActivity
import kotlinx.android.synthetic.main.fragment_hotel_list.view.*


class HotelsListFragment : MvpAppCompatFragment(), HotelsListView, HotelsListView.IHotelClickListener {

    private lateinit var hotelsRecyclerView: RecyclerView
    private lateinit var hotelsPagedList: PagedList<HotelListItem>
    private lateinit var hotelListAdapter: HotelsListRecyclerViewAdapter

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mHotelListPresenter: HotelsListPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun provideHotelListPresenter() = HotelsListPresenter()

    private lateinit var searchId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            searchId = it.getString(ARG_SEARCH_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_list, container, false)
        initHotelRecyclerView(view)
        return view
    }

    private fun initHotelRecyclerView(view: View) {

        hotelsPagedList = mHotelListPresenter.createHotelsPagedList(searchId)

        hotelListAdapter =  HotelsListRecyclerViewAdapter(this@HotelsListFragment)
        with(hotelListAdapter){
            setNetworkState(mHotelListPresenter.getNetworkStateLiveData())
            submitList(hotelsPagedList)
        }

        hotelsRecyclerView = view.hotels_list
        with(hotelsRecyclerView){
            addItemDecoration(MarginItemDecoration(
                    resources.getDimension(R.dimen.hotel_list_recycler_view_padding).toInt()))
            hotelsRecyclerView.layoutManager = LinearLayoutManager(context)
            hotelsRecyclerView.adapter = hotelListAdapter
        }
    }

    override fun showErrorMessage(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun showNotFoundMessage() {
        Toast.makeText(activity?.applicationContext, R.string.hotels_not_found, Toast.LENGTH_SHORT).show()
        activity?.finish()
    }

    override fun hotelClicked(hotel: HotelListItem) {
        val intent = Intent(activity, HotelDetailsActivity::class.java)
        intent.putExtra(HotelDetailsFragment.ARG_SEARCH_ID, searchId)
        intent.putExtra(HotelDetailsFragment.ARG_HOTEL_ID, hotel.id)
        startActivity(intent)
    }

    companion object {

        const val ARG_SEARCH_ID = "search_id"

        @JvmStatic
        fun newInstance(searchId: String) =
                HotelsListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_SEARCH_ID, searchId)
                    }
                }
    }
}
