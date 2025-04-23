package com.patchwork

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val author: String,
    val title: String,
    val isbn: String,
    val isReference: Boolean = false,
    var isAvailable: Boolean = true
)
