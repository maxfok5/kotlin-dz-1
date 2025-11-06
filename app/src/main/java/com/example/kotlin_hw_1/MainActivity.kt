package com.example.kotlin_hw_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kotlin_hw_1.ui.theme.Kotlin_hw_1Theme

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Kotlin_hw_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(1.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SquareGrid()
                    }
                }
            }
        }
    }
}

@Composable
fun SquareGrid() {
    val context = LocalContext.current
    val columns = if (LocalConfiguration.current.orientation == 1) {
        context.resources.getInteger(R.integer.portrait_columns)
    } else {
        context.resources.getInteger(R.integer.landscape_columns)
    }

    var squares by rememberSaveable { mutableStateOf<List<Int>>(emptyList()) }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val minDimension = min(screenWidth, screenHeight)

    val buttonSize = (minDimension * 0.1f).dp
    val padding = (minDimension * 0.02f).dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = padding)
        ) {
            items(squares.size) { index ->
                SquareCard(
                    index = squares[index],
                    size = (screenWidth * (92f / columns) / 100f).dp
                )
            }
        }

        AddButton(
            onClick = {
                val newSquare = if (squares.isEmpty()) 0 else squares.last() + 1
                squares = squares + newSquare
            },
            size = buttonSize,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = padding * 2)
        )
    }
}

@Composable
fun SquareCard(index: Int, size: Dp) {
    val backgroundColor = if (index % 2 == 0) {
        Color(ContextCompat.getColor(LocalContext.current, R.color.red))
    } else {
        Color(ContextCompat.getColor(LocalContext.current, R.color.blue))
    }

    Box(
        modifier = Modifier
            .size(size)
            .padding(2.dp)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = index.toString(),
            color = Color.White,
            fontSize = (size.value * 0.4f).sp
        )
    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Color(ContextCompat.getColor(LocalContext.current, R.color.button))
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+",
            color = Color.Black,
            fontSize = (size.value * 0.5f).sp
        )
    }
}