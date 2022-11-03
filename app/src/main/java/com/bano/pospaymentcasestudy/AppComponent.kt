package com.bano.pospaymentcasestudy

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun inject(app: MainActivity)
}