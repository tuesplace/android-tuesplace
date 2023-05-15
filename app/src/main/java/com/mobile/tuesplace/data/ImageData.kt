package com.mobile.tuesplace.data

data class Image<Data>(
    var _id: String,
    var collectionName: String,
    var shouldResolve: Boolean,
    var data: Data
)

data class ImageData(
    var _id: String,
    var owner: ShouldResolveData,
    var key: String,
    var mimetype: String,
    var meta: MetaData,
    var createdAt: String,
    var updatedAt: String,
    var src: String
)

data class MetaData(
    var originalName: String
)
