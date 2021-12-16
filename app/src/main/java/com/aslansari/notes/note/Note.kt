package com.aslansari.notes.note

import java.util.*

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val createdAt: String,
    val editedAt: String,
) {
    constructor(title: String, content: String, imageUrl: String, createdAt: String, editedAt: String) :
            this(UUID.randomUUID().toString(), title, content, imageUrl, createdAt, editedAt)
}
