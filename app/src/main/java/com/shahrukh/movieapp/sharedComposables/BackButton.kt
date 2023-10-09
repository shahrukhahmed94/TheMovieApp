package com.shahrukh.movieapp.sharedComposables

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahrukh.movieapp.ui.theme.AppOnPrimaryColor
import com.shahrukh.movieapp.ui.theme.ButtonColor

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier.size(42.dp),
        backgroundColor = ButtonColor,
        contentColor = AppOnPrimaryColor,
        onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "back icon",
        )
    }
}