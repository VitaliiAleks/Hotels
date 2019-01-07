package hotels.zumme.my.hotels.adapters


import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import hotels.zumme.my.hotels.adapters.paging.LoadStatus
import hotels.zumme.my.hotels.adapters.viewholder.HotelViewHolder
import hotels.zumme.my.hotels.adapters.viewholder.ProgressViewHolder
import hotels.zumme.my.hotels.models.hotel.HotelListItem
import hotels.zumme.my.hotels.mvp.HotelsListView


class HotelsListRecyclerViewAdapter(private val mHotelClickListener: HotelsListView.IHotelClickListener?)
    : PagedListAdapter<HotelListItem, RecyclerView.ViewHolder>(HotelsDiffUtilCallback) {

    private val dataViewType = 1
    private val footerViewType = 2
    private var loadStatus: LoadStatus? = LoadStatus.INIT

    companion object {
        val HotelsDiffUtilCallback = object : DiffUtil.ItemCallback<HotelListItem>() {
            override fun areItemsTheSame(oldItem: HotelListItem, newItem: HotelListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HotelListItem, newItem: HotelListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == dataViewType) HotelViewHolder.create(parent, mHotelClickListener)
        else ProgressViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == dataViewType)
            (holder as HotelViewHolder).bind(getItem(position))
        else (holder as ProgressViewHolder)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) dataViewType else footerViewType
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return loadStatus == LoadStatus.LOADING || loadStatus == LoadStatus.ERROR
    }

    fun setNetworkState(networkLoadStatusLiveData: MutableLiveData<LoadStatus>) {
        networkLoadStatusLiveData.observeForever {
            if (loadStatus != it) {
                loadStatus = it
                notifyItemChanged(super.getItemCount())
            }
        }
    }
}
