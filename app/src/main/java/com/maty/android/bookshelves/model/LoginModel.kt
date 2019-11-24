package com.maty.android.bookshelves.model

import com.maty.android.bookshelves.common.isEmailValid
import com.maty.android.bookshelves.common.isPasswordValid

data class LoginRequest(var email: String = "",
                        var password: String = "") {

  fun isValid() = isEmailValid(email) && isPasswordValid(password)
}