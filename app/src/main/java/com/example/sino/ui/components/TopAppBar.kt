package com.example.sino.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sino.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinoTopAppBar(
    screenName: String,
    showBackButton: Boolean = false,
    onBack: () -> Unit = {},
    showBreathingIcon: Boolean = true,
    onBreathingExercisesClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.height(64.dp),
        windowInsets = WindowInsets(0.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.background,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(screenName)
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            if (showBreathingIcon) {
                IconButton(onClick = onBreathingExercisesClick) {
                    Icon(
                        Icons.Outlined.Spa,
                        contentDescription = stringResource(R.string.breathing_exercises)
                    )
                }
            }
        }
    )
}