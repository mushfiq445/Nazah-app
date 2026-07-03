// FIXED: Updated package to match the rest of the project
package com.example.nazahapp 

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // FIXED: Removed FirebaseApp.initializeApp(this) 
        // The google-services plugin handles this automatically now.
        
        setContent {
            MaterialTheme {
                Surface {
                    LoginScreen()
                }
            }
        }
    }
}
