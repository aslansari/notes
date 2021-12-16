package com.aslansari.notes.util

import java.util.regex.Pattern

object NoteInputValidator {

    private val URL_PATTERN =  Pattern.compile("((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)")

    fun validateURL(title: String): Boolean {
        return URL_PATTERN.matcher(title).matches()
    }
}