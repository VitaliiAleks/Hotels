package hotels.zumme.my.hotels.adapters


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import hotels.zumme.my.hotels.R
import kotlinx.android.synthetic.main.adapter_hotel_photo.view.*


class HotelPhotosRecyclerViewAdapter(private val mPhotos: List<String>)
    : RecyclerView.Adapter<HotelPhotosRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_hotel_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = mPhotos[position]
        Picasso.get().load(photo)
                .fit()
                .centerCrop()
                .into(holder.mPhoto, object : Callback {
                    override fun onSuccess() {
                        holder.mProgressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                    }
                })
    }

    override fun getItemCount(): Int = mPhotos.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mPhoto: ImageView = mView.hotel_preview_photo
        val mProgressBar: ProgressBar = mView.load_image
    }
}
