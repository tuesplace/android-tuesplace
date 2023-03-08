package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.Specification
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificationImpl(private val retrofit: ApiServices): SpecificationService {
    override suspend fun getSpecification(specificationCallback: SpecificationService.SpecificationCallback<ArrayList<Specification>>) {
        retrofit.getSpecification().enqueue(
            object : Callback<BaseResponse<ArrayList<Specification>>> {
                override fun onResponse(
                    call: Call<BaseResponse<ArrayList<Specification>>>,
                    response: Response<BaseResponse<ArrayList<Specification>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { specificationCallback.onSuccess(it) }
                    } else {
                        specificationCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<ArrayList<Specification>>>, t: Throwable) {
                    t.localizedMessage?.let { specificationCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun editSpecificationAssets(
        specificationCallback: SpecificationService.SpecificationCallback<Unit>,
        specificationId: String,
        specification: MultipartBody.Part
    ) {
        retrofit.editSpecificationAssets(specificationId, specification).enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        response.body()?.let { specificationCallback.onSuccess(it) }
                    } else {
                        specificationCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    t.localizedMessage?.let { specificationCallback.onError(it) }
                }
            }
        )
    }
}