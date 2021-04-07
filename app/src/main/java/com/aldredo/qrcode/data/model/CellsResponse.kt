package com.aldredo.qrcode.data.model

data class CellsResponse(
    val error: Boolean,
    val error_text: String,
    val cells: ArrayList<CellsModel>,
) {
    data class CellsModel(
        val barcode: String,
        val type: String,
        val count_series: String,
        val count_goods: String,
        val name: String
    )
}