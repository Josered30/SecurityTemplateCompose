package com.example.securitytemplate.ui.modules.shared.components


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CustomRoundedShape(
    private val topLeftRadius: Float,
    private val topRightRadius: Float,
    private val bottomLeftRadius: Float,
    private val bottomRightRadius: Float,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(
            path = drawPath(
                size = size,
                topLeftRadius,
                topRightRadius,
                bottomLeftRadius,
                bottomRightRadius
            )
        )
    }

    private fun drawPath(
        size: Size,
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomLeftRadius: Float,
        bottomRightRadius: Float,
    ): Path {

        val topLeftRadiusAux = size.height * topLeftRadius
        val topRightRadiusAux = size.height * topRightRadius
        val bottomLeftRadiusAux = size.height * bottomLeftRadius
        val bottomRightRadiusAux = size.height * bottomRightRadius


        return Path().apply {
            reset()


            // Top left arc
            arcTo(
                rect = Rect(
                    Offset(0f, 0f),
                    Size(topLeftRadiusAux, topLeftRadiusAux)
                ),
                startAngleDegrees = 180.0f,
                sweepAngleDegrees = 90.0f,
                forceMoveTo = false
            )
            lineTo(x = size.width - topRightRadiusAux, y = 0f)


            // Top right arc
            arcTo(
                rect = Rect(
                    Offset(x = size.width - topRightRadiusAux, y = 0f),
                    Size(width = topRightRadiusAux, height = topRightRadiusAux)
                ),
                startAngleDegrees = 270.0f,
                sweepAngleDegrees = 90.0f,
                forceMoveTo = false
            )
            lineTo(x = size.width, y = size.height - bottomRightRadiusAux)


            // Bottom right arc
            arcTo(
                rect = Rect(
                    Offset(
                        x = size.width - bottomRightRadiusAux,
                        y = size.height - bottomRightRadiusAux
                    ),
                    Size(bottomRightRadiusAux, height = bottomRightRadiusAux)
                ),
                startAngleDegrees = 0.0f,
                sweepAngleDegrees = 90.0f,
                forceMoveTo = false
            )
            lineTo(x = bottomLeftRadius, y = size.height)


            // Bottom left arc
            arcTo(
                rect = Rect(
                    Offset(0f, y = size.height - bottomLeftRadiusAux),
                    Size(bottomLeftRadiusAux, height = bottomLeftRadiusAux)
                ),
                startAngleDegrees = 90.0f,
                sweepAngleDegrees = 90.0f,
                forceMoveTo = false
            )
            lineTo(x = 0f, y = topLeftRadiusAux)
          


            close()
        }
    }

}