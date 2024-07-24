pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://artifact.bytedance.com/repository/pangle") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://artifact.bytedance.com/repository/pangle") }


    }
}

rootProject.name = "PangleAds"
include(":app")
 