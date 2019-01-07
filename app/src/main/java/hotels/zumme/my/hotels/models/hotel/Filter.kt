package hotels.zumme.my.hotels.models.hotel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Filter (val values: List<Value>,
                   val name: String,
                   val id: Int)