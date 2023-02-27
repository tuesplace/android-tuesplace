package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.SpecificationService
import okhttp3.MultipartBody

class EditSpecificationAssetsUseCase(private val specificationService: SpecificationService) {
    suspend operator fun invoke(specificationCallback: SpecificationService.SpecificationCallback<Unit>, specificationId: String, specification: MultipartBody.Part){
        specificationService.editSpecificationAssets(specificationCallback = specificationCallback, specificationId = specificationId, specification = specification)
    }
}