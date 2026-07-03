package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // আল্টিমেট ক্র্যাশ ক্যাচার - ক্র্যাশ হলে ফাইলে সেভ করবে
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            try {
                // ফাইলটি সেভ হবে: Android/data/com.aistudio.nazah.abcdxy/files/CRASH_LOG.txt
                val file = File(getExternalFilesDir(null), "CRASH_LOG.txt")
                file.writeText(e.toString() + "\n" + e.stackTraceToString())
            } catch (ex: Exception) {
                // Ignore
            }
            System.exit(1)
        }

        // সব UI বাদ দিয়ে জাস্ট এই লেখাটা প্রিন্ট করব
        setContent {
            Text(text = "Hello Boss! The App is Finally Working!")
        }
    }
}
