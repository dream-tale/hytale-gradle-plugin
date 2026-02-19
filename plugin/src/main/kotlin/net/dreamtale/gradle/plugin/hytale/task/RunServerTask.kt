package net.dreamtale.gradle.plugin.hytale.task

import net.dreamtale.gradle.plugin.hytale.extension.HytaleExtension
import net.dreamtale.gradle.plugin.hytale.util.assetsPath
import net.dreamtale.gradle.plugin.hytale.util.serverPath
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.JavaExec
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import javax.inject.Inject

@CacheableTask
abstract class RunServerTask @Inject constructor(
    toolchains: JavaToolchainService
) : JavaExec() {

    private val ext = project.extensions.getByType(HytaleExtension::class.java)
    private val runDir = project.layout.projectDirectory.dir("run")

    init {
        group = "hytale"
        description = "Run server"

        standardInput = System.`in`
        standardOutput = System.out
        errorOutput = System.err
        isIgnoreExitValue = false

        mainClass.set("com.hypixel.hytale.Main")
        workingDir = runDir.asFile

        javaLauncher.convention(
            toolchains.launcherFor {
                it.languageVersion.set(
                    JavaLanguageVersion.of(ext.serverJavaLanguageVersion.get())
                )
            }
        )

        jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005")

        val buildDir = project.layout.buildDirectory
        classpath += project.files(
            buildDir.dir("classes/java/main"),
            buildDir.dir("classes/kotlin/main"),
            buildDir.dir("resources/main"),
            buildDir.dir("libs").map { libsDir ->
                project.fileTree(libsDir.asFile).matching { it.include("*.jar") }
            }
        )

        args(
            "--assets",
            assetsPath(),
            "--allow-op",
        )
    }
}
