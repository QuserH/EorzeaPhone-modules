pluginManagement {
    repositories { google(); mavenCentral(); gradlePluginPortal() }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories { google(); mavenCentral() }
}
rootProject.name = "EorzeaPhoneModules"
include(":api")
file("modules").listFiles()?.filter { it.isDirectory && File(it, "module").isDirectory }?.forEach { dir ->
    include(":modules:${dir.name}:module")
    project(":modules:${dir.name}:module").projectDir = File(dir, "module")
}
