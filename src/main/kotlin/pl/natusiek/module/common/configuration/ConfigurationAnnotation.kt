package pl.natusiek.module.common.configuration

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ConfigurationAnnotation(val file: String)