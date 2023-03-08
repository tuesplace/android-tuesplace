package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.services.ActivitiesService

class GetActivitiesUseCase(private val activitiesService: ActivitiesService) {
    suspend operator fun invoke(activitiesCallback: ActivitiesService.ActivitiesCallback<List<AgendaResponseData>>){
        activitiesService.getActivities(activitiesCallback)
    }
}