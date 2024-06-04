package com.example.buyergetter.model

data class Order(
    var orderId: String = "",
    val userId: String = "",
    val items: List<OrderItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val orderDate: Long = System.currentTimeMillis(),
    var status: Int = 0
) {
    data class OrderItem(
        val name: String = "",
        val image: Int = 0,
        val quantity: Int = 0,
        val amount: Double = 0.0
    )
}
