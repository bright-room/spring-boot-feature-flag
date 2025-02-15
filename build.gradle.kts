plugins {
    alias(libs.plugins.spotless)
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
