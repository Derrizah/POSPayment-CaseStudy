package com.bano.pospaymentcasestudy.components

import com.bano.pospaymentcasestudy.base.BaseViewModel
import com.bano.pospaymentcasestudy.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(app: BaseViewModel)
}