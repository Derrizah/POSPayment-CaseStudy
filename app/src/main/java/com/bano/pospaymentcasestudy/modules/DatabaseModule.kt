package com.bano.pospaymentcasestudy.modules

import android.content.Context
import androidx.room.Room
import com.bano.pospaymentcasestudy.db.payment.PaymentDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides variables needed for dependency injection related to database
 */
@Module
open class DatabaseModule constructor(val context: Context) {
    @Singleton
    @Provides
    open fun provideDatabase() =
        Room.databaseBuilder(
            context,
            PaymentDatabase::class.java,
            "payments_database"
        ).build()

    @Singleton
    @Provides
    open fun provideDao(db: PaymentDatabase) =
        db.getDao()

    @Provides
    fun context(): Context {
        return context
    }
}