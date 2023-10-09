package com.codespacepro.moviecompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codespacepro.moviecompose.R

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(69.dp)
            .padding( end = 8.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(start = 30.dp)

        ) {
            Icon(

                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(Color.DarkGray)
                    .height(40.dp)
                    .width(40.dp),
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "",
            )
        }
        Spacer(modifier = Modifier.width(4.dp))


        Column(modifier = Modifier.padding(end = 100.dp)) {
            Text(
                text = "Hello, Smith",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                    letterSpacing = 0.12.sp,
                )
            )
            Text(
                text = "Let’s stream your favorite movie",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF92929D),
                    letterSpacing = 0.12.sp,
                )
            )


        }
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .background(color = Color(0xFF252836), shape = RoundedCornerShape(size = 12.dp))

        ) {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite, contentDescription = "",
                    tint = Color.Red
                )
            }
        }
    }

}