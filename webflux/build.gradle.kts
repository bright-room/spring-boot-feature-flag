plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(project(":core"))

    implementation(libs.spring.boot.starter.webflux)
    annotationProcessor(libs.spring.boot.configuration.processor)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.rest.assured)
}

java {
    toolchain {
        val javaVersion = libs.versions.java.version.get()
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

spotless {
    java {
        googleJavaFormat()
    }
}

tasks {
    compileJava {
        sourceCompatibility = libs.versions.java.version.get()
    }

    test {
        useJUnitPlatform()
    }
}

