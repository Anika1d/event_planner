package com.template.core.usecase.events

import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class GetLikedEventListByDateUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
     operator fun invoke(isLiked:Boolean,dateStart:String) =
        repositoryImpl.getIsLikedEventListByDate(isLiked=isLiked,dateStart=dateStart)
}