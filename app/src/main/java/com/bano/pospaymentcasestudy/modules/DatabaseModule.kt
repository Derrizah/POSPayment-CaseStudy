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
class DatabaseModule constructor(val context: Context) {
    @Singleton
    @Provides
    fun provideYourDatabase() =
        Room.databaseBuilder(
            context,
            PaymentDatabase::class.java,
            "payments_database"
        ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: PaymentDatabase) =
        db.getDao()

    @Provides
    fun context(): Context {
        return context
    }
}