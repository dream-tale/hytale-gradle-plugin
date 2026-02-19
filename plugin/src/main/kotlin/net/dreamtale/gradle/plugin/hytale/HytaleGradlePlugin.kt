package net.dreamtale.gradle.plugin.hytale

import net.dreamtale.gradle.plugin.hytale.extension.HytaleExtension
import net.dreamtale.gradle.plugin.hytale.task.DecompileServerTask
import net.dreamtale.gradle.plugin.hytale.task.RunServerTask
import net.dreamtale.gradle.plugin.hytale.task.SyncAssetsTask
import net.dreamtale.gradle.plugin.hytale.util.assetsPath
import net.dreamtale.gradle.plugin.hytale.util.basePath
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

class HytaleGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create("hytale", HytaleExtension::class.java)
        val hytaleCfg = project.configurations.create("hytale") { cfg ->
            cfg.isCanBeConsumed = false
            cfg.isCanBeResolved = true
        }

        project.repositories.flatDir {
            it.dirs("${basePath()}/Server")
        }
        project.dependencies.add(
            "implementation",
            ":HytaleServer@jar"
        )
        project.dependencies.add(
            "compileOnly",
            project.files(assetsPath())
        )

        project.afterEvaluate {
            if (ext.onlyDependencies.get()) {
                return@afterEvaluate
            }

            project.dependencies.add(
                hytaleCfg.name,
                "org.vineflower:vineflower:${ext.vineflowerVersion.get()}"
            )

            project.tasks.register("decompileServer", DecompileServerTask::class.java) { task ->
                task.classpath = hytaleCfg
            }
            project.tasks.register("runServer", RunServerTask::class.java) { task ->
                val javaExt = project.extensions.getByType(JavaPluginExtension::class.java)
                val mainRuntimeProvider = javaExt.sourceSets.named("main").map { it.runtimeClasspath }

                task.classpath = project.files(mainRuntimeProvider)
            }
            project.tasks.register("syncAssets", SyncAssetsTask::class.java)
        }
    }
}
