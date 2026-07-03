package com.example.nazahapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.PrintWriter
import java.io.StringWriter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // গ্লোবাল ক্র্যাশ ক্যাচার - ব্যাকগ্রাউন্ডের যেকোনো ক্র্যাশ ধরতে
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val sw = StringWriter()
            throwable.printStackTrace(PrintWriter(sw))
            val errorLog = sw.toString()
            
            // ক্র্যাশ হলে লাল স্ক্রিনে লগ দেখাবে
            runOnUiThread {
                showCrashScreen(errorLog)
            }
        }

        try {
            setContent {
                MaterialTheme {
                    Surface {
                        LoginScreen()
                    }
                }
            }
        } catch (e: Throwable) {
            // UI বা Compose-এর কোনো ক্র্যাশ হলে সরাসরি লাল স্ক্রিনে দেখাবে
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            showCrashScreen(sw.toString())
        }
    }
    
    // ক্র্যাশ রিপোর্ট দেখানোর জন্য কাস্টম স্ক্রিন
    private fun showCrashScreen(errorText: String) {
        setContent {
            MaterialTheme {
                Surface(color = Color(0xFFB00020)) { // গাঢ় লাল রং
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "APP CRASHED! (Logcat)", 
                            color = Color.White, 
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = errorText, color = Color.White)
                    }
                }
            }
        }
    }
}
