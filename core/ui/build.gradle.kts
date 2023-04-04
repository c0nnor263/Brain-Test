@file:Suppress("UnstableApiUsage")

plugins {
    PluginType.LIBRARY.get(this)
}
android {
    namespace = "com.conboi.core.ui"
    compileSdk = Versions.Config.compileSdk

    defaultConfig {
        minSdk = Versions.Config.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    compileOptions {
        sourceCompatibility = Versions.Config.sourceCompatibility
        targetCompatibility = Versions.Config.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Versions.Config.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compiler
    }
    packagingOptions {
        resources {
            resources.excludes.add(Versions.excludeFiles)
        }
    }

}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    coreData()
    composeCore()
}
