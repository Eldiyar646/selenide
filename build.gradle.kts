tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
    id("io.qameta.allure") version "2.9.4"
    id("org.gradle.test-retry") version "1.6.2"
}

val allureClean by tasks.registering(Delete::class) {
    delete("allure-results", "allure-report")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

allure {
    report {
        version.set("2.25.0")
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
    google()
}

val allureVersion = "2.25.0"
val ownerVersion = "1.0.9"
val assertjVersion = "3.22.0"
val lombokVersion = "1.18.30"
val selenideVersion = "7.8.1"
val seleniumVersion = "4.32.0"  // версия, где точно есть devtools-v140

dependencies {
    testImplementation("org.slf4j:slf4j-simple:2.0.12")
    implementation("org.slf4j:slf4j-api:2.0.17")

    // Selenium
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
    testImplementation("org.seleniumhq.selenium:selenium-devtools-v140:$seleniumVersion")

    implementation("org.json:json:20230227")
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("allure.results.directory", "$buildDir/allure-results")
}

// Задача для Smoke тестов
tasks.register<Test>("SmokeTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(Tags.SMOKE)"
    useJUnitPlatform {
        includeTags("Smoke")
    }
    reports {
        junitXml.required.set(true)
        junitXml.outputLocation.set(layout.buildDirectory.dir("test-results/SmokeTest"))
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/tests/SmokeTest"))
    }
}

// Задача для Regression тестов
tasks.register<Test>("RegressionTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(Tags.REGRESSION)"
    useJUnitPlatform {
        includeTags("Regression")
    }
    reports {
        junitXml.required.set(true)
        junitXml.outputLocation.set(layout.buildDirectory.dir("test-results/RegressionTest"))
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/tests/RegressionTest"))
    }
}