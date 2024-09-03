import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        browser()
    }

    // TODO: Remove once `compose-plugin` has been updated.
    configurations.configureEach {
        exclude("androidx.window.core", "window-core")
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries {
            framework {
                baseName = "shared"
                isStatic = true

                export(libs.decompose.decompose)
                export(libs.essenty.lifecycle)
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.material3)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.bundles.ktor.common)
                implementation(libs.kotlinx.coroutines.core)

                api(libs.koin.core)
                implementation(libs.koin.compose.multiplatform)
                implementation(libs.kotlin.serialization)
                implementation(libs.kotlinx.datetime)

                api(libs.decompose.decompose)
                api(libs.essenty.lifecycle.coroutines)
                api(libs.essenty.lifecycle)
                implementation(libs.decompose.extensions.compose)

                implementation(libs.coil.compose)
                implementation(libs.coil.compose.core)
                implementation(libs.coil.network.ktor)
                implementation(libs.coil.mp)

                // TODO: Remove once `compose-plugin` has been updated.
                api("org.jetbrains.compose.material3.adaptive:adaptive:1.0.0-alpha01")
                api("org.jetbrains.compose.material3.adaptive:adaptive-layout:1.0.0-alpha01")
                api("org.jetbrains.compose.material3.adaptive:adaptive-navigation:1.0.0-alpha01")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.slf4j)

                // TODO: Remove once `compose-plugin` has been updated.
                implementation("androidx.window:window-core:1.3.0")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.ktor.client.cio)
                implementation(libs.slf4j)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(libs.koin.core.js)
            }
        }

        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
            dependsOn(commonMain)
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "ph.mart.cmptemplate.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}