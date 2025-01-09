package co.wawand.composetypesafenavigation.presentation.screens.lib.camera

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun CameraGrid(
    rows: Int = 4,
    columns: Int = 3,
    lineColor: Color = Color.White,
    lineAlpha: Float = 0.5f
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        for (i in 1 until rows) {
            drawLine(
                start = Offset(x = 0f, y = (canvasHeight * i / rows)),
                end = Offset(x = canvasWidth, y = (canvasHeight * i / rows)),
                color = lineColor,
                alpha = lineAlpha
            )
        }

        for (i in 1 until columns) {
            drawLine(
                start = Offset(x = canvasWidth * i / columns, y = 0f),
                end = Offset(x = canvasWidth * i / columns, y = canvasHeight),
                color = lineColor,
                alpha = lineAlpha
            )
        }
    }
}