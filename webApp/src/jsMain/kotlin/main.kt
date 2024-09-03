import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import di.appModule
import di.createRootComponent
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import ui.root.RootContent

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        startKoin {
            modules(appModule())
        }

        val lifecycle = LifecycleRegistry()
        val root = createRootComponent(DefaultComponentContext(lifecycle = lifecycle))

        lifecycle.resume()

        CanvasBasedWindow("Cmptemplate") {
            RootContent(root)
        }
    }
}