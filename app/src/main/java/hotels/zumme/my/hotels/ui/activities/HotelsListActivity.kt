package hotels.zumme.my.hotels.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.ui.fragments.HotelsListFragment

class HotelsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, HotelsListFragment
                        .newInstance(intent.extras.getString(HotelsListFragment.ARG_SEARCH_ID))).commit()
    }
}
