package pl.aftermc.vouchery.configuration;

import pl.aftermc.api.util.ChatUtil;
import pl.aftermc.lib.okaeri.configs.OkaeriConfig;
import pl.aftermc.lib.okaeri.configs.exception.OkaeriException;

import java.lang.reflect.Field;
import java.util.List;

public final class MessageConfiguration extends OkaeriConfig {

    public String voucherUsage = "&aUżyłeś vouchera!";

    public String configReload = "&aKonfiguracja została przeładowana!";

    @Override
    public OkaeriConfig load() throws OkaeriException {
        super.load();
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getType().equals(String.class)) {
                    field.set(this, ChatUtil.color((String) field.get(this)));
                }

                if (field.getType().equals(List.class)) {
                    List<String> list = (List<String>) field.get(this);
                    list.replaceAll(ChatUtil::color);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
}
