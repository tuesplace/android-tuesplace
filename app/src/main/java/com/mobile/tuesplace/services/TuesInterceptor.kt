package com.mobile.tuesplace.services
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.mobile.tuesplace.*
import com.mobile.tuesplace.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class TuesInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val noTokenHeader = chain.request().headers[HEADER_PREFIX_TOKEN]

        val newRequest: Request.Builder = chain.request().newBuilder()
        var token = SessionManager.getInstance(dataStore = context.dataStore).getToken()


        if (TextUtils.isEmpty(noTokenHeader)) {
            if (token.isEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    token = SessionManager.getInstance(dataStore = context.dataStore)
                        .fetchAuthToken()
                }
            }
            if (!TextUtils.isEmpty(token)) {
                newRequest.addHeader("Authorization", "Bearer $token")
            }
        } else {
            newRequest.removeHeader(HEADER_PREFIX_TOKEN)
        }

        return chain.proceed(newRequest.build())
    }
}