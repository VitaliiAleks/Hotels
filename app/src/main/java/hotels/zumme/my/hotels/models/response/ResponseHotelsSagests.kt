package hotels.zumme.my.hotels.models.response

data class ResponseHotelsSagests(var status: String,
                                 var suggestions: List<ResponseHotelsSearchPlace>)