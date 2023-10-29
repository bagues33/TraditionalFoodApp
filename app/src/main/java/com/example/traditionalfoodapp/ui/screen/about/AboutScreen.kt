package com.example.traditionalfoodapp.ui.screen.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.traditionalfoodapp.R
import com.example.traditionalfoodapp.ui.theme.TraditionalFoodAppTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = stringResource(R.string.my_image),
            contentDescription = "about_image",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.about_me),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.my_name),
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.my_email),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    TraditionalFoodAppTheme() {
        AboutScreen()
    }
}