package com.mobile.tuesplace

const val BASE_URL = "https://server-tuesplace-97zq3.ondigitalocean.app/api/"
const val SIGN_IN = "auth/sign-in"
const val GENERATE_TOKEN_PAIR = "auth/generate-token-pair"
const val CREATE_GROUP = "groups/"
const val PROFILE_ME = "profiles/me"
const val ALL_PROFILES = "profiles"
const val PROFILE = "profiles/{profileId}"
const val GET_GROUP = "groups/{groupId}"
const val GET_MY_GROUPS = "groups/me"
const val GET_POSTS = "groups/{groupId}/posts"
const val EDIT_POST = "groups/{groupId}/posts/{postId}"
const val GET_MARKS = "groups/{groupId}/marks"
const val STUDENT_MARKS = "groups/{groupId}/marks/students/{studentId}"
const val STUDENT_MARK = "groups/{groupId}/marks/students/{studentId}/{markId}"
const val POST_COMMENTS = "groups/{groupId}/posts/{postId}/comments"
const val POST_COMMENT = "groups/{groupId}/posts/{postId}/comments/{commentId}"

var ACCESS_TOKEN = ""
var USER_ID = ""

var ROLE = ""

