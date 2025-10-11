package dev.schedler.amortify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.schedler.amortify.data.local.db.getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbBuilder = getDatabaseBuilder(this)

        setContent {
            App(dbBuilder)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(getDatabaseBuilder(LocalContext.current))
}