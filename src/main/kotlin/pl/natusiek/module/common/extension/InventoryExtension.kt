package pl.natusiek.module.common.extension

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import pl.natusiek.module.common.builder.ItemBuilder

private val item: ClickableItem = ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, 8).name("&8*").build())

fun InventoryContents.set(slotPos: SlotPos): InventoryContents = this.set(slotPos, ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, 2).name("&8*").build()))

fun InventoryContents.fillBorder(): InventoryContents = this.fillBorders(item)

fun InventoryContents.fillRect(from: SlotPos, to: SlotPos): InventoryContents = this.fillRect(from, to, item)

fun InventoryContents.fill(): InventoryContents = this.fill(item)
