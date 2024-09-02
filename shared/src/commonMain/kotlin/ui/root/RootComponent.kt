package ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ui.home.HomeComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    val model: Value<Model>

    fun handleEvent(event: RootEvent)

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
    }

    data class Model(
        val snackBarMessage: String? = null,
        val isFabVisible: Boolean = false
    )

    @Serializable
    sealed interface Config {

        @Serializable
        data object Home : Config
    }
}

sealed class RootEvent {
    data object OnBack : RootEvent()
    data object OnClearSnackbar : RootEvent()
}