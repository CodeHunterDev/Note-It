package com.riyazuddin.noteit.common

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
