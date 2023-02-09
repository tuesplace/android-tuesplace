package com.mobile.tuesplace.services
import android.content.Context
import android.text.TextUtils
import com.mobile.tuesplace.*
import com.mobile.tuesplace.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class TuesInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //Check if a request has header "token: none" we should not add authorization to the request.
        val noTokenHeader = chain.request().headers[HEADER_PREFIX_TOKEN]

        val newRequest: Request.Builder = chain.request().newBuilder()
        val token = SessionManager.getInstance(dataStore = context.dataStore).getToken()

        if (TextUtils.isEmpty(noTokenHeader)) {
            //set token
//            val token = getToken()
            if (!TextUtils.isEmpty(token)) {
                newRequest.addHeader("Authorization", "Bearer $token")
            }
        } else {
            //We should remove unnecessary header
            newRequest.removeHeader(HEADER_PREFIX_TOKEN)
            //If the token is refresh token we use it for refreshing the token
//            if (noTokenHeader.equals(HEADER_VALUE_REFRESH_TOKEN)) {
//                val refreshToken = Api.getInstance().refreshToken
//                if (!TextUtils.isEmpty(refreshToken)) {
//                    newRequest.addHeader("Authorization", "Bearer $refreshToken")
//                }
//            }
        }

        return chain.proceed(newRequest.build())
    }
}