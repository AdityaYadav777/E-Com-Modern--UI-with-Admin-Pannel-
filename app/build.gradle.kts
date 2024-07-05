plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.aditya.a_kart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aditya.a_kart"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding=true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Toast
    implementation ("com.github.GrenderG:Toasty:1.5.2")
        //animated Button
    implementation ("com.github.khan-mujeeb:Button-Morphing-Animation:1.0.2")
    //navigation
    implementation ("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")
    //img load
    implementation("io.coil-kt:coil:2.5.0")
    //corsol image
    implementation ("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")
//number picker
    implementation ("com.github.sephiroth74:NumberSlidingPicker:1.0.3")
        //slide button
    implementation ("com.ncorti:slidetoact:0.11.0")
    implementation("com.razorpay:checkout:1.6.38")
}