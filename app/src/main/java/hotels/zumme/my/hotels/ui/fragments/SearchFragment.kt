package hotels.zumme.my.hotels.ui.fragments


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.models.response.ResponseHotelsSearchPlace
import hotels.zumme.my.hotels.mvp.SearchHotelPresenter
import hotels.zumme.my.hotels.mvp.SearchHotelPresenter.Companion.ONE_DAY_MILISECONDS
import hotels.zumme.my.hotels.mvp.SearchHotelView
import hotels.zumme.my.hotels.ui.activities.HotelsListActivity
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import java.util.concurrent.TimeUnit


class SearchFragment : MvpAppCompatFragment(), SearchHotelView{

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mSearchHotelPresenter: SearchHotelPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun provideSearchHotelPresenter() = SearchHotelPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchHotelPresenter.getCurrentDate()
        initViews()
        updateDateInView()
    }

    private fun showDatePicker(isArrival: Boolean) {
        val arrivalDate = mSearchHotelPresenter.getArrivalDate()
        val departureDate = mSearchHotelPresenter.getDepartureDate()
        val datePickerTime: Date  =   if (isArrival) arrivalDate else departureDate
        val calendar = Calendar.getInstance()
        calendar.time = datePickerTime

       val datePickerDialog =  DatePickerDialog(context, setDateSetListener(isArrival),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))

        when(isArrival){
            true -> datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            false -> datePickerDialog.datePicker.minDate = arrivalDate.time + ONE_DAY_MILISECONDS
        }

        datePickerDialog.show()
    }


    private fun setDateSetListener(isArrival: Boolean): DatePickerDialog.OnDateSetListener? {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val datePickerCalendar = Calendar.getInstance()
            datePickerCalendar.set(year, monthOfYear, dayOfMonth)

            when(isArrival){
                true -> mSearchHotelPresenter.setArrivalTime(datePickerCalendar.time)
                false -> mSearchHotelPresenter.setDepartureTime(datePickerCalendar.time)
            }
            updateDateInView()
        }
    }

    private fun updateDateInView() {
        arrival_date_text_view.text = mSearchHotelPresenter.getFormattedArrivalDate()
        departure_date_text_view.text = mSearchHotelPresenter.getFormattedDepartureDate()
    }

    override fun showCitiesList(citiesList: List<ResponseHotelsSearchPlace>?) {
        if(citiesList != null){
            val citiesAdapter = ArrayAdapter<ResponseHotelsSearchPlace>(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    citiesList)

            with(search_place_edit_text) {
                setAdapter(citiesAdapter)
                onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedCity = parent.getItemAtPosition(position) as ResponseHotelsSearchPlace
                    mSearchHotelPresenter.setCityId(selectedCity.id)
                }
            }
        }
    }

    override fun startHotelListActivity(searchId: String) {
        val intent = Intent(context, HotelsListActivity::class.java)
        intent.putExtra(HotelsListFragment.ARG_SEARCH_ID, searchId)
        startActivity(intent)
    }

    override fun showErrorMessage(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun initViews() {

        RxTextView.textChanges(search_place_edit_text)
                .subscribe{charSequence ->
                    clear_view.visibility = if (charSequence.isEmpty()) View.GONE else View.VISIBLE}

        RxTextView.textChanges(search_place_edit_text)
                .debounce(TIMEOUT, TimeUnit.MILLISECONDS)
                .filter { charSequence -> charSequence.length >= MIN_TEXT_LENGTH}
                .map { charSequence -> charSequence.toString()}
                .subscribe { query -> mSearchHotelPresenter.getCityList(query)}

        RxView.clicks(clear_view).subscribe{
            search_place_edit_text.text = null
            mSearchHotelPresenter.setCityId(SearchHotelPresenter.DEFAULT_CITY_ID)
        }

        RxView.clicks(arrival_date_text_view).subscribe { showDatePicker(true) }
        RxView.clicks(departure_date_text_view).subscribe { showDatePicker(false) }
        RxView.clicks(fab_search).subscribe{ mSearchHotelPresenter.getSearchID()}
    }

    companion object {

        private const val MIN_TEXT_LENGTH = 2
        private const val TIMEOUT = 300L

        @JvmStatic
        fun newInstance() =
                SearchFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
