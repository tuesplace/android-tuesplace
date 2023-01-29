package com.mobile.tuesplace

import android.Manifest
import android.accounts.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

import java.io.IOException

class AuthenticationManager(context: Context?, val loginIntent: Intent) :
    AbstractAccountAuthenticator(context) {

    private var accountManager: AccountManager = AccountManager.get(TuesplaceApplication.instance)

    fun createAccount(userName: String, token: String, refreshToken: String) {
        val account = Account(userName, ACCOUNT_TYPE)
        accountManager.addAccountExplicitly(account, "", null)
        accountManager.setAuthToken(account, TOKEN_TYPE, token)
        accountManager.setUserData(account, KEY_REFRESH_TOKEN, refreshToken)
    }

    fun peekTokenForUserWithName(
        context: Context,
        username: String?,
        tokenType: String = TOKEN_TYPE,
        accountType: String = ACCOUNT_TYPE
    ): String? {
        val account = getUserAccountFor(context, AccountManager.get(context), username, accountType)
            ?: return null

        return AccountManager.get(context).peekAuthToken(account, tokenType)
    }

    fun peekRefreshTokenUserWithName(
        context: Context,
        username: String?,
        accountType: String = ACCOUNT_TYPE
    ): String? {
        val account = getUserAccountFor(context, AccountManager.get(context), username, accountType)
            ?: return null

        return AccountManager.get(context).getUserData(account, KEY_REFRESH_TOKEN)
    }

    fun updateTokenForUserWithName(
        context: Context,
        username: String,
        accountType: String = ACCOUNT_TYPE,
        token: String,
        refreshToken: String
    ) {
        createAccount(username, token, refreshToken)
    }

    @Throws(IOException::class)
    private fun getAccessToken(
        context: Context,
        account: Account?,
        tokenType: String,
        accountType: String
    ): String {
        val accessToken = AccountManager.get(context).peekAuthToken(account, tokenType)
        if (accessToken == null) {
            AccountManager.get(context).invalidateAuthToken(accountType, accessToken)
        }

        try {
            return AccountManager.get(context).blockingGetAuthToken(account, tokenType, true)
        } catch (e: OperationCanceledException) {
            throw IOException("Authentication operation cancelled", e)
        } catch (e: IOException) {
            throw e
        } catch (e: AuthenticatorException) {
            throw IOException("Couldn't connect to authenticator", e)
        }

    }

    companion object {

        const val KEY_REFRESH_TOKEN = "refresh_token"

        fun getUserAccountFor(
            context: Context,
            manager: AccountManager,
            username: String?,
            accountType: String
        ): Account? {
            if (username == null) {
                return null
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                val getAccountsCheck = PermissionCheckManager.Builder()
//                    .context(context)
//                    .forPermission(Manifest.permission.GET_ACCOUNTS)
//                    .create()
//                if (!/* don't notify the listeners immediately */getAccountsCheck.check(false)) {
//                    // the android.permission.GET_ACCOUNTS permission is not granted -> fail
//                    throw MissingPermissionException("The android.permission.GET_ACCOUNTS has not been granted.")
//                }
            }

            val accounts = manager.getAccountsByType(accountType)
            return if (accounts.isEmpty()) {
                null
            } else findAccountsByName(accounts, username)

        }

        fun findAccountsByName(accounts: Array<Account>, name: String): Account? {
            for (account in accounts) {
                if (name == account.name) {
                    return account
                }
            }
            return null
        }
    }

    /**
     * Used for fetching a valid access token from account information. If no valid access token
     * is retrieved a login intent is created
     */
    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        p3: Bundle?
    ): Bundle {
        if (TOKEN_TYPE != authTokenType) {
            // Invalid token type. No idea what to do with this type
            val result = Bundle()
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "Invalid token type: $authTokenType")
            return result
        }

//        var accessToken: String? =
//           accountManager.peekAuthToken(account, TOKEN_TYPE)

//        if (accessToken != null) {
//            // Valid access token retrieved - return it
//            val result = Bundle()
//            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
//            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
//            result.putString(AccountManager.KEY_AUTHTOKEN, accessToken)
//            return result
//        }

        // No valid Access token and Refresh token. The user needs to log in
        return createLoginActivityIntentBundle()
    }

    override fun hasFeatures(
        accountAuthenticatorResponse: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        // return true if the user exists and has all of the specified features.
        val result = Bundle()
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false)
        return result
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        return createLoginActivityIntentBundle()
    }

    private fun createLoginActivityIntentBundle(): Bundle {
        val intent = createLoginActivityIntent()
        val result = Bundle()
        result.putParcelable(AccountManager.KEY_INTENT, intent)
        return result
    }

    /**
     * Provides intent to the Login activity with a prepared bundle of information for the account
     * that needs logging in
     */
    private fun createLoginActivityIntent(): Intent? {
        return loginIntent
    }

    override fun getAuthTokenLabel(p0: String?): String? {
        return null
    }

    override fun confirmCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Bundle?
    ): Bundle? {
        return null
    }

    override fun updateCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle? {
        return null
    }

    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle? {
        return null
    }

}