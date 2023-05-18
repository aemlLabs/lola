package com.aeml.lolatools.ui.widgets

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aeml.lolatools.R
import com.aeml.lolatools.ui.theme.LolaToolsTheme
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LoadingScreen(
    items: List<String>, showLoading: MutableState<Boolean>

) {

    val uniqueItems = remember { mutableStateListOf<String>() }

    items.forEach { item ->
        if (!uniqueItems.contains(item)) {
            uniqueItems.add(item)
        }
    }

    AnimatedVisibility(

        visible = showLoading.value,
        enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.End) + fadeIn(),
        exit = slideOutHorizontally() + shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()

    ) {

        LolaToolsTheme{
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() ){
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(id = R.string.lola_loading_default_title),
                        style = MaterialTheme.typography.titleMedium
                    )

                    LazyColumn {
                        items(uniqueItems.size) { item ->
                            var animatedText by remember { mutableStateOf("") }

                            LaunchedEffect(Unit) {
                                val loadingText = uniqueItems[item]
                                loadingText.forEachIndexed { index, _ ->
                                    delay(5)
                                    animatedText = loadingText.substring(0, index + 1)
                                }
                            }

                            Text(
                                text = animatedText,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.labelMedium
                            )

                        }
                    }

                }
            }
        }
    }



}