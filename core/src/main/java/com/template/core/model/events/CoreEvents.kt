package com.template.core.model.events

data class CoreEvents(
    val id: Long,
    val name: String,
    val description: String,
    val dateStart: String,
    val timeStart: String,
    val dateEnd: String,
    val timeEnd: String,
    val city: String,
    val address: String,
    val isLiked: Boolean,
    val isVisited: Boolean?,
)