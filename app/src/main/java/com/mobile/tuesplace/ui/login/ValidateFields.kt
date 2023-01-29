package com.mobile.tuesplace.ui.login

import android.util.Patterns
import java.util.regex.Pattern

object ValidateFields {
    fun validatePassword(password : String) : Boolean {
        //ArrayList<ValidatePasswordState>
        val array = arrayListOf<ValidatePasswordState>()

        val regexSymbols: Pattern = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")

        if (password.any { it.isUpperCase() }) {
            array.add(ValidatePasswordState.CAPITAL_LETTER)
        }
        if (password.any { it.isDigit() }) {
            array.add(ValidatePasswordState.NUMBERS)

        }
        if (regexSymbols.matcher(password).find()) {
            array.add(ValidatePasswordState.SPECIAL_SYMBOLS)
        }
        if (password.length >= 6) {
            array.add(ValidatePasswordState.AT_LEAST_EIGHT_CHARS)
        }

        return array.size == 4
    }

    fun isValidEmail(email : String) : Boolean {
        if (email.isNotBlank()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true
            }
        }
        return false
    }

}

enum class ValidatePasswordState(val id: Int) {
    CAPITAL_LETTER(0),
    NUMBERS(1),
    SPECIAL_SYMBOLS(2),
    AT_LEAST_EIGHT_CHARS(3)
}