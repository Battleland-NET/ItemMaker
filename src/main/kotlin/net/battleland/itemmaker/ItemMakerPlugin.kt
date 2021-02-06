package net.battleland.itemmaker

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ItemMakerPlugin : JavaPlugin() {
    lateinit var luckPerms: LuckPerms
    override fun onEnable() {
        luckPerms = LuckPermsProvider.get()
        super.onEnable()
        this.getCommand("createitem")?.setExecutor(CreateItemCommand(this))
        Bukkit.getPluginManager().registerEvents(RightClickListener(this), this)
        if (config.saveToString() == "") saveDefaultConfig()
    }
}