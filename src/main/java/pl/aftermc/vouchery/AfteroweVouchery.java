package pl.aftermc.vouchery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.aftermc.api.AfteroweAPI;
import pl.aftermc.api.command.base.CommandManager;
import pl.aftermc.api.configuration.serdes.DecolorTransformer;
import pl.aftermc.api.configuration.serdes.ItemStackTransformer;
import pl.aftermc.api.configuration.serdes.PotionEffectTransformer;
import pl.aftermc.api.util.PluginLogger;
import pl.aftermc.lib.okaeri.configs.ConfigManager;
import pl.aftermc.lib.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import pl.aftermc.vouchery.command.VoucherCommand;
import pl.aftermc.vouchery.configuration.MessageConfiguration;
import pl.aftermc.vouchery.configuration.PluginConfiguration;
import pl.aftermc.vouchery.listener.PlayerInteract;
import pl.aftermc.vouchery.listener.PlayerJoin;

import java.io.File;

public class AfteroweVouchery extends JavaPlugin {

    private AfteroweAPI API;
    private PluginLogger pluginLogger;
    private PluginConfiguration pluginConfiguration;
    private MessageConfiguration messageConfiguration;

    private CommandManager commandManager;

    private boolean newPluginUpdate;

    @Override
    public void onEnable() {
        this.pluginLogger = new PluginLogger(this);
        if(Bukkit.getPluginManager().getPlugin("AfteroweAPI") == null) {
            this.pluginLogger.error("Brak pluginu 'AfteroweAPI'! Wgraj wymagany plugin i uruchom ponownie serwer.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.API = AfteroweAPI.getPlugin();

        this.loadConfiguration();

        this.registerCommands();
        this.registerListeners();
        // testasdasd

        this.newPluginUpdate = this.getAPI().isPluginUpdate(this, "https://raw.githubusercontent.com/querko242/AfteroweVouchery/main/version.txt");
    }

    public void loadConfiguration() {
        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withSerdesPack(registry -> {
                registry.register(new ItemStackTransformer());
                registry.register(new PotionEffectTransformer());
            });
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });
        this.messageConfiguration = ConfigManager.create(MessageConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withSerdesPack(registry -> registry.register(new DecolorTransformer()));
            it.withBindFile(new File(this.getDataFolder(), "messages.yml"));
            it.saveDefaults();
            it.load(true);
        });
    }
    public PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }
    public MessageConfiguration getMessageConfiguration() {
        return messageConfiguration;
    }

    private void registerCommands() {
        this.commandManager = new CommandManager(this);
        this.commandManager.hideTabComplete(true);
        new VoucherCommand(this);
    }

    private void registerListeners() {
        new PlayerInteract(this);
        new PlayerJoin(this);
    }

    public AfteroweAPI getAPI() {
        return API;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public boolean isNewPluginUpdate() {
        return newPluginUpdate;
    }
}
