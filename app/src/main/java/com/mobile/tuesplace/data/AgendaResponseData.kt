package com.mobile.tuesplace.data

import com.google.gson.annotations.SerializedName

data class AgendaResponseData(
    var _id: String,
    var day: Int,
    var start: Int,
    var end: Int,
    var associations: AgendaAssociations,
)

data class AgendaAssociations(
    var group: AgendaAssociationsData<AgendaGroupData>,
    var room: AgendaAssociationsData<RoomData>,
)

data class AgendaAssociationsData<Data>(
    var _id: String,
    var shouldResolve: String,
    var data: Data,
)

data class AgendaGroupData(
    var name: String,
    var type: String,
    var classes: ArrayList<String>,
    var owners: ArrayList<AgendaOwnerData<AgendaProfileData>>,

    )//temp

data class AgendaOwnerData<Data>(
    var _id: String,
    var collectionName: String,
    var shouldResolve: String,
    var data: Data,
    var assets: Image<ImageData>,
    var createdAt: String,
    var updatedAt: String,

    )//temp

data class AgendaProfileData(
    var _id: String,
    var fullName: String,
    @SerializedName("class")
    var className: String,
    var role: String,
    var updatedAt: String,
    var assets: Assets<Image<ImageData>>,
)

data class Assets<Data>(
    var assets: ArrayList<Data>
)

data class RoomData(
    var _id: String,
    var location: String,
)
