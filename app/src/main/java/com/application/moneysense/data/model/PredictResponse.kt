package com.application.moneysense.data.model

data class PredictResponse(
	val data: PredictionData? = null,
	val status: String? = null
)

data class PredictionData(
	val createdAt: String? = null,
	val authenticity: String? = null,
	val authenticityConfidence: Double? = null,
	val currency: String? = null,
	val id: String? = null,
	val nominalConfidence: Double? = null,
	val userId: String? = null
)
