package pl.aftermc.vouchery.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.aftermc.api.AfteroweAPI;
import pl.aftermc.api.util.ChatUtil;
import pl.aftermc.vouchery.AfteroweVouchery;

public class PlayerJoin implements Listener {

    public PlayerJoin(final AfteroweVouchery plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfteroweVouchery plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission(AfteroweAPI.getPlugin().getPluginConfiguration().prefixPermission + ".notifyupdate")) {
            if(this.plugin.isNewPluginUpdate()) {
                player.sendMessage(ChatUtil.color("&bAfteroweVouchery &8&l- &cDostępna jest nowa wersja pluginu!"));
                player.sendMessage(ChatUtil.color("&bAfteroweVouchery &8&l- &6Wejdź na serwer discord &b&lAFTERMC.PL &6i pobierz najnowszą wersję!"));
                player.sendMessage(ChatUtil.color("&bLink do Discorda: https://discord.aftermc.pl"));
            }
        }
    }
}
