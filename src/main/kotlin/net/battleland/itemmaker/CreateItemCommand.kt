package net.battleland.itemmaker

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class CreateItemCommand(var plugin: JavaPlugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.config.getString("messages.onlyplayers")!!)
            return true
        }
        val handItem = sender.inventory.itemInMainHand
        if (handItem.type == Material.AIR) {
            sender.sendMessage(plugin.config.getString("messages.holditem")!!)
            return true
        }

        if (args.size != 1) {
            return false
        }

        if (args[0].toIntOrNull() != null) {
            if(!sender.hasPermission("createitem.money")) {
                sender.sendMessage("§4You don't have permission")
                return true
            }
            val meta = handItem.itemMeta
            meta?.lore = listOf("§kc" + args[0])
            handItem.itemMeta = meta
        } else {
            if(!sender.hasPermission("createitem.permission")) {
                sender.sendMessage("§4You don't have permission")
                return true
            }
            val meta = handItem.itemMeta
            meta?.lore = listOf("§kp" + args[0])
            handItem.itemMeta = meta
        }

        return true
    }
}