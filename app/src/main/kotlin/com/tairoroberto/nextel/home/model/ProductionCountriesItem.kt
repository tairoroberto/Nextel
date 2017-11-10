package com.tairoroberto.nextel.home.model

import com.google.gson.annotations.SerializedName

data class ProductionCountriesItem(@SerializedName("iso_3166_1")
                                   val iso: String = "",
                                   val name: String = "")