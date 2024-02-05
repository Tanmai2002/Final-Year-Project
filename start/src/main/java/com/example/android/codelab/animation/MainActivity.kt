/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelab.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.codelab.animation.ui.AnimationCodelabTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationCodelabTheme {

            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier
){
//    Text(
//        stringResource(R.string.Text),
//        color = Color.Red,
//        fontWeight = FontWeight.Bold,
//        fontSize = 15.sp,
//        modifier = modifier
//            .fillMaxWidth()
//            .heightIn(min = 10.dp)
//    )
    Image(
        painter = painterResource(R.drawable.tensorflow),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .size(50.dp)
            .height(160.dp)
            .width(160.dp)
    )

}

@Composable
fun TwoText(modifier: Modifier = Modifier, ) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Training", fontSize = 20.sp
        )
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Inference", fontSize = 20.sp
        )
    }
}

@Composable
fun Sample(
    modifier: Modifier = Modifier
){

    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(("Collect Sample"),
        modifier = modifier
            .drawBehind {
                drawRect(
                    color = Color.Gray
                )
            }
            .padding(horizontal = 40.dp, vertical = 10.dp),
        color = Color.White
        )
    }
}

@Composable
fun Count(
    modifier: Modifier = Modifier
){
    Row {
        Column {
            var count1 by remember { mutableIntStateOf(0) }

            Row(Modifier.padding(top = 2.dp)){
                Button(onClick = { count1++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .heightIn(60.dp)
                ) {Column{
                    Text("1", fontSize = 20.sp)
                    Text("$count1", fontSize = 15.sp)}
                }
            }
        }
        Column {
            var count2 by remember { mutableIntStateOf(0) }
            Row(Modifier.padding(top = 2.dp)){
                Button(onClick = { count2++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .heightIn(60.dp)
//                        .background(color = 0xff888888)
                ) {Column{
                    Text("2", fontSize = 20.sp)
                    Text("$count2", fontSize = 15.sp)}
                }
            }
        }
        Column {
            var count3 by remember { mutableIntStateOf(0) }
            Row(Modifier.padding(top = 2.dp)){
                Button(onClick = { count3++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .heightIn(60.dp)
                ) {Column{
                    Text("3", fontSize = 20.sp)
                    Text("$count3", fontSize = 15.sp)}
                }
            }
        }
        Column {
            var count4 by remember { mutableIntStateOf(0) }

            Row(Modifier.padding(top = 2.dp)){
                Button(
                    onClick = { count4++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .heightIn(60.dp)
                ) {Column{
                    Text("4", fontSize = 20.sp)
                    Text("$count4", fontSize = 15.sp)}
                }
            }
        }

    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){

    Column {
        Header(modifier.padding(vertical = 16.dp))
        TwoText()
        Spacer(modifier.padding(vertical = 150.dp))
        Sample(modifier.padding(vertical = 2.dp))
        Count(modifier.padding(bottom = 10.dp))
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFE8F3ED)
//@Composable
//fun HeaderPreview() {
//    AnimationCodelabTheme { Header(Modifier.padding(4.dp)) }
//}

//@Preview(showBackground = true, backgroundColor = 0xFFE8F3ED)
//@Composable
//fun CountPreview(){
//    AnimationCodelabTheme{Count(Modifier.padding(4.dp))}
//}

@Preview(showBackground = true, backgroundColor = 0xFFE8F3ED)
@Composable
fun HomeScreenPreview() {
    AnimationCodelabTheme { HomeScreen(Modifier.padding(20.dp)) }
}

//@Preview(showBackground = true, backgroundColor = 0xFFE8F3ED)
//@Composable
//fun SamplePreview() {
//    AnimationCodelabTheme {Sample(Modifier.padding(4.dp)) }
//}

//@Preview(showBackground = true, backgroundColor = 0xFFE8F3ED)
//@Composable
//fun TwoTextPreview() {
//    AnimationCodelabTheme {TwoText(Modifier.padding(4.dp))}
//}