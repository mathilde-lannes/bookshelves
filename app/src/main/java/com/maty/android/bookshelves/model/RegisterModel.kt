package com.maty.android.bookshelves.model

import com.maty.android.bookshelves.common.arePasswordsSame
import com.maty.android.bookshelves.common.isEmailValid
import com.maty.android.bookshelves.common.isUsernameValid

data class RegisterRequest(var name: String = "",
                           var email: String = "",
                           var password: String = "",
                           var repeatPassword: String = "") {

  fun isValid(): Boolean = isUsernameValid(name)
      && isEmailValid(email)
      && arePasswordsSame(password, repeatPassword)

}