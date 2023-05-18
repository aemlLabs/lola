package com.aeml.lolatools.ui.homecontainer

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aeml.lolatools.BuildConfig
import com.aeml.lolatools.R
import com.aeml.lolatools.ui.theme.*
import kotlinx.coroutines.delay
import java.util.*

data class Avatar(
    val AvatarRef: Painter,
    val AvatarDesc:String
)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(onButtonClick: () -> Unit) {

    val images = listOf(
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_1),
            AvatarDesc = stringResource(id = R.string.app_name),

            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_2),
            AvatarDesc = stringResource(id = R.string.app_name),

            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_2),
            AvatarDesc = stringResource(id = R.string.app_name),

            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_4),
            AvatarDesc = stringResource(id = R.string.app_name),

            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_5),
            AvatarDesc = stringResource(id = R.string.app_name),

            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_6),
            AvatarDesc = stringResource(id = R.string.app_name),

            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_7),
            AvatarDesc = stringResource(id = R.string.app_name),
            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_8),
            AvatarDesc = stringResource(id = R.string.app_name),
            ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_9),
            AvatarDesc = stringResource(id = R.string.app_name),
        ),
        Avatar(
            AvatarRef = painterResource(id = R.drawable.lola_random_10),
            AvatarDesc = stringResource(id = R.string.app_name),
        )
    )
    val random = Random()

// Generate a random integer number from 0 to 10.
    val randomNumber = random.nextInt(images.size)
    val mediumPadding = 15.dp
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0x31E9DBFF),
                Color(0x17BA68C8),
                Color(0x22DB7E7E),
                Color(0x28FFB74D),
                Color(0x6DFFF176),
                Color(0x2DAED581),
                Color(0x204DD0E1),
                Color(0x1B9575CD)
            )
        )
    }
    rainbowColorsBrush.intrinsicSize
    val rotationState = remember { mutableStateOf(0.dp) }

    LaunchedEffect(key1 = "rotation") {
        while (rotationState.value <= 130.dp) {
            delay(10)
            rotationState.value += 4.0.dp
        }
    }


    var currentColorIndex = remember { mutableStateOf(0) } // Índice del color actual

    val infiniteTransition = rememberInfiniteTransition() // Transición infinita
    val colors = listOf(
        Color(0x31E9DBFF),
        Color(0x5EBA68C8),
        Color(0x65DB7E7E),
        Color(0x61FFB74D),
        Color(0x7EFFF176),
        Color(0x65AED581),
        Color(0x604DD0E1),
        Color(0x469575CD),
        Color(0x604DD0E1),
        Color(0x65AED581),
        Color(0x7EFFF176),
        Color(0x61FFB74D),
        Color(0x65DB7E7E),
        Color(0x5EBA68C8)
    )

    val color = infiniteTransition.animateColor(
        initialValue = colors[currentColorIndex.value],
        targetValue = colors[(currentColorIndex.value + 1) % colors.size], // Siguiente color en la lista
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000), // Duración de la animación de transición
            repeatMode = RepeatMode.Restart
        )
    )


    LolaToolsTheme {
        Surface(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()

                    .padding(mediumPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "${stringResource(R.string.app_name)}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${stringResource(R.string.app_name_complete)}  ${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Secondary
                )
                Box( modifier = Modifier.fillMaxSize().background(Color.Transparent),
                    contentAlignment = Alignment.Center){
                    Image(
                        painter = images[randomNumber].AvatarRef,
                        contentDescription = stringResource(id = R.string.app_name),
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .alpha(0.75f)
                            .padding(40.dp)
                            .width(rotationState.value)
                            .height(200.dp)
                            .border(
                                BorderStroke(150.dp, rainbowColorsBrush),
                                CircleShape,
                            )
                            .clip(CircleShape)

                    )
                }



                LargeFloatingActionButton(
                    containerColor = Color.White,
                    onClick = { onButtonClick() },
                ) {
                    Icon(
                        Icons.Filled.PowerSettingsNew,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                        tint = color.value
                    )
                }
            }
        }

    }



}




