package com.bano.pospaymentcasestudy.db.payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity used to represent a row in payments database
 */
@Entity(tableName = "payments_database")
data class Payment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payment_id")
    val paymentID: Int,

    @ColumnInfo(name = "amount")
    val amount: Int,

    @ColumnInfo(name = "date_time")
    val dateTime: String,

    @ColumnInfo(name = "session_id")
    val sessionID: String,

    @ColumnInfo(name = "qr_data")
    val qrData: String,
)