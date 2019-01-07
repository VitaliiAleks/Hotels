package hotels.zumme.my.hotels.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hotels.zumme.my.hotels.R


/*
 *   ViewHolder for the progressView
 */
class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): ProgressViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_progress, parent, false)
            return ProgressViewHolder(view)
        }
    }
}