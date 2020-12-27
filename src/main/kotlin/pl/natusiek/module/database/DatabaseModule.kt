package pl.natusiek.module.database

import com.mongodb.MongoClientURI
import org.bukkit.Bukkit
import pl.natusiek.module.common.configuration.ConfigurationService
import pl.natusiek.module.common.plugin.module.Module

import pl.natusiek.module.database.system.Database
import pl.natusiek.module.database.configuration.DatabaseConfiguration
import pl.natusiek.practice.impl.PracticePlugin

class DatabaseModule(private val plugin: PracticePlugin): Module {

    lateinit var database: Database
    lateinit var databaseConfiguration: DatabaseConfiguration

    override fun onStart() {
        this.databaseConfiguration = ConfigurationService.load(this.plugin.dataFolder, DatabaseConfiguration::class)
        try {
            this.database = Database(MongoClientURI(this.databaseConfiguration.url), this.databaseConfiguration.database)
        } catch (e: Exception) {
            println("Can't connect to database...")
            Bukkit.shutdown()
        }
        DatabaseAPI.database = this.database
    }

    override fun onStop() {

    }

}