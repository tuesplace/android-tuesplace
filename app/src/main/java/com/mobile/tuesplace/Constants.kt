package com.mobile.tuesplace

import com.mobile.tuesplace.ui.navigation.LOGIN_SCREEN

const val BASE_URL = "https://server-tuesplace-97zq3.ondigitalocean.app/api/"
const val SIGN_IN = "v1/auth/sign-in"
const val GENERATE_TOKEN_PAIR = "v1/auth/generate-token-pair"
const val CREATE_GROUP = "v1/groups/"
const val PROFILE_ME = "v1/profiles/me"
const val ALL_PROFILES = "v1/profiles"
const val PROFILE = "v1/profiles/{profileId}"
const val PROFILE_ASSETS = "v1/profiles/{profileId}/assets"
const val MY_PROFILE_ASSETS = "v1/profiles/me/assets"
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
const val SUBMISSIONS = "v1/groups/{groupId}/posts/{postId}/submissions"
const val SUBMISSIONS_MARKS = "v1/groups/{groupId}/posts/{postId}/submissions/{submissionId}/marks"
const val EDIT_MARK = "v1/groups/{groupsId}/posts/{postId}/marks/{markId}"

var ACCESS_TOKEN = ""
var USER_ID = ""

var ROLE = "role"
const val BLOCKED = "blocked"
const val GROUP_ID = "groupId"
const val POST_ID = "postId"

const val DATE_PATTERN = "EEE"

const val WEB_VIEW_LINK = "https://tues.bg"

const val HEADER_PREFIX_TOKEN = "token"

const val UPLOAD_CODE = 1
const val DOCUMENTS_DIR = "documents"
const val EMPTY_STRING = ""
const val ZERO_STRING = "0"

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

const val APP_SETTINGS_FILENAME = "app-settings.json"
const val XLSX_FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
const val IMAGE_FILE_TYPE = "image/*"
const val ALL_TYPES = "*/*"
const val MULTIPART_NAME_XLSX = "specification"
const val MULTIPART_NAME_IMAGE = "profilePic"
const val MULTIPART_NAME_SUBMISSION = "assets"

const val STUDENT_ROLE = "student"
const val TEACHER_ROLE = "teacher"
const val ADMIN_ROLE = "admin"

const val CHAT_TYPE = "chat"
const val SUBJECT_TYPE = "subject"

const val ID_STRING = "id"
const val IS_ADMIN = "is_admin"

const val EIGHT_A_GRADE = "8А"
const val NINTH_A_GRADE = "9А"
const val TENTH_A_GRADE = "10А"
const val ELEVENTH_A_GRADE = "11А"
const val TWELFTH_A_GRADE = "12А"
const val EIGHT_B_GRADE = "8Б"
const val NINTH_B_GRADE = "9Б"
const val TENTH_B_GRADE = "10Б"
const val ELEVENTH_B_GRADE = "11Б"
const val TWELFTH_B_GRADE = "12Б"
const val EIGHT_C_GRADE = "8В"
const val NINTH_C_GRADE = "9В"
const val TENTH_C_GRADE = "10В"
const val ELEVENTH_C_GRADE = "11В"
const val TWELFTH_C_GRADE = "12В"
const val EIGHT_D_GRADE = "8Г"
const val NINTH_D_GRADE = "9Г"
const val TENTH_D_GRADE = "10Г"
const val ELEVENTH_D_GRADE = "11Г"
const val TWELFTH_D_GRADE = "12Г"
const val EMPTY_GRADE = "-"

