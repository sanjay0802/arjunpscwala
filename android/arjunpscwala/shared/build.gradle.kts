plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
        }

        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.kotlinx.serialization.json)
            api(libs.koin.core)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            api(libs.koin.test)
        }

    }
}

android {
    namespace = "com.arjunpscwala.pscwala"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
