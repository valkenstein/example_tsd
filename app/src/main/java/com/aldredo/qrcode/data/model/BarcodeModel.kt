package com.aldredo.qrcode.data.model

data class BarcodeModel(
    val error: Boolean,
    val error_text: String?,
    val cell: Cell?
) {
    data class Cell(
        val barcode: String?,
        val name: String?,
        val type: String?,
        val count_series: String?,
        val count_goods: String?,
        val search_type: String?,
    )
}