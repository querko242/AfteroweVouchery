package pl.aftermc.vouchery.configuration;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.aftermc.api.util.TimeUtil;
import pl.aftermc.api.util.item.ItemBuilder;
import pl.aftermc.lib.okaeri.configs.OkaeriConfig;
import pl.aftermc.lib.okaeri.configs.annotation.Comment;
import pl.aftermc.lib.okaeri.configs.annotation.CustomKey;
import pl.aftermc.lib.okaeri.configs.annotation.Exclude;
import pl.aftermc.lib.okaeri.configs.annotation.Header;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Header("Konfiguracja pluginu AfteroweVouchery")
@Header("Serwer Discord na którym pomagamy z konfiguracją serwera: https://discord.aftermc.pl")
@Header("Dołącz na serwer już teraz! :)")
@Header(" ")
@Header("Wymagany plugin: AfteroweAPI (w wersji minimum 2.0)")
@Header(" ")
@Header("Uprawnienia:")
@Header("/voucher (aftermc.voucher) - Lista voucherów")
@Header("/voucher reload (aftermc.voucher.reload) - Przeładowanie konfiguracji")
@Header(" ")
public final class PluginConfiguration extends OkaeriConfig {

    @Comment("item - Przedmiot jaki ma być voucherem")
    @Comment("usageMessage - Wiadomość po użyciu vouchera")
    @Comment("Zmienne:")
    @Comment("giveItems - Przedmioty jakie ma dawać")
    @Comment("giveEffect - Jakie efekty ma nadawać graczowi nazwa efektu:(Czas trwania w tickach 20 ticków = 1 sekunda):poziom(od 0)")
    @Comment("(Nazwy efektów muszą pasować do nazw podanych tu: https://spigotdocs.okaeri.cloud/select/org/bukkit/potion/PotionEffectType.html)")
    @Comment("commands - Komendy jakie ma wykonywać voucher (przez konsolę)")
    @Comment("Komendy należy podać bez /")
    @Comment("Zmienne:")
    @Comment("%player% - Nick gracza")

    public List<Voucher> vouchery = Collections.singletonList(
            new Voucher(1, new ItemBuilder(Material.PAPER, 1).setNameRGB("&8%> &e&lDARMOWY VIP &8&l<%").getItem(),
                    "&a&lDostałeś darmowego VIPA",
                    Arrays.asList(new ItemBuilder(Material.DIAMOND, 16).getItem(), new ItemBuilder(Material.EMERALD, 64).getItem()),
                    Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, 6000, 3)),
                    Collections.singletonList("lp user %player% parent set vip"))
    );

    @Comment("Formatowanie przedmiotów")
    @Comment("Tylko wartości ujęte w <> są wymagane - reszta, ujeta w [], jest opcjonalna")
    @Comment("Wzór: <ilosc> <przedmiot>:[metadata] [name:lore:enchants:eggtype:skullowner:armorcolor:flags]")
    @Comment("Przykład: \"1 stone name:&bAfteroweArtefakty lore:&eJestem_najlepszym#&6pluginem!\"")
    @Comment(" ")
    @Comment("Zamiast spacji wstawiaj podkreślnik: _")
    @Comment("Aby zrobić nową linię lore wstaw hash: #")
    @Comment("Aby w lore użyć znaku # wstaw {HASH}")
    @Comment(" ")
    @Comment("eggtype to typ jajka do spawnu moba, używane tylko gdy typem przedmiotu jest MONSTER_EGG")
    @Comment("skullowner to nick gracza, którego głowa jest tworzona, używane tylko gdy typem przedmiotu jest SKULL_ITEM")
    @Comment("armorcolor to kolor, w którym będzie przedmiot, używane tylko gdy przedmiot jest częścią zbroi skórzanej")
    @Comment("flags to flagi, które maja byc nałożone na przedmiot. Dostepne flagi: HIDE_ENCHANTS, HIDE_ATTRIBUTES, HIDE_UNBREAKABLE, HIDE_DESTROYS, HIDE_PLACED_ON, HIDE_POTION_EFFECTS")
    @Comment("Kolor musi byc podany w postaci: \"R_G_B\"")
    @Comment(" ")
    @Comment("UWAGA: Nazwy przedmiotów musza pasować do nazw podanych tutaj: https://spigotdocs.okaeri.cloud/select/org/bukkit/Material.html")
    @Comment("UWAGA: Typ jajka musi pasować do typów entity podanych tutaj: https://spigotdocs.okaeri.cloud/select/org/bukkit/entity/EntityType.html")
    @Exclude
    public String exclude;

    public static class Voucher extends OkaeriConfig {

        public int id;
        public ItemStack item;
        public String usageMessage;

        public List<ItemStack> giveItems;
        public List<PotionEffect> giveEffects;
        public List<String> commands;

        public Voucher(final int id,
                        final ItemStack item,
                         final String usageMessage,
                         final List<ItemStack> giveItems,
                         final List<PotionEffect> giveEffects,
                         final List<String> commands) {
            this.id = id;
            this.item = item;
            this.usageMessage = usageMessage;
            this.giveItems = giveItems;
            this.giveEffects = giveEffects;
            this.commands = commands;
        }
    }
}
