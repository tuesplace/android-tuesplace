package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.Specification
import okhttp3.MultipartBody

interface SpecificationService {
    suspend fun getSpecification(specificationCallback: SpecificationCallback<ArrayList<Specification>>)
    suspend fun editSpecificationAssets(specificationCallback: SpecificationCallback<Unit>, specificationId: String, specification: MultipartBody.Part)

    interface SpecificationCallback<Data>{
        fun onSuccess(data: Data)
        fun onError(error: String)
    }
}