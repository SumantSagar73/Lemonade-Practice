package com.example.lemonade

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                    LemonadePreview()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    val (step, setStep) = remember { mutableStateOf(0) }
    val (clicksNeeded, setClicksNeeded) = remember { mutableStateOf(0) }
    val (currentClicks, setCurrentClicks) = remember { mutableStateOf(0) }

    // Function to handle clicks and update the step
    val handleImageClick: () -> Unit = {
        when (step) {
            0 -> {
                setStep(1) // From lemon tree to lemon
                setClicksNeeded((2..4).random()) // Random number of clicks needed between 2 and 4
                setCurrentClicks(0) // Reset current clicks
            }
            1 -> {
                val newClicks = currentClicks + 1
                setCurrentClicks(newClicks)
                if (newClicks >= clicksNeeded) {
                    setStep(2) // From lemon to lemonade glass
                }
            }
            2 -> setStep(3) // From lemonade glass to empty glass
            3 -> setStep(0) // From empty glass back to lemon tree
        }
    }

    val imagePainter = when (step) {
        0 -> painterResource(id = R.drawable.lemon_tree)
        1 -> painterResource(id = R.drawable.lemon_squeeze)
        2 -> painterResource(id = R.drawable.lemon_drink)
        else -> painterResource(id = R.drawable.lemon_restart)
    }

    val imageDescription = when (step) {
        0 -> stringResource(id = R.string.lemon_tree)
        1 -> stringResource(id = R.string.lemon)
        2 -> stringResource(id = R.string.glass_of_lemonade)
        else -> stringResource(id = R.string.empty_glass)
    }

    LemonadeImageWithText(
        image = imagePainter,
        text = imageDescription,
        onClick = handleImageClick
    )
}


@Composable
fun LemonadeImageWithText(image: Painter, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onClick,
            shape = CutCornerShape(10),
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = image,
                contentDescription = text,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeApp()
}