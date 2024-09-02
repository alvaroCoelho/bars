import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.findKaptConfiguration

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.devtools.ksp)
}

    android {
    namespace = "com.bars"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bars"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
           //buildConfigField("String", "BASE_URL_PATH", "\"https://api.foursquare.com/v3/\"")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
           // buildConfigField("String", "BASE_URL_PATH", "\"https://api.foursquare.com/v3/\"")
        }
    }

        flavorDimensions += "version"
        productFlavors {
            create("cocktail") {
                dimension = "version"
                applicationIdSuffix = ".cocktail"
                versionNameSuffix = "-cocktail"
                buildConfigField("String", "BASE_VENUE_URL_PATH", "\"https://api.foursquare.com/v2/venues/\"")
                buildConfigField("String", "BASE_PLACE_URL_PATH", "\"https://api.foursquare.com/v3/\"")
                buildConfigField("String", "AUTHORIZATION", "\"fsq3X2nZUQFeyuJP4NSHpNxuvLALtY/uDG3OkmT1ZNejgjw=\"")
                buildConfigField("String", "QUERY_PLACES", "\"cocktail\"")
                buildConfigField("String", "RADIUS_PLACES", "\"3000\"")

            }
            create("coffee") {
                dimension = "version"
                applicationIdSuffix = ".coffee"
                versionNameSuffix = "-coffee"
                buildConfigField("String", "BASE_VENUE_URL_PATH", "\"https://api.foursquare.com/v2/venues/\"")
                buildConfigField("String", "BASE_PLACE_URL_PATH", "\"https://api.foursquare.com/v3/\"")
                buildConfigField("String", "AUTHORIZATION", "\"fsq3X2nZUQFeyuJP4NSHpNxuvLALtY/uDG3OkmT1ZNejgjw=\"")
                buildConfigField("String", "QUERY_PLACES", "\"coffee\"")
                buildConfigField("String", "RADIUS_PLACES", "\"1000\"")

            }

        }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.14"
        }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
                excludes += "/META-INF/gradle/incremental.annotation.processors"

            }
        }
        buildFeatures {
            buildConfig = true
        }
}


dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.location)
    implementation(libs.accompanist.permissions)
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.concurrent.futures.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}
