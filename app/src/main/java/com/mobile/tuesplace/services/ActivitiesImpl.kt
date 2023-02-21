package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.AgendaResponseData
import com.mobile.tuesplace.data.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivitiesImpl(private val retrofit: ApiServices): ActivitiesService {
    override suspend fun getActivities(activitiesCallback: ActivitiesService.ActivitiesCallback<List<AgendaResponseData>>) {
        retrofit.getActivities().enqueue(
            object: Callback<BaseResponse<List<AgendaResponseData>>>{
                override fun onResponse(
                    call: Call<BaseResponse<List<AgendaResponseData>>>,
                    response: Response<BaseResponse<List<AgendaResponseData>>>,
                ) {
                    if (response.isSuccessful){
                        response.body()?.response?.let { activitiesCallback.onSuccess(it) }
                    }  else {
                        activitiesCallback.onError(response.message())
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<AgendaResponseData>>>,
                    t: Throwable,
                ) {
                    t.localizedMessage?.let { activitiesCallback.onError(it) }
                }
            }
        )
    }
}