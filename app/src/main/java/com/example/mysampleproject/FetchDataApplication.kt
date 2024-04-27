package com.example.mysampleproject

import android.app.Application
import android.content.Context

class FetchDataApplication : Application() {
    companion object {
        lateinit var baseApplicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        baseApplicationContext = this
    }
}