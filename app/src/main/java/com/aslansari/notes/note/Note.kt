package com.aslansari.notes.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val createdAt: String,
    val editedAt: String,
) {
    constructor(title: String, content: String, imageUrl: String, createdAt: String, editedAt: String) :
            this(UUID.randomUUID().toString(), title, content, imageUrl, createdAt, editedAt)
}
