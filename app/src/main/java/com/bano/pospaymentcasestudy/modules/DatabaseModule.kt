package com.bano.pospaymentcasestudy.modules

import android.content.Context
import androidx.room.Room
import com.bano.pospaymentcasestudy.db.payment.PaymentDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule constructor(val context: Context) {
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase() =
        Room.databaseBuilder(
        context,
        PaymentDatabase::class.java,
        "payments_database"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: PaymentDatabase) = db.getDao() // The reason we can implement a Dao for the database

    @Provides
    fun context(): Context {
        return context
    }
}