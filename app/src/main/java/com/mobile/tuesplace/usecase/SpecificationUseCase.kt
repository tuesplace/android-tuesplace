package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.Specification
import com.mobile.tuesplace.services.SpecificationService

class SpecificationUseCase(private val specificationService: SpecificationService) {
    suspend operator fun invoke(specificationCallback: SpecificationService.SpecificationCallback<ArrayList<Specification>>){
        specificationService.getSpecification(specificationCallback)
    }
}