package hotels.zumme.my.hotels.models.hotel

class Details(val place_id: String,
              val id: Int,
              val name: String,
              val latitude: Double,
              val longitude: Double,
              val stars: Int,
              val location: String,
              val description: String,
              val amenities: List<String>,
              val images: List<String>,
              val geo_type: String)