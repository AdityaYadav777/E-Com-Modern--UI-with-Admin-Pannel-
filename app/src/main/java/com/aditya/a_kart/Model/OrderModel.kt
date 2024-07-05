package com.aditya.a_kart.Model

data class OrderModel(
    val addressData:AddressModel,
    val product:String="",
    val productUrl:String="",
    val prise:String="",
    val quantity:String="",
    val docId:String="",
    val status:String="",
    val uid:String=""
)
{
    constructor() : this(AddressModel(), "", "", "", "", "", "")
}
