package net.dreamtale.gradle.plugin.hytale.task

import net.dreamtale.gradle.plugin.hytale.extension.HytaleExtension
import net.dreamtale.gradle.plugin.hytale.util.serverPath
import net.dreamtale.gradle.plugin.hytale.util.serverSourcesPath
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.JavaExec
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import javax.inject.Inject

@CacheableTask
abstract class DecompileServerTask @Inject constructor(
    toolchains: JavaToolchainService
) : JavaExec() {

    init {
        group = "hytale"
        description = "Decompile server"
        mainClass.set("org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler")

        val ext = project.extensions.getByType(HytaleExtension::class.java)
        javaLauncher.convention(
            toolchains.launcherFor { spec ->
                spec.languageVersion.set(JavaLanguageVersion.of(ext.decompileJavaLanguageVersion.get()))
            }
        )

        args(
            serverPath(),
            serverSourcesPath()
        )
    }
}
