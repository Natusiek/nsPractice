package pl.natusiek.module.database

import pl.natusiek.module.database.system.Database
import pl.natusiek.module.database.system.DatabaseEntity

object DatabaseAPI {

    lateinit var database: Database

    inline fun <reified T : DatabaseEntity> loadBySelector(name: String, id: String, selector: String, crossinline listener: (data: T?) -> Unit) =
            database.loadBySelector<T>(name, id, selector, listener)

    inline fun <reified T : DatabaseEntity> loadAll(name: String, crossinline listener: (data: Set<T>) -> Unit) =
            database.loadAll(name, listener)

    inline fun <reified T : DatabaseEntity> load(name: String, id: String, crossinline listener: (data: T?) -> Unit) =
            database.load<T>(name, id, listener)

    fun removeCollection(name: String) = database.removeCollection(name)

}