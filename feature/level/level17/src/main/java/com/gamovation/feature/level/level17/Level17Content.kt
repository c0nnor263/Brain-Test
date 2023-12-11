package com.gamovation.feature.level.level17

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.gamovation.core.domain.level.LevelScreenState
import com.gamovation.core.ui.Dimensions
import com.gamovation.core.ui.animation.DrawAnimation
import com.gamovation.core.ui.animation.Durations
import com.gamovation.core.ui.level.interactions.CollisionImage
import com.gamovation.core.ui.level.interactions.DraggableImage
import com.gamovation.core.ui.theme.WordefullTheme

@Composable
fun Level17Content(
    modifier: Modifier = Modifier,
    onLevelAction: (LevelScreenState) -> Unit
) {
    var isBalloonPoppedOut by remember { mutableStateOf(false) }
    var ringsOffset by remember { mutableStateOf(Offset.Zero) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Padding.Medium.value)
    ) {
        val (
            boy,
            girl,
            status,
            rings,
            balloon,
            present
        ) = createRefs()

        createHorizontalChain(boy, status, girl)
        DrawAnimation(
            modifier = Modifier.constrainAs(boy) {
                width = Dimension.fillToConstraints
                height = Dimension.ratio("1:1")
                top.linkTo(parent.top)
                start.linkTo(parent.start)

                centerVerticallyTo(status)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.l17_boy),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        DrawAnimation(
            modifier = Modifier.constrainAs(status) {
                height = Dimension.ratio("1:1")
                width = Dimension.fillToConstraints
                top.linkTo(parent.top)
            },
            delayOrder = 1
        ) {
            Text(
                text = if (isBalloonPoppedOut) {
                    stringResource(R.string.l17_heart_emoji)
                } else {
                    stringResource(
                        R.string.l17_snooze_emoji
                    )
                },
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center

            )
        }
        DrawAnimation(
            modifier = Modifier.constrainAs(girl) {
                width = Dimension.fillToConstraints
                height = Dimension.ratio("1:1")
                top.linkTo(parent.top)
                end.linkTo(parent.end)

                centerVerticallyTo(status)
            },
            delayOrder = 2
        ) {
            Image(
                painter = painterResource(id = R.drawable.l17_girl),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        createHorizontalChain(
            rings,
            balloon,
            present
        )

        OptionsContent(
            isBalloonPoppedOut = isBalloonPoppedOut,
            ringsOffset = ringsOffset,
            rings = rings,
            balloon = balloon,
            present = present,
            onUpdateRingsOffset = { offset ->
                ringsOffset = offset
            },
            onMatch = {
                isBalloonPoppedOut = true
                onLevelAction(
                    LevelScreenState.UserCorrectChoice(
                        com.gamovation.core.domain.R.string.event_level_17_finished
                    )
                )
            }
        )
    }
}

@Composable
internal fun ConstraintLayoutScope.OptionsContent(
    isBalloonPoppedOut: Boolean,
    ringsOffset: Offset,
    rings: ConstrainedLayoutReference,
    balloon: ConstrainedLayoutReference,
    present: ConstrainedLayoutReference,
    onUpdateRingsOffset: (Offset) -> Unit,
    onMatch: () -> Unit
) {
    val balloonScaleAnimation by animateFloatAsState(
        targetValue = if (isBalloonPoppedOut) 0F else 1F,
        label = ""
    )

    val horizontalGuideline = createGuidelineFromTop(0.5F)

    DraggableImage(
        isEnabled = !isBalloonPoppedOut,
        drawableRes = R.drawable.l17_rings,
        modifier = Modifier.constrainAs(rings) {
            width = Dimension.fillToConstraints
            height = Dimension.ratio("1:1")
            top.linkTo(horizontalGuideline)
        }
    ) { offset, _ ->
        onUpdateRingsOffset(offset)
    }

    AnimatedVisibility(
        !isBalloonPoppedOut,
        modifier = Modifier.constrainAs(balloon) {
            width = Dimension.fillToConstraints
            height = Dimension.ratio("1:1")
            top.linkTo(horizontalGuideline)
        },
        exit = scaleOut(tween(Durations.Medium.time))
    ) {
        CollisionImage(
            modifier = Modifier.scale(balloonScaleAnimation),
            defaultDrawableRes = R.drawable.l17_balloon,
            outerOffset = ringsOffset,
            delayOrder = 0,
            onMatch = onMatch
        )
    }

    DraggableImage(
        isEnabled = isBalloonPoppedOut.not(),
        drawableRes = R.drawable.l17_present,
        modifier = Modifier.constrainAs(present) {
            width = Dimension.fillToConstraints
            height = Dimension.ratio("1:1")
            top.linkTo(horizontalGuideline)
        }
    )
}

@Preview
@Composable
fun Level17ContentPreview() {
    WordefullTheme {
        Level17Content(onLevelAction = {})
    }
}
