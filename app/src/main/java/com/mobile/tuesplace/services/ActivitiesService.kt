package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.AgendaResponseData

interface ActivitiesService {

    suspend fun getActivities(activitiesCallback: ActivitiesCallback<List<AgendaResponseData>>)
    suspend fun getMyActivities(activitiesCallback: ActivitiesCallback<List<AgendaResponseData>>)

    interface ActivitiesCallback<Data>{
        fun onSuccess(data: Data)
        fun onError(error: String)
    }
}