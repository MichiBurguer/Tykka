package com.example.tykka.data.network

import com.google.gson.annotations.SerializedName


data class EmisorDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nombreEmpresa: String,
    @SerializedName("username") val rucOIdentificacion: String,
    @SerializedName("email") val emailContacto: String,
    @SerializedName("phone") val telefono: String,
    @SerializedName("website") val sitioWeb: String
)