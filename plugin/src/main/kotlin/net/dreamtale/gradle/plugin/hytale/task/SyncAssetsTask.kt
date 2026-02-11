package net.dreamtale.gradle.plugin.hytale.task

import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Copy
import javax.inject.Inject


@CacheableTask
abstract class SyncAssetsTask @Inject constructor() : Copy() {

    init {
        group = "hytale"
        description = "Run server"

        from(project.layout.buildDirectory.dir("resources/main"))
        into("src/main/resources")

        exclude("manifest.json")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
