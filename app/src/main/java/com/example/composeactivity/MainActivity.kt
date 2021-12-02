package com.example.composeactivity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeactivity.ui.theme.ComposeActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeActivityTheme {
               MyApp()
            }

        }
    }
}

@Composable fun MyApp(names: List<String> = listOf("android","jiwon","korea")){
    Column {
        for (name in names){
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String){
    val expanded = remember{ mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp
        )) {
        Row(modifier = Modifier.padding(24.dp)){
            Column(modifier = Modifier.weight(1f).padding(extraPadding)) {
                Text(text = "Hello ")
                Text(text = "$name!")
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
       
    }
}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    ComposeActivityTheme{
        MyApp()
    }
}



@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)){
       Image(
           painter = painterResource(id = R.drawable.ic_launcher_foreground),
           contentDescription = "Contact profile picture",
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
       
       )
        
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false)  }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }){
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }

    }


}



data class Message(val author: String, val body: String)

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = " Dark Mode"
)
@Composable
fun PreviewMessageCard(){
    ComposeActivityTheme{
        MessageCard(msg = Message("jiwon", "goood!"))

    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn{
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation(){
    ComposeActivityTheme() {
        Conversation(listOf(Message("jiwon","hi"), Message("gomdol","hello"), Message("sarah","nice to meet you!")))
    }
}



@Composable
fun MessageList(messages: List<String>) {
    Column{
        messages.forEach{ message ->
            Text(text = message)
        }
    }
}