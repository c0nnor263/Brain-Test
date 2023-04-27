package com.conboi.wordefull.presentation.composables.header_bar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.conboi.core.navigation.Screens
import com.conboi.core.ui.Dimensions
import com.conboi.core.ui.R
import com.conboi.core.ui.defaultSpringAnimation
import com.conboi.core.ui.state.LocalCurrency
import com.conboi.wordefull.presentation.composables.header_bar.options.HeaderBarHomeOption
import com.conboi.wordefull.presentation.composables.header_bar.options.HeaderBarLevelOption
import com.conboi.wordefull.presentation.composables.header_bar.options.HeaderBarMenuOption
import com.conboi.wordefull.presentation.composables.header_bar.options.HeaderBarSettingsOption

@Composable
fun HeaderBar(navController: NavHostController) {

    val currency = LocalCurrency.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination


    Row(
        modifier = Modifier.padding(
            vertical = Dimensions.Padding.Small.value,
            horizontal = Dimensions.Padding.Medium.value
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedContent(
            targetState = currentDestination?.route ?: "",
            label = "",
            transitionSpec = {
                (scaleIn(animationSpec = defaultSpringAnimation()) + fadeIn() with
                        scaleOut(animationSpec = defaultSpringAnimation()) + fadeOut()
                        )
                    .using(
                        SizeTransform(clip = false)
                    )
            }
        ) {
            Row {
                when (it) {
                    Screens.Home.route -> {
                        HeaderBarHomeOption(
                            onNavigateToSettings = {
                                navController.navigate(Screens.Settings.route)
                            },
                            onNavigateToMenu = {
                                navController.navigate(Screens.Menu.route)
                            },
                        )
                    }

                    Screens.Level().route -> {
                        HeaderBarLevelOption(
                            onNavigateToHome = {
                                navController.navigate(Screens.Home.route)
                            },
                            onNavigateToMenu = {
                                navController.navigate(Screens.Menu.route)
                            },
                        )
                    }

                    Screens.Menu.route -> {
                        HeaderBarMenuOption(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    Screens.Settings.route -> {
                        HeaderBarSettingsOption(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        Card(
            modifier = Modifier.padding(Dimensions.Padding.Small.value),
            shape = Dimensions.RoundedShape.Large.value
        ) {
            TextButton(onClick = {
                // TODO Navigate to Currency
            }) {
                Text(
                    text = currency.toString(), style = MaterialTheme.typography.headlineMedium
                )
                Image(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.lamp),
                    contentDescription = null
                )
            }
        }
    }
}