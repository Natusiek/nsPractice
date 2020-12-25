package pl.natusiek.module.common.plugin

import co.aikar.commands.BaseCommand
import co.aikar.commands.ExceptionHandler
import co.aikar.commands.PaperCommandManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


abstract class Plugin : JavaPlugin() {

    lateinit var commandManager: PaperCommandManager

    fun initCommand() {
        this.commandManager = PaperCommandManager(this)
        this.commandManager.defaultExceptionHandler = ExceptionHandler { command, _, sender, _, _ ->
            sender.sendMessage("&4! &fZglos sie do nas poniewaz jest problem z komenda &8(&c${command.name}&8)")
            true
        }
        Locale("pl").apply {
            this@Plugin.commandManager.addSupportedLanguage(this)
            this@Plugin.commandManager.locales.defaultLocale = this
        }
    }

    fun <T> registerCommands(vararg commands: T) where T : BaseCommand {
        this.initCommand()
        val manager = this.commandManager
        commands.forEach { manager.registerCommand(it) }
    }

    fun <T> registerListeners(vararg listener: T) where T : Listener {
        val manager = Bukkit.getPluginManager()
        listener.forEach { manager.registerEvents(it, this) }
    }

}