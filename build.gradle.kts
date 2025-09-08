group = "com.selenide"
version = "1.0-SNAPSHOT"

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

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

// Очистка Allure результатов и отчётов
val allureClean by tasks.registering(Delete::class) {
    delete("build/allure-results", "build/allure-report")
}

repositories {
    mavenCentral()
}

val allureVersion = "2.34.1"
val ownerVersion = "1.0.9"
val assertjVersion = "3.22.0"
val lombokVersion = "1.18.30"
val selenideVersion = "7.8.1"

dependencies {
    implementation("org.aspectj:aspectjtools:1.9.22")
    implementation("org.aspectj:aspectjweaver:1.9.22")
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.codeborne:selenide-selenoid:$selenideVersion")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("io.github.bonigarcia:webdrivermanager:6.1.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.22.0")
    implementation("io.qameta.allure:allure-selenide:2.29.1")
    implementation("net.datafaker:datafaker:2.2.2")
    implementation("org.aeonbits.owner:owner:$ownerVersion")


    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Тестовые зависимости
    testImplementation("org.jfree:jfreechart:1.5.4")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("allureReport") // Генерация отчёта после тестов
}

// Отдельные задачи для запуска по тегам
tasks.register<Test>("smokeTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Smoke\")"
    useJUnitPlatform {
        includeTags("Smoke")
    }
}

tasks.register<Test>("regressionTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Regression\")"
    useJUnitPlatform {
        includeTags("Regression")
    }
}

// Конфигурация Allure
allure {
    report {
        version.set(allureVersion)
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
