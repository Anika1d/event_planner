package com.template.core.usecase.events

import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class GetLikedEventListUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
     operator fun invoke(isLiked:Boolean) =
        repositoryImpl.getIsLikedEventList(isLiked)
}