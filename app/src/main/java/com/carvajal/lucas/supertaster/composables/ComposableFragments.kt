package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopRow(heading: String, icon: ImageVector, action: () -> Unit) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = heading,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
            onClick = { action() }
        ) {
            Icon(icon, contentDescription = "", tint = MaterialTheme.colors.onSurface)
        }
    }
}
