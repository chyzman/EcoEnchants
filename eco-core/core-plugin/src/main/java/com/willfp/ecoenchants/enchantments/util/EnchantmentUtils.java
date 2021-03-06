package com.willfp.ecoenchants.enchantments.util;


import com.willfp.eco.util.NumberUtils;
import com.willfp.eco.util.StringUtils;
import com.willfp.eco.util.integrations.placeholder.PlaceholderEntry;
import com.willfp.eco.util.integrations.placeholder.PlaceholderManager;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.itemtypes.Spell;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class EnchantmentUtils {
    /**
     * If the enchantment has successfully passed its specified chance.
     *
     * @param enchantment The enchantment to query.
     * @param level       The level to base the chance off of.
     * @return If the enchantment should then be executed.
     */
    public static boolean passedChance(@NotNull final EcoEnchant enchantment,
                                       final int level) {
        return NumberUtils.randFloat(0, 1) < ((enchantment.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "chance-per-level") * level) / 100);
    }

    /**
     * Register the placeholders for an enchantment.
     *
     * @param enchantment The enchantment to register placeholders for.
     */
    public static void registerPlaceholders(@NotNull final EcoEnchant enchantment) {
        PlaceholderManager.registerPlaceholder(
                new PlaceholderEntry(
                        enchantment.getPermissionName() + "_" + "enabled",
                        player -> String.valueOf(enchantment.isEnabled())
                )
        );

        enchantment.getConfig().getConfig().getKeys(true).forEach(string -> {
            String key = string.replace("\\.", "_").replace("-", "_");
            Object object = enchantment.getConfig().getConfig().get(string);

            PlaceholderManager.registerPlaceholder(
                    new PlaceholderEntry(
                            enchantment.getPermissionName() + "_" + key,
                            player -> StringUtils.internalToString(object)
                    )
            );
        });

        if (enchantment.getConfig().getConfig().get(EcoEnchants.CONFIG_LOCATION + "chance-per-level") != null) {
            PlaceholderManager.registerPlaceholder(
                    new PlaceholderEntry(
                            enchantment.getPermissionName() + "_" + "chance_per_level",
                            player -> NumberUtils.format(enchantment.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "chance-per-level"))
                    )
            );
        }

        if (enchantment.getConfig().getConfig().get(EcoEnchants.CONFIG_LOCATION + "multiplier") != null) {
            PlaceholderManager.registerPlaceholder(
                    new PlaceholderEntry(
                            enchantment.getPermissionName() + "_" + "multiplier",
                            player -> NumberUtils.format(enchantment.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "multiplier"))
                    )
            );
            PlaceholderManager.registerPlaceholder(
                    new PlaceholderEntry(
                            enchantment.getPermissionName() + "_" + "multiplier_percentage",
                            player -> NumberUtils.format(enchantment.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "multiplier") * 100)
                    )
            );
        }

        if (enchantment instanceof Spell) {
            PlaceholderManager.registerPlaceholder(
                    new PlaceholderEntry(
                            enchantment.getPermissionName() + "_" + "cooldown",
                            player -> NumberUtils.format(Spell.getCooldown((Spell) enchantment, player)),
                            true
                    )
            );
            PlaceholderManager.registerPlaceholder(
                    new PlaceholderEntry(
                            enchantment.getPermissionName() + "_" + "cooldown_total",
                            player -> NumberUtils.format(((Spell) enchantment).getCooldownTime())
                    )
            );
        }
    }
}
