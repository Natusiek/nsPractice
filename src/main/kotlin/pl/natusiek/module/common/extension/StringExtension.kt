package pl.natusiek.module.common.extension

import org.bukkit.ChatColor

fun String.colored(): String = ChatColor.translateAlternateColorCodes('&', this)

fun Collection<String>.colored(): List<String> = this.map(String::colored)
