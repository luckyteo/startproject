import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


plugins {
    id(Dependencies.Plugins.application)
    id(Dependencies.Plugins.kotlinAndroid)
}

android {
    namespace = "com.example.startproject"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions.add("default")
    productFlavors {
        create("product") {
        }
        create("staging") {
            applicationIdSuffix = ".stg"
        }
        create("develop") {
            applicationIdSuffix = ".dev"
        }
    }
    // Config your output file name
    applicationVariants.all {
        val variant = this
        val outputFileName = "StartProject_" +
                variant.flavorName + "-" +
                variant.buildType.name +
                "-${variant.versionName}" +
                "-${variant.versionCode}" +
                "-${SimpleDateFormat("yyyyMMdd-HHmm", Locale.US).format(Date())}.apk"
        outputs.all {
            val output = this as? BaseVariantOutputImpl
            output?.outputFileName = outputFileName
        }
    }

    compileOptions {
        sourceCompatibility = AppConfig.javaCompatibility
        targetCompatibility = AppConfig.javaCompatibility
    }
    kotlinOptions {
        jvmTarget = AppConfig.javaJvmTarget
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Android.Version.compose
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Android
    implementation("androidx.compose.ui:ui-graphics")
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.activityCompose)
    implementation(Dependencies.Android.navigationCompose)

    implementation(Dependencies.Android.composeUi)
    implementation(Dependencies.Android.composeMaterial3)
    implementation(Dependencies.Android.composeMaterialIconsExtended)
    implementation(platform(Dependencies.Android.composeBom))
    implementation(Dependencies.Android.composeUiTooling)
    implementation(Dependencies.Android.composeUiToolingPreview)

    implementation(Dependencies.Android.lifecycleRuntimeKtx)
    implementation(Dependencies.Android.lifecycleViewModelKtx)
    implementation(Dependencies.Android.lifecycleExtensions)
    implementation(Dependencies.Android.lifecycleViewModelCompose)

    // Third Party

    implementation(Dependencies.ThirdParty.coilCompose)

    implementation(Dependencies.ThirdParty.kotlinxCoroutinesCore)
    implementation(Dependencies.ThirdParty.kotlinxCoroutinesAndroid)

    implementation(Dependencies.ThirdParty.retrofit)
    implementation(Dependencies.ThirdParty.retrofitConverterGson)
    implementation(Dependencies.ThirdParty.retrofitConverterScalars)

    // okHttp
    implementation(Dependencies.ThirdParty.oKHttpLogInterceptor)

    implementation(Dependencies.ThirdParty.koinAndroid)
    implementation(Dependencies.ThirdParty.koinAndroidxCompose)


    // Android Test
    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.junitExt)
    androidTestImplementation(Dependencies.AndroidTest.espressoCore)

    androidTestImplementation(Dependencies.AndroidTest.composeJunit)
    debugImplementation(Dependencies.AndroidTest.composeUiTooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}