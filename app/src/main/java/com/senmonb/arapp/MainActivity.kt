package com.senmonb.arapp

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.ar.core.ArCoreApk
import com.senmonb.arapp.ui.theme.ARAppTheme

class MainActivity : ComponentActivity() {
    companion object{
        private var isEnabled = false

        fun getIsEnabled() :Boolean = isEnabled
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARAppTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
    fun maybeEnableArrButton() {
        ArCoreApk.getInstance().checkAvailabilityAsync(this.applicationContext) { availability ->
            if (availability.isSupported) {
                isEnabled = true
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier){
    val alertText = rememberSaveable{ mutableStateOf(AlertText.Stop)}
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick =
            {
                alertText.value = AlertText.Start
            }, modifier = Modifier ) {
                Text(text = stringResource(id = R.string.ar_start))
            }
            Text(text = "${alertText.value}!")
        }
    }
}

private enum class AlertText{
    Stop,Start

}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen(modifier = Modifier)
}

