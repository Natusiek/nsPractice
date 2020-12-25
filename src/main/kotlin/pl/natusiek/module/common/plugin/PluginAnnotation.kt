package pl.natusiek.module.common.plugin

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class PluginAnnotation(val name: String, val version: Int)