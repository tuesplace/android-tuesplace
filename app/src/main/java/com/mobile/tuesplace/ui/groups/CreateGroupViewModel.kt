package com.mobile.tuesplace.ui.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.TEACHER_ROLE
import com.mobile.tuesplace.data.GroupData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.GroupService
import com.mobile.tuesplace.services.ProfileService
import com.mobile.tuesplace.ui.states.CreateGroupUiState
import com.mobile.tuesplace.ui.states.GetAllProfilesUiState
import com.mobile.tuesplace.usecase.CreateGroupUseCase
import com.mobile.tuesplace.usecase.GetAllProfilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateGroupViewModel(
    private val createGroupUseCase: CreateGroupUseCase,
    private val getAllProfilesUseCase: GetAllProfilesUseCase,
) : ViewModel() {

    var classesList: ArrayList<String> = ArrayList()
    var teachersList: ArrayList<String> = ArrayList()

    fun addClass(className: String) {
        classesList.add(className)
    }

    fun addTeacher(teacherId: String) {
        teachersList.add(teacherId)
    }

    private val _groupName =
        MutableStateFlow("")
    val groupName: StateFlow<String> = _groupName

    fun groupName(nameInput: String) {
        _groupName.value = nameInput
    }

    private val _teachers =
        MutableStateFlow("")
    val teachers: StateFlow<String> = _teachers

    fun teachers(teacher: String) {
        _teachers.value = teacher
    }

    private val _classes =
        MutableStateFlow("")
    val classes: StateFlow<String> = _classes

    fun classes(classesInput: String) {
        _classes.value = classesInput
    }

    private val _search =
        MutableStateFlow("")
    val search: StateFlow<String> = _search

    fun search(searchInput: String) {
        _search.value = searchInput
    }

    private val _groupsTypeStateFlow = MutableStateFlow(false)
    val groupsTypeStateFlow: StateFlow<Boolean> = _groupsTypeStateFlow

    fun groupsType(groupsType: Boolean) {
        _groupsTypeStateFlow.value = !groupsType
    }

    private val _uiStateFlow = MutableStateFlow<CreateGroupUiState>(CreateGroupUiState.Empty)
    val uiState: StateFlow<CreateGroupUiState> = _uiStateFlow

    fun createGroup(groupData: GroupData) {
        viewModelScope.launch {
            createGroupUseCase.invoke(groupData, object : GroupService.GroupCallback<Unit> {
                override fun onError(error: String) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(CreateGroupUiState.Error(error))
                    }
                }

                override fun onSuccess(groupGeneric: Unit) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(CreateGroupUiState.Success)
                    }
                }
            })
        }
    }

    fun resetState(){
        viewModelScope.launch {
            _uiStateFlow.emit(CreateGroupUiState.Empty)
        }
    }

    private val _teacherListVisibility = MutableStateFlow(false)
    val teacherListVisibility: StateFlow<Boolean> = _teacherListVisibility

    fun teacherListVisibility(teacherListInput: Boolean) {
        _teacherListVisibility.value = teacherListInput
    }

    private val _classListVisibility = MutableStateFlow(false)
    val classListVisibility: StateFlow<Boolean> = _classListVisibility

    fun classListVisibility(classListInput: Boolean) {
        _classListVisibility.value = classListInput
    }

    var profiles: List<ProfileResponseData>? = null

    private val _getAllProfilesStateFlow = MutableStateFlow<GetAllProfilesUiState>(
        GetAllProfilesUiState.Empty)
    val getAllProfilesStateFlow: StateFlow<GetAllProfilesUiState> = _getAllProfilesStateFlow

    fun getAllProfiles(){
        viewModelScope.launch {
            getAllProfilesUseCase.invoke(object : ProfileService.GetProfileCallback<List<ProfileResponseData>>{
                override fun onSuccess(profileGeneric: List<ProfileResponseData>) {
                    viewModelScope.launch {
                        profiles = profileGeneric.filter { profile -> profile.role == TEACHER_ROLE }
                        _getAllProfilesStateFlow.emit(GetAllProfilesUiState.Success(profileGeneric))
                    }
                }

                override fun onError(error: String) {
                    viewModelScope.launch {
                        _getAllProfilesStateFlow.emit(GetAllProfilesUiState.Error(error))
                    }
                }

            })
        }
    }
}