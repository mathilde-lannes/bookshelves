package com.maty.android.bookshelves.common

import java.util.regex.Pattern

const val EMAIL_REGEX = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
const val MIN_CREDENTIAL_LENGTH = 6
const val MIN_JOKE_LENGTH = 10

fun isEmailValid(email: String) = Pattern.matches(EMAIL_REGEX, email)

fun isPasswordValid(password: String) = password.length >= MIN_CREDENTIAL_LENGTH

fun isUsernameValid(username: String) = username.length >= MIN_CREDENTIAL_LENGTH

fun arePasswordsSame(password: String, repeatPassword: String) = isPasswordValid(password) && isPasswordValid(repeatPassword) && password==repeatPassword

fun isValidJoke(jokeText: String) = jokeText.length >= MIN_JOKE_LENGTH