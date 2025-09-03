
group = "com.selenide"
version = "1.0-SNAPSHOT"

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

val allureClean by tasks.registering(Delete::class) {
    delete("allure-results", "allure-report")
}

plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
    id("io.qameta.allure") version "2.9.4"
    id("org.gradle.test-retry") version "1.6.2"
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
allure {
    report {
        version.set("2.34.1")
    }
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.25.0")
            }
        }
    }
}
repositories {
    mavenCentral()
}
val allureVersion = "2.29.1"
val ownerVersion = "1.0.9"
val jacksonVersion = "2.17.0"
val assertjVersion = "3.22.0"
val lombokVersion = "1.18.30"
val slf4jVersion = "2.0.16"
val selenideVersion = "7.8.1"
dependencies {
    testImplementation("org.json:json:20231013")
    implementation("org.aspectj:aspectjtools:1.9.22")
    implementation("org.aspectj:aspectjweaver:1.9.22")
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.codeborne:selenide-selenoid:$selenideVersion")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("io.github.bonigarcia:webdrivermanager:6.1.0")
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion")
    implementation("org.assertj:assertj-core:$assertjVersion")
    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("io.qameta.allure:allure-junit5:$allureVersion")
    implementation("net.datafaker:datafaker:2.2.2")
    implementation("org.aeonbits.owner:owner:$ownerVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}
tasks.test {
    useJUnitPlatform()
}
// Отдельная задача для запуска только тестов с @Tag("Smoke")
tasks.register<Test>("smokeTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Smoke\")"
    useJUnitPlatform {
        includeTags("Smoke")
    }
}

// Отдельная задача для запуска только тестов с @Tag("Smoke")
tasks.register<Test>("regressionTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Regression\")"
    useJUnitPlatform {
        includeTags("Regression")
    }
}
