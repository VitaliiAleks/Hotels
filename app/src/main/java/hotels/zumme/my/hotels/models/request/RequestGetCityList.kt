package hotels.zumme.my.hotels.models.request

import hotels.zumme.my.hotels.extensions.HotelConstants


data class RequestGetCityList(var type: String = "hotel",
                              var user_id: String = HotelConstants.USER_ID,
                              var search: String,
                              var country: String = "UA",
                              var currency: String = "USD",
                              var language: String = "ru",
                              var geo_type: String = "")