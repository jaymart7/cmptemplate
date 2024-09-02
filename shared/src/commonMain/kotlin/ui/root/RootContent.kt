package ui.root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import ui.home.HomeContent
import ui.root.RootComponent.Child
import util.getAsyncImageLoader

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    // Coil
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val stack by component.stack.subscribeAsState()
    val model by component.model.subscribeAsState()

    // Show snackbar
    model.snackBarMessage?.let {
        LaunchedEffect(it) {
            snackbarHostState.showSnackbar(it)
            component.handleEvent(RootEvent.OnClearSnackbar)
        }
    }

    MaterialTheme {
        Surface(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            RootContent(
                snackbarHostState = snackbarHostState,
                stack = stack,
            )
        }
    }
}

@Composable
private fun RootContent(
    snackbarHostState: SnackbarHostState,
    stack: ChildStack<*, Child>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
        content = { innerPadding ->
            Children(
                stack = stack,
                modifier = Modifier.padding(innerPadding),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val instance = it.instance) {
                    is Child.Home -> HomeContent(component = instance.component)
                }
            }
        }
    )
}