package com.zhaaky.brightcovetest.models

data class ShowDetails(
    val id : Int,
    val url : String,
    val name : String,
    val type : String,
    val language : String,
    val status : String,
    val premiered : String,
    val ended : Boolean,
    val schedule : Schedule,
    val image: ImageShow,
    val summary : String
)
