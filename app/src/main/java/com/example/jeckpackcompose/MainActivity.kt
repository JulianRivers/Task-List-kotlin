package com.example.jeckpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoApp()
        }
    }
}

@Composable
fun ToDoApp() {
    var taskText by remember { mutableStateOf(TextFieldValue("")) }
    var taskList by remember { mutableStateOf(listOf<Task>()) }

    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "To-Do List",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp).height(35.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (taskText.text.isNotEmpty()) {
                        taskList = taskList + Task(taskText.text, false)
                        taskText = TextFieldValue("") // Limpiar el campo de texto
                    }
                }) {
                    Text(text = "Agregar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(taskList.size) { index ->
                    TaskItem(
                        task = taskList[index],
                        onCheckedChange = { checked ->
                            taskList = taskList.mapIndexed { i, t ->
                                if (i == index) t.copy(isChecked = checked) else t
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.mipmap.check),
            contentDescription = "Check Icon",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = task.text,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = if (task.isChecked) TextDecoration.LineThrough else TextDecoration.None
        )

        Checkbox(
            checked = task.isChecked,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}

data class Task(val text: String, val isChecked: Boolean)

@Preview(showBackground = true)
@Composable
fun PreviewToDoApp() {
    ToDoApp()
}
