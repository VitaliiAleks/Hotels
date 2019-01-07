package hotels.zumme.my.hotels.models.hotel

data class HotelListItem (val id: Int,
                          val name: String,
                          val latitude: Double,
                          val longitude: Double,
                          val stars: Int,
                          val price_total: Int,
                          val imageUrl: String,
                          val popularity: Int,
                          val geo_type: String )