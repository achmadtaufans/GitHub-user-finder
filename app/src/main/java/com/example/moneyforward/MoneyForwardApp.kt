package com.example.moneyforward

import androidx.multidex.MultiDexApplication
import com.example.moneyforward.di.appModule
import com.example.moneyforward.di.initDependencyInjection
import org.koin.core.context.loadKoinModules

class MoneyForwardApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection(this@MoneyForwardApp)
        loadKoinModules(appModule)
    }
}