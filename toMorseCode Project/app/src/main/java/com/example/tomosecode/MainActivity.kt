package com.example.tomosecode

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tomosecode.ui.theme.ToMoseCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToMoseCodeTheme {
                UIDesign()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIDesign() {
    val context = LocalContext.current
    val morseCodeMap = mapOf(
        'A' to ".-",    'B' to "-...",  'C' to "-.-.",  'D' to "-..",
        'E' to ".",     'F' to "..-.",  'G' to "--.",   'H' to "....",
        'I' to "..",    'J' to ".---",  'K' to "-.-",   'L' to ".-..",
        'M' to "--",    'N' to "-.",    'O' to "---",   'P' to ".--.",
        'Q' to "--.-",  'R' to ".-.",   'S' to "...",   'T' to "-",
        'U' to "..-",   'V' to "...-",  'W' to ".--",   'X' to "-..-",
        'Y' to "-.--",  'Z' to "--..",


        '0' to "-----", '1' to ".----", '2' to "..---", '3' to "...--",
        '4' to "....-", '5' to ".....", '6' to "-....", '7' to "--...",
        '8' to "---..", '9' to "----.", ' ' to " "
    )

    val clipboard : ClipboardManager = LocalClipboardManager.current
    var text by remember { mutableStateOf("") }
    var resultTxt by remember { mutableStateOf("") }

    Column (Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally){
        Image(modifier = Modifier
            .size(120.dp)
            .padding(top = 48.dp),painter = painterResource(id = R.drawable.icon_app), contentDescription ="app icon" )
        Card (
            modifier = Modifier
                .width(300.dp)
                .padding(top = 24.dp),
            shape = Shapes().medium,
            onClick = {
                clipboard.setText(AnnotatedString(resultTxt))
                Toast.makeText(context, "copied!", Toast.LENGTH_SHORT).show()
            }
        ){
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = resultTxt)
            }

        }
        Spacer(modifier = Modifier.size(114.dp))
        OutlinedTextField(value = text,
            onValueChange = { text = it },
            label = { Text(text = "type here")},
            supportingText = { Text(text = stringResource(id = R.string.Note))})
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = {
            if (symbolCheck(text.toUpperCase(), morseCodeMap, context)) {
                val morseResult = converter(text.toUpperCase(), morseCodeMap)
                resultTxt = morseResult
                Toast.makeText(context, "Morse Code: $morseResult", Toast.LENGTH_SHORT).show()
            }
        },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0094EC))) {
                Text(text = "CONVERT")

        }
    }

}
fun symbolCheck(text : String, morseCodeMap: Map<Char, String>, context: Context):Boolean {
    for (i in text.toCharArray()) {
        if (!morseCodeMap.containsKey(i)) {
            Toast.makeText(context, "error typing,read the Note", Toast.LENGTH_SHORT).show()
            return false
        }
    }
    return true
}
fun converter(text: String, morseCodeMap: Map<Char, String>): String {
    val morseCode = StringBuilder()
    for (i in text.toCharArray()) {
        morseCode.append(morseCodeMap[i]).append(" ")
    }
    return morseCode.toString()
}



@Preview(showBackground = true)
@Composable
fun APreview() {
    ToMoseCodeTheme {
        UIDesign()
    }
}