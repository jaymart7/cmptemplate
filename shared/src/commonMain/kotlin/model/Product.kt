package model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val dateTime: LocalDateTime
)

@Serializable
data class ProductRequest(
    val title: String,
    val dateTime: LocalDateTime
)