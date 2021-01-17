package com.willfp.ecoenchants.mmo.enchants.misc;

import com.willfp.eco.util.events.armorequip.ArmorEquipEvent;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import org.bukkit.entity.Player;

public class Strengthening extends MMOEnchantment {
    private static final String KEY = "ecoenchants_bonus_strength";

    public Strengthening() {
        super("strengthening", EnchantmentType.NORMAL);
    }

    @Override
    public void onArmorEquip(Player player, int level, ArmorEquipEvent event) {
        MMOPlayerData data = MMOPlayerData.get(player);

        data.getStatMap().getInstance(SharedStat.ATTACK_DAMAGE).remove(KEY);

        if(level == 0) {
            MMOLib.plugin.getStats().runUpdates(data.getStatMap());
            return;
        }

        double multiplier = (this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "multiplier") * level) * data.getStatMap().getInstance(SharedStat.ATTACK_DAMAGE).getBase();

        data.getStatMap().getInstance(SharedStat.ATTACK_DAMAGE).addModifier(KEY, new StatModifier(multiplier));

        MMOLib.plugin.getStats().runUpdates(data.getStatMap());
    }
}
