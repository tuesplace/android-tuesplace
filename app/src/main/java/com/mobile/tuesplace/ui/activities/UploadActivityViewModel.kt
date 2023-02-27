package com.mobile.tuesplace.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.data.Specification
import com.mobile.tuesplace.services.SpecificationService
import com.mobile.tuesplace.ui.states.EditSpecificationUiState
import com.mobile.tuesplace.ui.states.GetSpecificationUiState
import com.mobile.tuesplace.usecase.EditSpecificationAssetsUseCase
import com.mobile.tuesplace.usecase.SpecificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadActivityViewModel(private val specificationUseCase: SpecificationUseCase, private val editSpecificationAssetsUseCase: EditSpecificationAssetsUseCase): ViewModel() {

    private val _getSpecificationStateFlow =
        MutableStateFlow<GetSpecificationUiState>(GetSpecificationUiState.Empty)
    val getSpecificationStateFlow: StateFlow<GetSpecificationUiState> = _getSpecificationStateFlow

    fun getSpecification(){
        viewModelScope.launch {
            specificationUseCase.invoke(
                object : SpecificationService.SpecificationCallback<ArrayList<Specification>> {
                    override fun onSuccess(data: ArrayList<Specification>) {
                        viewModelScope.launch {
                            _getSpecificationStateFlow.emit(GetSpecificationUiState.Success(data))
                        }
                    }

                    override fun onError(error: String) {
                        viewModelScope.launch {
                            _getSpecificationStateFlow.emit(GetSpecificationUiState.Error(error))
                        }
                    }

                }
            )
        }
    }

    fun resetSpecificationState(){
        viewModelScope.launch {
            _getSpecificationStateFlow.emit(GetSpecificationUiState.Empty)
        }
    }

    private val _editSpecificationAssetsStateFlow =
        MutableStateFlow<EditSpecificationUiState>(EditSpecificationUiState.Empty)
    val editSpecificationAssetsStateFlow: StateFlow<EditSpecificationUiState> = _editSpecificationAssetsStateFlow

    fun editSpecificationAssets(specificationId: String, specification: MultipartBody.Part){
        viewModelScope.launch {
            editSpecificationAssetsUseCase.invoke(object : SpecificationService.SpecificationCallback<Unit> {
                override fun onSuccess(data: Unit) {
                    viewModelScope.launch {
                        _editSpecificationAssetsStateFlow.emit(EditSpecificationUiState.Success)
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch{
                        _editSpecificationAssetsStateFlow.emit(EditSpecificationUiState.Error(error))
                    }
                }
            }, specificationId, specification)
        }
    }
}