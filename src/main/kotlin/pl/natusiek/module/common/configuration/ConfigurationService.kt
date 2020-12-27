package pl.natusiek.module.common.configuration

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.lang.IllegalStateException
import java.nio.file.Files
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

object ConfigurationService {

    val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create()

    fun <T : Configuration> load(dataFolder: File, clazz: KClass<T>): T {
        if (clazz.hasAnnotation<ConfigurationAnnotation>()) {
            val annotation = clazz.findAnnotation<ConfigurationAnnotation>()!!

            val file = File(dataFolder, "${annotation.file}.json")
            if (!file.exists()) {
                dataFolder.mkdirs()
                this.save(file, clazz)
            }
            val stringConfig = file.readText(Charsets.UTF_8)

            return this.gson.fromJson(stringConfig, clazz.java)
        }
        throw IllegalStateException("Failed to load configuration: ${clazz.simpleName}")
    }

    fun <T> save(file: File, clazz: KClass<T>) where T : Configuration {
        val json = this.gson.toJson(clazz.java.newInstance())

        Files.write(file.toPath(), json.toByteArray(Charsets.UTF_8))
    }

    fun createJson(file: File, string: String) { Files.write(file.toPath(), string.toByteArray(Charsets.UTF_8)) }

    fun removeFile(file: File) { Files.delete(file.toPath()) }

}