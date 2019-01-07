package hotels.zumme.my.hotels.models.response

import hotels.zumme.my.hotels.models.hotel.Details

class ResponseHotelDetails(val code: Int,
                           val status: String,
                           val details: Details)