package com.example.firstkotlinapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstkotlinapp.ui.theme.FirstkotlinappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstkotlinappTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen(){

    //States
    val (tasked,setTasked) =remember {
        mutableStateOf("")
    }
    val allTasks = remember{
        mutableStateListOf<String>()
    }

   //column
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        // TodoHeading Composable
        HeadingTodo()

        // A TextField and add icon in AddingTaskComposable
        AddingTaskComposable(tasked = tasked, setTasked = setTasked,allTasks = allTasks)

        //TaskList in this composable
        TaskListComposable(allTasks = allTasks)

    }
}

@Composable
fun HeadingTodo(modifier: Modifier = Modifier){
 Box(contentAlignment = Alignment.Center,modifier = modifier
     .fillMaxWidth()
     .fillMaxHeight(fraction = 0.1f)
     ) {
     Text(text = "TODO ", color = Color.Blue, fontSize = 30.sp)
 }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddingTaskComposable(tasked:String,setTasked:(String)->Unit,modifier: Modifier = Modifier,allTasks: SnapshotStateList<String>) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun addTaskAfterDone(){
        allTasks.add(tasked)
        setTasked("")
        keyboardController?.hide()
        focusManager.clearFocus()
    }
    Box(modifier = modifier
        .padding(horizontal = 20.dp, vertical = 20.dp)
        .fillMaxWidth()
        .fillMaxHeight(fraction = 0.1f), contentAlignment = Alignment.Center
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(value = tasked, onValueChange ={text -> setTasked(text)},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        addTaskAfterDone()
                    }
                ))
            IconButton(onClick = {addTaskAfterDone()}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.Blue)
            }
        }
    }
}

@Composable
fun TaskListComposable(allTasks:List<String>,modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp, vertical = 10.dp)){
        items(count = allTasks.size){
                item ->  Text(text = "${item+1}.\t ${allTasks[item]} ", fontSize = 18.sp, modifier= modifier.
        padding(horizontal = 15.dp, vertical = 0.dp))
            Divider( modifier= modifier.
            padding(horizontal = 15.dp, vertical = 0.dp))
            Spacer(modifier = modifier.
            padding(horizontal = 0.dp, vertical = 15.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    FirstkotlinappTheme {
        MainScreen()
    }
}