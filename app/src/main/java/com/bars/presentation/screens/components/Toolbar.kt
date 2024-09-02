package com.bars.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.bars.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun toolbar (title: String) {

    CenterAlignedTopAppBar(
        title = {
            Box(modifier = Modifier
                .background(colorResource(id = R.color.main_color)))
            {
                Text(title)
            }
        },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =colorResource(id = R.color.main_color),
                    titleContentColor = Color.White
                )
            )
}



