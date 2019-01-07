package hotels.zumme.my.hotels.models.response

import hotels.zumme.my.hotels.models.hotel.Price

data class ResponsePrice (val status: String,
                          val prices: List<Price>)