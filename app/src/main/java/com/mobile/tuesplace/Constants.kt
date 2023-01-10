package com.mobile.tuesplace

const val BASE_URL = "https://server-tuesplace-97zq3.ondigitalocean.app/api/"
const val SIGN_IN = "auth/sign-in"
const val GENERATE_TOKEN_PAIR = "auth/generate-token-pair"
const val CREATE_GROUP = "group/"
const val PROFILE_ME = "profile/me"
const val PROFILE = "profile/{profileId}"
const val GET_GROUP = "group/{groupId}"
const val GET_POSTS = "group/{groupId}/post"
const val EDIT_POST = "group/{groupId}/post/{postId}"
const val GET_MARKS = "group/{groupId}/mark"
const val STUDENT_MARKS = "group/{groupId}/mark/student/{studentId}"
const val STUDENT_MARK = "group/{groupId}/mark/student/{studentId}/{markId}"

var ACCESS_TOKEN = ""
var USER_ID = ""

var ROLE = ""

