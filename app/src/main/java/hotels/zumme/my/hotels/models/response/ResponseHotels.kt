package hotels.zumme.my.hotels.models.response

import hotels.zumme.my.hotels.models.hotel.Filter
import hotels.zumme.my.hotels.models.hotel.HotelListItem

data class ResponseHotels (val code: Int,
                           val status : String,
                           val total_hotels : Int,
                           val available_hotels: Int,
                           val hotels : List<HotelListItem>,
                           val filters: List<Filter>,
                           val place_id: String,
                           val geo_type: String)
