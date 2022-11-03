package com.bano.pospaymentcasestudy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Payments")
data class PaymentEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "payment_id") val payment_id: Int,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "date_time") val date_time: Int,
)