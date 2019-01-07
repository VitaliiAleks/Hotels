package hotels.zumme.my.hotels.adapters


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.models.hotel.Price
import kotlinx.android.synthetic.main.adapter_hotel_price.view.*


class HotelPriceRecyclerViewAdapter(private val sellers: List<Price>)
    : RecyclerView.Adapter<HotelPriceRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_hotel_price, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val seller = sellers[position]
        val price = "${seller.price}".plus(holder.bookButton.context.resources.getString(R.string.default_currency))
        with(holder){
            sellerName.text = seller.name
            bookButton.text = price
        }
    }

    override fun getItemCount(): Int = sellers.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val sellerName: TextView = mView.hotels_seller
        val bookButton: Button = mView.hotels_book_button
    }
}
