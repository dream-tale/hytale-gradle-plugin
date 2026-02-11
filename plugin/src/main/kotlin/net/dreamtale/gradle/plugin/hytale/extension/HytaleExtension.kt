package net.dreamtale.gradle.plugin.hytale.extension

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class HytaleExtension @Inject constructor(objects: ObjectFactory) {
    val vineflowerVersion: Property<String> = objects.property(String::class.java)
        .convention("1.11.2")
    val decompileJavaLanguageVersion: Property<Int> = objects.property(Int::class.java)
        .convention(25)
    val serverJavaLanguageVersion: Property<Int> = objects.property(Int::class.java)
        .convention(25)
    val onlyDependencies: Property<Boolean> = objects.property(Boolean::class.java)
        .convention(false)
}
