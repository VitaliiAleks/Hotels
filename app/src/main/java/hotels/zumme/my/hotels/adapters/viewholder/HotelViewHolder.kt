package hotels.zumme.my.hotels.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import hotels.zumme.my.hotels.R
import hotels.zumme.my.hotels.models.hotel.HotelListItem
import hotels.zumme.my.hotels.mvp.HotelsListView
import kotlinx.android.synthetic.main.adapter_hotel_card.view.*

/*
 *   ViewHolder for the Hotel
 */
class HotelViewHolder(view: View, private val mHotelClickListener: HotelsListView.IHotelClickListener?) : RecyclerView.ViewHolder(view) {

    private val mCurrentHotelClickListener: View.OnClickListener

    private val mHotelNameView: TextView = itemView.hotel_name_text_view
    private val mHotelPriceView: TextView = itemView.hotel_price_text_view
    private val mHotelImage: ImageView = itemView.hotel_image
    private  val mHotelRating: RatingBar = itemView.hotel_rating

    init {
        mCurrentHotelClickListener = View.OnClickListener { v ->
            val hotel = v.tag as HotelListItem
            mHotelClickListener?.hotelClicked(hotel)
        }
    }

    fun bind(hotel: HotelListItem?) {
        if (hotel != null){
            mHotelNameView.text = hotel.name
            val price = "${hotel.price_total} ${mHotelNameView.context.resources.getString(R.string.default_currency)}"
            mHotelPriceView.text = price

            Picasso.get().load(hotel.imageUrl)
                    .fit()
                    .centerCrop()
                    .into(mHotelImage)
            mHotelRating.rating = hotel.stars.toFloat()

            with(itemView) {
                tag = hotel
                setOnClickListener(mCurrentHotelClickListener)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, hotelClickListener: HotelsListView.IHotelClickListener?): HotelViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_hotel_card, parent, false)
            return HotelViewHolder(view, hotelClickListener)
        }
    }
}