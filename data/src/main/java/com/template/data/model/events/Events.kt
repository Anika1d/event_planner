package com.template.data.model.events


data class Events(
    val id: Long,
    val name: String,
    val description: String,
    val dateStart: Triple<Int, Int, Int>,
    val timeStart: String,
    val dateEnd: Triple<Int, Int, Int>,
    val timeEnd: String,
    val city: String,
    val address: String,
    val isLiked: Boolean = false,
    val isVisited: Boolean? = null,

) {
    fun datesAreDifferent(): Boolean {
        return dateStart.first == dateEnd.first && dateStart.second == dateEnd.second && dateStart.third == dateEnd.third
    }
}