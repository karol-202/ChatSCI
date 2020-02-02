plugins {
    kotlin("jvm") version "1.3.61"
    application
}

application.mainClassName = "pl.karol202.chatsci.MainKt"

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
    implementation("io.ktor:ktor-network:1.2.5")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.koin:koin-core:2.0.1")
}
