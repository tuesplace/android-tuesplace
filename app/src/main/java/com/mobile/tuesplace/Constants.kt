package com.mobile.tuesplace

const val BASE_URL = "https://server-tuesplace-97zq3.ondigitalocean.app/api/"
const val SIGN_IN = "v1/auth/sign-in"
const val GENERATE_TOKEN_PAIR = "v1/auth/generate-token-pair"
const val CREATE_GROUP = "v1/groups/"
const val PROFILE_ME = "v1/profiles/me"
const val ALL_PROFILES = "v1/profiles"
const val PROFILE = "v1/profiles/{profileId}"
const val GET_GROUP = "v1/groups/{groupId}"
const val GET_MY_GROUPS = "v1/groups/me"
const val GET_POSTS = "v1/groups/{groupId}/posts"
const val EDIT_POST = "v1/groups/{groupId}/posts/{postId}"
const val GET_MARKS = "v1/groups/{groupId}/marks"
const val STUDENT_MARKS = "v1/groups/{groupId}/marks/students/{studentId}"
const val STUDENT_MARK = "v1/groups/{groupId}/marks/students/{studentId}/{markId}"
const val POST_COMMENTS = "v1/groups/{groupId}/posts/{postId}/comments"
const val POST_COMMENT = "v1/groups/{groupId}/posts/{postId}/comments/{commentId}"
const val ACTIVITIES = "v1/activities"
const val MY_ACTIVITIES = "v1/activities/me"
const val SPECIFICATION = "v1/specification"
const val SPECIFICATION_ASSETS = "v1/specification/{specificationId}/assets"

var ACCESS_TOKEN = ""
var USER_ID = ""

var ROLE = ""

const val HEADER_PREFIX_TOKEN = "token"

const val UPLOAD_CODE = 1
const val DOCUMENTS_DIR = "documents"
const val EMPTY_STRING = ""

const val ZERO = 0
const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
const val A_LETTER = "А"
const val B_LETTER = "Б"
const val C_LETTER = "В"
const val D_LETTER = "Г"
const val MONDAY = "Mon"
const val TUESDAY = "Tue"
const val WEDNESDAY = "Wed"
const val THURSDAY = "Thu"
const val FRIDAY = "Fri"
