import android.os.Build
import org.koin.dsl.module

actual fun platformModule() = module {
    single<Platform> {
        object : Platform {
            override val name: String = "Android ${Build.VERSION.SDK_INT}"
            override val remoteHost: String = "shopperserver-heec.onrender.com"
            override val localHost: String = "10.0.2.2"
        }
    }
}