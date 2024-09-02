package di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import platformModule
import repository.ProductRepository
import repository.ProductRepositoryImpl
import ui.root.DefaultRootComponent
import ui.root.RootComponent

fun appModule() = listOf(commonModule, platformModule(), networkModule)

val commonModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(
            httpClient = get()
        )
    }
}

fun createRootComponent(
    componentContext: ComponentContext
): RootComponent {
    return DefaultRootComponent(componentContext)
}