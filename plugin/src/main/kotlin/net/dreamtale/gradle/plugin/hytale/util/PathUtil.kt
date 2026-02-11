package net.dreamtale.gradle.plugin.hytale.util


fun basePath(): String {
    val os = System.getProperty("os.name")
    if (!os.contains("Windows")) {
        throw RuntimeException("OS $os is not supported")
    }

    val base = System.getenv("APPDATA")

    return "$base/Hytale/install/release/package/game/latest"
}

fun serverPath(): String {
    return "${basePath()}/Server/HytaleServer.jar"
}

fun serverSourcesPath(): String {
    return "${basePath()}/Server/HytaleServer-sources.jar"
}

fun assetsPath(): String {
    return "${basePath()}/Assets.zip"
}
