package com.application.moneysense.data.model

data class HistoryResponse(
    val data: List<HistoryData>? = null,
    val status: String? = null
)

data class HistoryData(
    val createdAt: String? = null,
    val authenticity: String? = null,
    val authenticityConfidence: Double? = null,
    val currency: String? = null,
    val id: String? = null,
    val nominalConfidence: Double? = null,
    val userId: String? = null
)
