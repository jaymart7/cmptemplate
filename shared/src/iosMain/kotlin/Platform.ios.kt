import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<Platform> {
            object : Platform {
                override val name: String = "iOS"
                override val localHost: String = "localhost"
                override val remoteHost: String = "shopperserver-heec.onrender.com"
            }
        }
    }
}