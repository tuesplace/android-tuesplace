package com.mobile.tuesplace.ui.creategoup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.tuesplace.R
import com.mobile.tuesplace.ui.GradientBorderButtonRound
import com.mobile.tuesplace.ui.theme.BabyBlue

@Composable
fun CreateGroupScreen(
    expanded: Boolean,
    setExpanded: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = TextFieldValue(),
                onValueChange = { TextFieldValue.Saver },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                placeholder = {
                    Text(
                        text = "Име на групата",
                        color = Color.White
                    ) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            TextField(
                value = TextFieldValue(),
                onValueChange = { TextFieldValue.Saver },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                placeholder = {
                    Text(
                        text = "Избиране на клас/ове",
                        color = Color.White
                    ) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            TextField(
                value = TextFieldValue(),
                onValueChange = { TextFieldValue.Saver },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .clickable { setExpanded() },
                placeholder = {
                    Text(
                        text = "Добавяне на учител",
                        color = Color.White
                    ) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            DropDown(list = listOf("Kalina Valeva", "Lachezar Topalov"), expanded = expanded, setExpanded = setExpanded)

            TextField(
                value = TextFieldValue(),
                onValueChange = { TextFieldValue.Saver },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .clickable {  },
                placeholder = {
                    Text(
                        text = "Тип на групата",
                        color = Color.White
                    ) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )
        }

        GradientBorderButtonRound(
            colors = null,
            modifier = Modifier.align(Alignment.BottomCenter),
            paddingValues = PaddingValues(15.dp),
            buttonText = "Създай",
            onLoginClick = { },
            buttonPadding = PaddingValues(15.dp))
    }
}

@Composable
fun ownerItem(name: String, subject: String){
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Card(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically)
                .background(BabyBlue, RoundedCornerShape(8))
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.menu),
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = Color.White
            )

            Text(
                text = subject,
                fontSize = 10.sp,
                color = Color.White
            )
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDown(list: List<String>, expanded: Boolean, setExpanded: () -> Unit){
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { setExpanded() }
    ) {
        LazyColumn {
            item {
                list.forEach {
                    ownerItem(name = it, subject = it)
                }
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview(){
    CreateGroupScreen(false) {}
    //  ownerItem("Kalina Valeva", "Bulgarian language")
}