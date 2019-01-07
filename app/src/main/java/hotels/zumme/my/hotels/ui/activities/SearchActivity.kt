package hotels.zumme.my.hotels.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.ui.fragments.SearchFragment

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment
                        .newInstance()).commit()
    }
}
