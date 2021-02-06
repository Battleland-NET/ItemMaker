package net.battleland.itemmaker

import com.earth2me.essentials.api.Economy
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.types.PermissionNode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin


class RightClickListener(private var plugin: JavaPlugin) : Listener {

    @Suppress("unused")
    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        val lore = event.item?.itemMeta?.lore ?: return
        if (!lore[0].startsWith("§k", true)) return
        if (lore[0].startsWith("§kc", true)) {
            Economy.add(event.player.uniqueId, lore[0].substring(3).toBigDecimalOrNull() ?: return)
            event.player.sendMessage(
                plugin.config.getString("messages.getmoney")!!
                    .replace("{bonus}", lore[0].substring(3))
                    .replace("{money}", Economy.getMoneyExact(event.player.uniqueId).toString())
            )
        } else if (lore[0].startsWith("§kp", true)) {
            val api = LuckPermsProvider.get()
            val user = api.getPlayerAdapter(Player::class.java).getUser(event.player)
            user.data().add(PermissionNode.builder(lore[0].substring(3)).build())
            api.userManager.saveUser(user)

            event.player.sendMessage(
                plugin.config.getString("messages.getpermission")!!
                    .replace("{permission}", lore[0].substring(3))
            )
        }

        event.item!!.amount -= 1
        event.isCancelled = true
    }
}