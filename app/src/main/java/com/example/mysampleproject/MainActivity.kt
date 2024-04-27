package com.example.mysampleproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
    }
}