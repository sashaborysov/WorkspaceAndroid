package com.workspaceandroid.domain.models.phrase

data class Phrase(
    val id: Long,
    val createdAt: Long,
    val formattedDate: String,
    val text: String,
    val imgUrl: String,
    val examples: List<String>,
    val definition: String,

    var isExpanded: Boolean
)