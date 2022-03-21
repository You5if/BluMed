package com.component.pharma.model

data class CompleteObj(
    val phInvoiceEntry: PhInvoiceEntry,
    val phInvProdEntry: ArrayList<PhInvProdEntry>
)