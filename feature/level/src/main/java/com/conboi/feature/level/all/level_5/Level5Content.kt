package com.conboi.feature.level.all.level_5

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.conboi.core.domain.level.LevelScreenState
import com.conboi.core.ui.Dimensions
import com.conboi.core.ui.Durations
import com.conboi.core.ui.R
import com.conboi.core.ui.animation.DrawAnimation
import com.conboi.core.ui.state.LocalLevelScreenState

@Composable
internal fun Level5Content(
    modifier: Modifier = Modifier,
    onLevelAction: (LevelScreenState) -> Unit
) {
    val levelUIState = LocalLevelScreenState.current
    val firstMarkInteractionSource = remember { MutableInteractionSource() }
    val secondMarkInteractionSource = remember { MutableInteractionSource() }

    val isFirstMarkPressed by firstMarkInteractionSource.collectIsPressedAsState()
    val isSecondMarkPressed by secondMarkInteractionSource.collectIsPressedAsState()

    LaunchedEffect(isFirstMarkPressed, isSecondMarkPressed) {
        if (levelUIState != LevelScreenState.IS_PLAYING) return@LaunchedEffect

        val event = if (isFirstMarkPressed
//            && isSecondMarkPressed TODO Test
        ) {
            LevelScreenState.CORRECT_CHOICE
        } else if (isFirstMarkPressed || isSecondMarkPressed) {
            LevelScreenState.WRONG_CHOICE
        } else {
            return@LaunchedEffect
        }
        onLevelAction(event)
    }


    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Level5Title()

        Spacer(modifier = Modifier.height(8.dp))
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (backgroundImage, firstMark, secondMark, thirdMark, fourthMark,
                fifthNark, sixthMark) = createRefs()


            DrawAnimation(
                modifier = Modifier.constrainAs(backgroundImage) {
                    top.linkTo(parent.top)
                    start.linkTo(firstMark.start)
                    end.linkTo(thirdMark.end)
                    bottom.linkTo(parent.bottom)
                }, delay = Durations.Medium.time.toLong() * 6
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tic_tac_toe_board),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }

            // First Row

            createHorizontalChain(
                firstMark,
                secondMark,
                thirdMark,
                chainStyle = ChainStyle.SpreadInside
            )


            Level5TicTacToeCell(
                modifier = Modifier
                    .constrainAs(
                        firstMark
                    ) {
                        top.linkTo(parent.top)
                    }
                    .padding(Dimensions.Padding.Small.value)
                    .fillMaxWidth(0.3F),
                drawableRes = R.drawable.o_mark,
                delay = 0L
            )

            Level5TicTacToeCell(
                modifier = Modifier

                    .constrainAs(
                        secondMark
                    ) {
                        top.linkTo(firstMark.top)
                        bottom.linkTo(firstMark.bottom)
                    }
                    .padding(Dimensions.Padding.Small.value)
                    .fillMaxWidth(0.3F),
                drawableRes = R.drawable.o_mark,
                delay = Durations.Medium.time.toLong()
            )
            Level5TicTacToeCell(
                modifier = Modifier

                    .constrainAs(
                        thirdMark
                    ) {
                        top.linkTo(secondMark.top)
                        bottom.linkTo(secondMark.bottom)
                    }
                    .padding(Dimensions.Padding.Small.value)
                    .fillMaxWidth(0.3F),
                interactionSource = firstMarkInteractionSource,
                alpha = if (isFirstMarkPressed) 1f else 0f,
                drawableRes = R.drawable.x_mark,
            )

            // Second Row

            Level5TicTacToeCell(
                modifier = Modifier
                    .constrainAs(
                        fourthMark
                    ) {
                        top.linkTo(thirdMark.bottom)
                        start.linkTo(thirdMark.start)
                        end.linkTo(thirdMark.end)
                        bottom.linkTo(sixthMark.top)

                    }
                    .padding(Dimensions.Padding.Small.value)
                    .fillMaxWidth(0.3F),
                drawableRes = R.drawable.x_mark,
                delay = Durations.Medium.time.toLong() * 2
            )


            // Third Row

            createHorizontalChain(fifthNark, sixthMark, chainStyle = ChainStyle.SpreadInside)

            Level5TicTacToeCell(
                modifier = Modifier

                    .constrainAs(
                        fifthNark
                    ) {

                        bottom.linkTo(parent.bottom)
                    }
                    .padding(Dimensions.Padding.Small.value)
                    .fillMaxWidth(0.3F),
                drawableRes = R.drawable.o_mark,
                delay = Durations.Medium.time.toLong() * 3
            )


            Level5TicTacToeCell(
                modifier = Modifier
                    .constrainAs(
                        sixthMark
                    ) {
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(Dimensions.Padding.Small.value)
                    .fillMaxWidth(0.3F),
                drawableRes = R.drawable.x_mark,
                interactionSource = secondMarkInteractionSource,
                alpha = if (isSecondMarkPressed) 1f else 0f,
            )

        }
    }
}


@Preview
@Composable
fun Level5ContentPreview() {
    Level5Content(onLevelAction = {})
}