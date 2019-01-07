package hotels.zumme.my.hotels.models.response


data class ResponseHotelsSearchPlace(val id: Long,
                                val name: String,
                                val geo_type: String,
                                val geo_type_localised: String,
                                val country_name: String,
                                val region_name: String,
                                val city_name: String){

    override fun toString(): String {
        return city_name
    }
}