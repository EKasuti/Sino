package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sino.R
import com.example.sino.ui.components.WellnessScoreCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Bar
        TopAppBar(
            modifier = Modifier.height(64.dp),
            windowInsets = WindowInsets(0.dp),
            title = { Text(text = stringResource(R.string.app_name)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.background
            ),
            actions = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Outlined.AccountCircle,  contentDescription = stringResource(R.string.profile))
                }
            }
        )

        Spacer(Modifier.height(4.dp))

        // Wellness Score Card
        // TODO: Replace the score once api from data science is live
        WellnessScoreCard(
            score = 7.5f,
        )
    }
}

