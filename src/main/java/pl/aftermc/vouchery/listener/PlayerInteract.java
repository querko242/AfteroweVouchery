package pl.aftermc.vouchery.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import pl.aftermc.api.util.ChatUtil;
import pl.aftermc.api.util.item.ItemUtil;
import pl.aftermc.lib.panda.std.Option;
import pl.aftermc.lib.panda.std.stream.PandaStream;
import pl.aftermc.vouchery.AfteroweVouchery;
import pl.aftermc.vouchery.configuration.MessageConfiguration;
import pl.aftermc.vouchery.configuration.PluginConfiguration;

public class PlayerInteract implements Listener {

    public PlayerInteract(final AfteroweVouchery plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfteroweVouchery plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if(!itemStack.hasItemMeta()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(!itemMeta.hasDisplayName()) return;
        PluginConfiguration pluginConfiguration = this.plugin.getPluginConfiguration();
        MessageConfiguration messageConfiguration = this.plugin.getMessageConfiguration();

        Option<PluginConfiguration.Voucher> vocher = PandaStream.of(pluginConfiguration.vouchery).find(voucher -> voucher.item.isSimilar(itemStack));

        vocher.peek(voucher -> {
            if(!voucher.giveItems.isEmpty()) {
                for(ItemStack giveItems : voucher.giveItems) {
                    ItemUtil.giveItem(player, giveItems);
                }
            }
            if(!voucher.commands.isEmpty()) {
                for(String command : voucher.commands) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                }
            }
            if(!voucher.giveEffects.isEmpty()) {
                for(PotionEffect effect : voucher.giveEffects) {
                    player.addPotionEffect(effect);
                }
            }
            if(voucher.usageMessage != null)  {
                player.sendMessage(ChatUtil.color(voucher.usageMessage));
            } else {
                player.sendMessage(messageConfiguration.voucherUsage);
            }
        });
    }
}
