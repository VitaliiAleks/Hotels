package hotels.zumme.my.hotels.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.ui.fragments.HotelDetailsFragment

class HotelDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, HotelDetailsFragment
                        .newInstance(intent.extras.getString(HotelDetailsFragment.ARG_SEARCH_ID),
                                intent.extras.getInt(HotelDetailsFragment.ARG_HOTEL_ID))).commit()
    }
}
