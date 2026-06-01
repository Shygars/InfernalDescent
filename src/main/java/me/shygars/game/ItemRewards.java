package me.shygars.game;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;

import java.util.Objects;

public class ItemRewards {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public static final ItemStack[] losableItemRewards = {
            new ItemStack("Demon_Blood"),
            new ItemStack("Demon_Bile"),
            new ItemStack("Demon_Bone"),
            new ItemStack("Demon_Flesh"),
            new ItemStack("Copper_Pendant"),
            new ItemStack("Iron_Charm"),
            new ItemStack("Tin_Chain"),
            new ItemStack("Gold_Idol"),
            new ItemStack("Amethyst_Medallion"),
            new ItemStack("Ruby_Medallion"),
            new ItemStack("Diamond_Medallion"),
            new ItemStack("Topaz_Medallion"),
            new ItemStack("Black_Feather"),
            new ItemStack("Broken_Sacrificial_Dagger"),
            new ItemStack("Cursed_Plate"),
            new ItemStack("Strange_Juice"),

            new ItemStack("Potion_Luck"),
            new ItemStack("Challenge_Amulet"),

            new ItemStack("Weapon_Glass_Stiletto"),
            new ItemStack("Soul_Crossbow"),
            new ItemStack("Lost_Crucifix"),
            new ItemStack("Lost_Crucifix_Activated"),
            new ItemStack("Demonic_Grimoire")
    };

    public static ItemStack[] keepingItemRewards = {
            new ItemStack("Potion_Health"),
            new ItemStack("Potion_Health_Big"),
            new ItemStack("Potion_Swiftness"),
            new ItemStack("Potion_Swiftness_Big"),
            new ItemStack("Potion_Strength"),
            new ItemStack("Potion_Strength_Big"),
            new ItemStack("Potion_Resistance"),
            new ItemStack("Potion_Resistance_Big"),
            new ItemStack("Potion_Stamina"),
            new ItemStack("Potion_Mana")
    };

    public static void removeLosableItemRewards(ItemContainer hotbarContainer, ItemContainer generalContainer) {
        for (short i = 2; i < hotbarContainer.getCapacity(); i++) {
            if (hotbarContainer.getItemStack(i) != null) {
                for (ItemStack stack : losableItemRewards) {
                    if (Objects.requireNonNull(hotbarContainer.getItemStack(i)).getItemId().equals(stack.getItemId())) {
                        LOGGER.atInfo().log("Removed ItemStack: " + Objects.requireNonNull(hotbarContainer.getItemStack(i)).getItemId() + " from slot:" + i);
                        hotbarContainer.removeItemStackFromSlot(i);
                        break;
                    }
                }
            }
        }
        for (short i = 0; i < generalContainer.getCapacity(); i++) {
            if (generalContainer.getItemStack(i) != null) {
                for (ItemStack stack : losableItemRewards) {
                    if (Objects.requireNonNull(generalContainer.getItemStack(i)).getItemId().equals(stack.getItemId())) {
                        LOGGER.atInfo().log("Removed ItemStack: " + Objects.requireNonNull(generalContainer.getItemStack(i)).getItemId() + " from slot:" + i);
                        generalContainer.removeItemStackFromSlot(i);
                        break;
                    }
                }
            }
        }
    }
}
