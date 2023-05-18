package com.aeml.lolatools.ui.theme.ChapterContainer

import android.app.Activity
import android.os.Build
import android.os.Build.VERSION
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aeml.lolatools.BuildConfig
import com.aeml.lolatools.R
import com.aeml.lolatools.ui.chaptercontainer.ChapterViewModel
import com.aeml.lolatools.ui.theme.LolaToolsTheme

@Composable
fun ChapterScreen(chapterViewModel: ChapterViewModel = viewModel()) {
    val chapterUiState by chapterViewModel.uiState.collectAsState()
    val mediumPadding = 16.dp

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${stringResource(R.string.app_name)}",
        )
        Text(
            text = "${stringResource(R.string.app_name_complete)}  ${BuildConfig.VERSION_NAME}",
        )

        GameLayout(
            onUserGuessChanged = { chapterViewModel.updateUserGuess(it) },
            wordCount = chapterUiState.currentWordCount,
            userGuess = chapterViewModel.userGuess,
            onKeyboardDone = { chapterViewModel.checkUserGuess() },
            currentScrambledWord = chapterUiState.currentScrambledWord,
            isGuessWrong = chapterUiState.isGuessedWordWrong,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { chapterViewModel.checkUserGuess() }
            ) {
                Text(
                    text = "Échale apá",
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { chapterViewModel.skipWord() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nel nomás",
                    fontSize = 16.sp
                )
            }
        }

        GameStatus(score = chapterUiState.score, modifier = Modifier.padding(20.dp))

        if (chapterUiState.isGameOver) {
            FinalScoreDialog(
                score = chapterUiState.score,
                onPlayAgain = { chapterViewModel.resetGame() }
            )
        }
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text ="La puntuación:"+score,
            modifier = Modifier.padding(8.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout(
    currentScrambledWord: String,
    wordCount: Int,
    isGuessWrong: Boolean,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = 10.dp

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = "La palabración: "+wordCount,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentScrambledWord)
            Text(
                text = "La instrucción",
                textAlign = TextAlign.Center)
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessWrong) {
                        Text("Fallo")
                    } else {
                        Text("Entra la palabra")
                    }
                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}

/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = "Felicidadaa") },
        text = { Text(text = "Tu punto:"+score) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text ="Sali")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text =
                "tseesesese")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    LolaToolsTheme {
        ChapterScreen()
    }
}