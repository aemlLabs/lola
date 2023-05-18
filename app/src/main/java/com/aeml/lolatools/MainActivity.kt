package com.aeml.lolatools

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aeml.lolatools.tools.PermissionsManager
import com.aeml.lolatools.ui.chaptercontainer.ChapterViewModel
import com.aeml.lolatools.ui.homecontainer.HomeScreen
import com.aeml.lolatools.ui.theme.ChapterContainer.ChapterScreen
import com.aeml.lolatools.ui.widgets.LoadingScreen
import kotlinx.coroutines.delay



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageFlipAnimation()

        }

        PermissionsManager(this).requestPermissions()
    }











    enum class Page { FIRST, SECOND }

    fun Page.toggle(): Page = if (this == Page.FIRST) Page.SECOND else Page.FIRST
    @Composable
    fun PageFlipAnimation() {

        var currentPage by remember { mutableStateOf(Page.FIRST) }
        val onButtonClick: () -> Unit = {

            // Lógica de manejo del clic del botón
            currentPage = currentPage.toggle()

        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(

                visible = currentPage == Page.FIRST,
                enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.End) + fadeIn(),
                exit = slideOutHorizontally() + shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()

            ) {
                HomeScreen(onButtonClick)

            }

            AnimatedVisibility(
                visible = currentPage == Page.SECOND,
                enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.End) + fadeIn(),
                exit = slideOutHorizontally() + shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
            ) {
                val showLoading = remember { mutableStateOf(true) }



                    val loadingLogging = remember { mutableStateListOf<String>() }

                    LoadingScreen(
                        loadingLogging,
                        showLoading
                    )
                    LaunchedEffect(Unit) {
                        delay(1000) // Llamada a delay dentro de un contexto suspendido
                        loadingLogging.add(resources.getString(R.string.lola_home_step_1))
                        delay(300)
                        loadingLogging.add(resources.getString(R.string.lola_home_step_2))
                        delay(400)
                        loadingLogging.add(resources.getString(R.string.lola_home_step_3))
                        delay(500)
                        loadingLogging.add(resources.getString(R.string.lola_home_step_4))
                        delay(1500)
                        showLoading.value = false


                        // Resto del código después del retraso
                    }
                ChapterScreen(ChapterViewModel())
                    //loadingMessages.value = ">>Restaurando sesión..."
                    //showLoading.value = false

            }
        }


    }

}

