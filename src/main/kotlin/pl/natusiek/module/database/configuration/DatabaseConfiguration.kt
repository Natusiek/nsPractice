package pl.natusiek.module.database.configuration

import pl.natusiek.module.common.configuration.Configuration
import pl.natusiek.module.common.configuration.ConfigurationAnnotation

@ConfigurationAnnotation("database")
class DatabaseConfiguration : Configuration {

    val url = "mongodb://localhost:27017/practice"
    val database = "practice"

}