package ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import component.componentCoroutineScope
import org.koin.core.component.KoinComponent
import ui.home.DefaultHomeComponent
import ui.home.HomeComponent
import ui.root.RootComponent.Child
import ui.root.RootComponent.Child.Home
import ui.root.RootComponent.Config
import ui.root.RootComponent.Model

internal class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val scope = componentCoroutineScope()

    private val navigation = StackNavigation<Config>()

    private val state = MutableValue(Model())
    override val model: Value<Model> = state

    override fun handleEvent(event: RootEvent) {
        when (event) {
            RootEvent.OnBack -> navigation.pop()
            RootEvent.OnClearSnackbar -> state.update { it.copy(snackBarMessage = null) }
        }
    }

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(
        config: Config,
        childComponentContext: ComponentContext
    ): Child = when (config) {
        is Config.Home -> Home(homeComponent(childComponentContext))
    }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext,
            onProductClick = { }
        )
}