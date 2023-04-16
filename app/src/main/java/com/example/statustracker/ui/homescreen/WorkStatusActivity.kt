package com.example.statustracker.ui.homescreen

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.statustracker.R
import com.example.statustracker.model.AlertOption
import com.example.statustracker.model.WorkStatusItemModel
import com.example.statustracker.router.StatusTrackerRouter
import com.example.statustracker.ui.loginScreen.LoginViewModel
import com.example.statustracker.ui.theme.backgroundColorPrimary
import com.example.statustracker.warehouse.Utils.getUtcTime
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val statusReportViewModel: HomeScreenViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatusTrackerRouter(
                loginViewModel = loginViewModel,
                homeScreenViewModel = statusReportViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusTrackerScreenPreview() {
    WorkStatusScreen(
        statusListItem = WorkStatusItemModel(
            date = "",
            palani = "",
            balaji = "",
            saran = "",
            fazil = "",
            maruthu = "",
            abdullah = "",
            palaniSlackId = "",
            balajiSlackId = "",
            saranSlackId = "",
            fazilSlackId = "",
            maruthuSlackId = "",
            abdullahSlackId = "",
            lastUpdate = 0
        ),
        alertOptions = listOf(),
        onOptionSelected = {},
        onSubmitTask = {},
        sendAlertToSlack = {},
        isRefreshing = true,
        onRefresh = {},
        isVerified = false
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkStatusScreen(
    statusListItem: WorkStatusItemModel,
    alertOptions: List<AlertOption>,
    onOptionSelected: () -> Unit,
    onSubmitTask: () -> Unit,
    sendAlertToSlack: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    isVerified: Boolean?
) {
    val showAlert = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = Color.Black.copy(0.5f),
        sheetContent = {
            BottomSheetContent()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Card(
                backgroundColor = backgroundColorPrimary,
                shape = RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                ),
                elevation = 10.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp, top = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Spacer(modifier = Modifier.weight(.4f))

                        if (isVerified == true) {
                            IconButton(
                                onClick = {

                                }) {
                                Icon(
                                    Icons.Filled.Verified,
                                    contentDescription = "verified",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                showAlert.value = true
                            }) {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = "alarm",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .background(backgroundColorPrimary)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Column(
                            modifier = Modifier.graphicsLayer {
                                translationY = -50f
                            }
                        ) {
                            Text(
                                text = getUtcTime(statusListItem.date),
                                fontSize = 12.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Welcome, \n    Team!",
                                fontSize = 22.sp,
                                color = Color.White,
                                lineHeight = 30.sp
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.appbarbackgound),
                            contentDescription = "TopImage",
                            modifier = Modifier
                                .size(110.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .height(520.dp)
                    .graphicsLayer {
                        translationY = 400f
                    }
            ) {
                SwipeRefresh(
                    modifier = Modifier
                        .weight(1f, true),
                    state = swipeRefreshState,
                    onRefresh = {
                        onRefresh()
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(10.dp),
                        ) {
                            item {
                                WorkProgressItems(
                                    name = "PALANI",
                                    statusListItem.palani,
                                    titlecolor = Color(0xFF655757),
                                    taskcolor = Color(0xFFFFEFE7)
                                )
                                WorkProgressItems(
                                    name = "BALAJI",
                                    statusListItem.balaji,
                                    titlecolor = Color(0xFFEA698F),
                                    taskcolor = Color(0xFFFFD8E3)
                                )
                                WorkProgressItems(
                                    name = "SARAN",
                                    statusListItem.saran,
                                    titlecolor = Color(0xFF705186),
                                    taskcolor = Color(0xFFEADFF3)
                                )
                                WorkProgressItems(
                                    name = "FAZIL",
                                    statusListItem.fazil,
                                    titlecolor = Color(0xFF655757),
                                    taskcolor = Color(0xFFFFEFE7)
                                )
                                WorkProgressItems(
                                    name = "MARUTHU",
                                    statusListItem.maruthu,
                                    titlecolor = Color(0xFFEA698F),
                                    taskcolor = Color(0xFFFFD8E3)
                                )
                                WorkProgressItems(
                                    name = "ABDULLAH",
                                    statusListItem.abdullah,
                                    titlecolor = Color(0xFF705186),
                                    taskcolor = Color(0xFFEADFF3)
                                )
                            }
                        }
                        if (statusListItem.isLoading) {
                            CircularProgressIndicator(
                                color = Color.LightGray
                            )
                        }
                    }
                }
                if (isVerified == false) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        onClick = {
                            coroutineScope.launch {
                                sheetState.show()
                                onSubmitTask()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = backgroundColorPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Submit")
                    }
                }
            }

        }
        if (showAlert.value) {
            Dialog(
                onDismissRequest = {
                    showAlert.value = false
                },
                properties = DialogProperties(
                    dismissOnBackPress = showAlert.value,
                    dismissOnClickOutside = showAlert.value
                )
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    elevation = 8.dp
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Send Alert Notification",
                            modifier = Modifier.padding(15.dp),
                            fontSize = 18.sp,
                            color = backgroundColorPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        alertOptions.forEachIndexed { _, alertOption ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = alertOption.isSelected,
                                    onClick = {
                                        alertOption.isSelected = !alertOption.isSelected
                                        onOptionSelected()
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = backgroundColorPrimary,
                                        unselectedColor = backgroundColorPrimary,
                                        disabledColor = backgroundColorPrimary
                                    )
                                )
                                Text(
                                    text = alertOption.optionText,
                                    color = backgroundColorPrimary
                                )
                            }
                        }
                        Row(Modifier.padding(top = 10.dp)) {

                            Button(
                                onClick = {
                                    showAlert.value = false
                                },
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1F),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = backgroundColorPrimary
                                ),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 0.dp
                                )
                            ) {
                                Text(text = "Cancel")
                            }
                            Button(
                                onClick = {
                                    showAlert.value = false
                                    sendAlertToSlack()
                                },
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1F),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = backgroundColorPrimary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkProgressItemsPreview() {
    WorkProgressItems(
        name = "Balaji",
        task = "Doing Digiclass App...",
        titlecolor = Color.LightGray,
        taskcolor = Color(0xFFEADFF3)
    )
}

@Composable
fun WorkProgressItems(
    name: String,
    task: String?,
    titlecolor: Color,
    taskcolor: Color
) {
    Box {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Card(
                backgroundColor = titlecolor,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .size(25.dp),
                        tint = Color(0XFFEBEBE8)
                    )
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            Surface(
                color = taskcolor,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
            ) {
                Text(
                    text = task.toString(),
                    style = MaterialTheme.typography.caption,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent()
}

@Composable
fun BottomSheetContent() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Image(
                painter = painterResource(id = R.drawable.success),
                contentDescription = "Success",
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Updated \n Daily Status Successfully",
                lineHeight = 25.sp,
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Your Team Status are now viewable in Slack.",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp)
            )
            Divider(
                color = Color.LightGray,
                thickness = .5.dp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProgressSampleScreenPreview() {
//    ProgressSampleScreen(
//        statusList = WorkStatusItemModel(
//            isLoading = true,
//            palaniSlackId = "1",
//            balajiSlackId = "2",
//            saranSlackId = "3",
//            fazilSlackId = "4",
//            maruthuSlackId = "5",
//            abdullahSlackId = "6",
//            date = "19/12/2001",
//            palani = "String?",
//            balaji = "String?",
//            saran = "String?",
//            fazil = "String?",
//            maruthu = "String?",
//            lastUpdate = 0,
//            abdullah = "String?",
//        )
//    )
//}
//
//@Composable
//fun ProgressSampleScreen(statusList: WorkStatusItemModel) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Surface(
//            modifier = Modifier
//                .weight(.3f)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.appbarbackgound),
//                contentDescription = "App bar background",
//                contentScale = ContentScale.Crop,
//                alignment = Alignment.Center,
//                alpha = 0.6f
//            )
//
//            Column(
//                modifier = Modifier
//                    .padding(20.dp)
//            ) {
//                Row() {
//                    Icon(
//                        Icons.Filled.PhoneAndroid,
//                        contentDescription = "Mobile",
//                        modifier = Modifier
//                            .size(40.dp)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//
//
//                    Icon(
//                        Icons.Filled.Verified,
//                        contentDescription = "Verified",
//                        modifier = Modifier
//                            .size(35.dp)
//                            .offset(x = (-20).dp)
//                    )
//                    Icon(
//                        Icons.Filled.Report,
//                        contentDescription = "Alert",
//                        modifier = Modifier
//                            .size(35.dp)
//                    )
//                }
//                Text(
//                    text = "10/09/2022",
//                    fontSize = 16.sp,
//                    modifier = Modifier
//                        .padding(vertical = 20.dp, horizontal = 5.dp)
//                )
//                Text(
//                    text = "Mobile Dev Team",
//                    fontSize = 18.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 5.dp),
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        Card(
//            modifier = Modifier
//                .weight(.7f),
//            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
//            backgroundColor = Color(0XFFEBEBE8)
//        ) {
//            Column {
//                Text(
//                    text = "Today Progress",
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 20.dp),
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Divider(
//                    modifier = Modifier.padding(horizontal = 20.dp)
//                )
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    contentPadding = PaddingValues(10.dp)
//                ) {
//                    item {
//                        WorkProgressItems("PALANI", statusList.palani)
//                        WorkProgressItems("BALAJI", statusList.balaji)
//                        WorkProgressItems("SARAN", statusList.saran)
//                        WorkProgressItems("FAZIL", statusList.fazil)
//                        WorkProgressItems("MARUTHU", statusList.maruthu)
//                        WorkProgressItems("ABDULLAH", statusList.abdullah)
//                    }
//                }
//
//                Button(onClick = { /*TODO*/ }) {
//                    Text(text = "Su")
//                }
//                if (statusList.isLoading) {
//                    CircularProgressIndicator()
//                }
//
//            }
//        }
//    }
//}


