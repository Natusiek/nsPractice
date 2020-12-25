package pl.natusiek.module.common.plugin

import org.bukkit.Bukkit
import java.lang.IllegalStateException
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

object PluginService {

    fun <T : Plugin> load(clazz: KClass<T>): Plugin {
        if (!clazz.hasAnnotation<PluginAnnotation>()) {
            Bukkit.shutdown()
            throw IllegalStateException("Failed to load class(${clazz.simpleName}) because it has not annotation")
        }
        if (clazz !is Plugin)
                clazz as Plugin
        clazz.initCommand()

        val annotation = clazz.findAnnotation<PluginAnnotation>()!!
        println("Loaded plugin ${annotation.name} (${annotation.version})")
        return clazz
    }

}