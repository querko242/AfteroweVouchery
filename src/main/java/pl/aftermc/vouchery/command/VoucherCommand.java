package pl.aftermc.vouchery.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.aftermc.api.command.annotations.*;
import pl.aftermc.api.command.base.CommandBase;
import pl.aftermc.api.gui.GuiWindow;
import pl.aftermc.api.util.item.ItemUtil;
import pl.aftermc.vouchery.AfteroweVouchery;
import pl.aftermc.vouchery.configuration.PluginConfiguration;

@Command("voucher")
@Alias("vouchery")
public class VoucherCommand extends CommandBase {

    public VoucherCommand(final AfteroweVouchery plugin) {
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweVouchery plugin;

    @Default
    @Permission("voucher")
    public void defaultCommand(final Player player) {
        GuiWindow guiWindow = new GuiWindow("&3&lLista voucherów", 4);
        for(PluginConfiguration.Voucher voucher : this.plugin.getPluginConfiguration().vouchery) {
            guiWindow.setToNextFree(voucher.item.clone(), event -> {
                event.setCancelled(true);
                ItemUtil.giveItem(player, voucher.item);
            });
        }
        guiWindow.open(player);
    }

    @SubCommand("reload")
    @Permission("voucher.reload")
    public void reloadCommand(final CommandSender sender) {
        this.plugin.loadConfiguration();
        sender.sendMessage(this.plugin.getMessageConfiguration().configReload);
    }
}
