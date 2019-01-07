package hotels.zumme.my.hotels.models.request

import hotels.zumme.my.hotels.extensions.HotelConstants


data class RequestGetSearchId(val user_id: String = HotelConstants.USER_ID,
                              var account_name: String = "account_name",
                              val country: String = "UA",
                              val currency: String = "USD",
                              val language: String = "ru",
                              val place_id: String?,
                              val checkin_date: String,
                              val checkout_date: String,
                              val guests: Int = 1,
                              val rooms: Int = 1,
                              val geo_type: String = "city")