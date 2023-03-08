package com.mobile.tuesplace.data

data class Image<Data>(
    var _id: String,
    var collectionName: String,
    var shouldResolve: Boolean,
    var data: Data
)

data class ImageData(
    var _id: String,
    var owner: OwnerData,
    var key: String,
    var mimetype: String,
    var meta: MetaData,
    var createdAt: String,
    var updatedAt: String,
    var src: String
    // v
)

data class MetaData(
    var originalName: String
)

data class OwnerData(
    var _id: String,
    var collectionName: String,
    var shouldResolve: Boolean
)