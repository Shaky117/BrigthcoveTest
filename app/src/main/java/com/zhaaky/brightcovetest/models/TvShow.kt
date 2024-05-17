package com.zhaaky.brightcovetest.models

import java.io.Serializable

data class TvShow(
    val airdate : String,
    val airtime : String,
    val show : ShowDetails
) : Serializable