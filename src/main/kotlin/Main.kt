import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.HorizontalSpacer
import components.NavIcon
import components.SharedState
import components.rememberMutableStateOf
import pages.Executor
import pages.Graphics
import pages.Profiler
import pages.Settings

fun main() {
    application {
        Window(
            title = "Kt8 Emulator",
            onCloseRequest = ::exitApplication
        ) {
            val (getDebug, setDebug) = rememberMutableStateOf(false)
            val executorStateMap = remember { mutableStateMapOf<String, Any>(
                "path" to System.getProperty("user.home") + "\\Desktop\\or.gasm",
                "content" to "",
                "consoleText" to "",
                "programLoaded" to false,
                "programLength" to 0u,
                "programCounter" to SharedState.state.programReadStart + 3u
            ) }
            Column(modifier = Modifier.fillMaxSize()) {
                val (destination, setDestination) = rememberMutableStateOf(Destination.Executor)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .padding(bottom = 4.dp)
                        .shadow(1.dp)
                ) {
                    HorizontalSpacer(10.dp)
                    Destination.entries.forEach {
                        NavIcon(
                            it.title,
                            it.icon,
                            it,
                            destination,
                            setDestination
                        )
                        HorizontalSpacer(2.dp)
                    }
                }
                Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                    when (destination) {
                        Destination.Executor -> Executor(
                            getDebug,
                            setDebug,
                            executorStateMap
                        )
                        Destination.Screen -> Graphics()
                        Destination.Profiler -> Profiler()
                        Destination.Settings -> Settings()
                    }
                }
            }
        }
    }
}

enum class Destination(val title: String, val icon: ImageVector) {
    Executor("Run", Icons.Filled.DeveloperMode),
    Screen("Graphics", Icons.Filled.Tv),
    Profiler("Memory Profiler", Icons.Filled.CalendarToday),
    Settings("Settings", Icons.Filled.Settings)
}