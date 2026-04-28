package me.shygars.game.classes;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.inventory.SetActiveSlot;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ClassItemsDistribution {
    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();

    public static ItemContainer getItemContainer(@Nonnull ComponentType<EntityStore, ? extends InventoryComponent> invComp, @Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
        var inventory = store.getComponent(ref, invComp);
        if (inventory != null) {
            return inventory.getInventory();
        }
        else return null;
    }

    public static void removeClassItems(ItemContainer hotbarContainer, ItemContainer armorContainer, ItemContainer utilityContainer, Player player, PlayerRef playerRef) {
        // Remove any item in the corresponding slots
        for (int i = 0; i <= 1; i++) {
            hotbarContainer.removeItemStackFromSlot((short) i);
        }
        for (int i = 0; i <= 3; i++) {
            armorContainer.removeItemStackFromSlot((short) i);
        }
        utilityContainer.removeItemStackFromSlot((short) 0);
    }

    public static void clearInventory(@NonNullDecl Player player) {
        assert player.getReference() != null;
        var ref = player.getReference();
        var store = ref.getStore();
        var playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        var playerClass = store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        assert playerRef != null;
        assert playerClass != null;
        ItemContainer hotbarContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-1)), ref, store);
        ItemContainer armorContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-3)), ref, store);
        ItemContainer utilityContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-5)), ref, store);
        ItemContainer generalContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-2)), ref, store);

        assert hotbarContainer != null;
        assert armorContainer != null;
        assert utilityContainer != null;
        assert generalContainer != null;

        hotbarContainer.clear();
        armorContainer.clear();
        utilityContainer.clear();
        generalContainer.clear();
    }

    public static void giveClassItems(@NonNullDecl Player player) {
        assert player.getReference() != null;
        var ref = player.getReference();
        var store = ref.getStore();
        var playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        var playerClass = store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        var world = store.getExternalData().getWorld();
        assert playerRef != null;
        assert playerClass != null;

        ItemContainer hotbarContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-1)), ref, store);
        ItemContainer armorContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-3)), ref, store);
        ItemContainer utilityContainer = getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-5)), ref, store);
        removeClassItems(hotbarContainer, armorContainer, utilityContainer, player, playerRef);

        assert hotbarContainer != null;
        assert armorContainer != null;
        assert utilityContainer != null;

        var utilityComp = store.getComponent(ref, InventoryComponent.Utility.getComponentType());
        assert utilityComp != null;

        // Give items (unless null)
        ItemStack mainWeapon = ClassItems.classItems[playerClass.getCurrentClass()][0][playerClass.getMainWeaponUpgrade()][0];
        ItemStack secWeapon = ClassItems.classItems[playerClass.getCurrentClass()][1][playerClass.getSecWeaponUpgrade()][0];
        if (mainWeapon != null) hotbarContainer.addItemStackToSlot((short) 0, mainWeapon);
        if (secWeapon != null) {
            if (secWeapon.equals(new ItemStack("Shadow_Knight_Secondary_Weapon")) || secWeapon.equals(new ItemStack("Shadow_Knight_Secondary_Weapon_Upgrade1")) || secWeapon.equals(new ItemStack("Shadow_Knight_Secondary_Weapon_Upgrade2")) || secWeapon.equals(new ItemStack("Shadow_Knight_Secondary_Weapon_Upgrade3"))) {
                utilityContainer.addItemStackToSlot((short) 0, secWeapon);
                hotbarContainer.addItemStackToSlot((short) 1, new ItemStack("Blocker"));
                utilityComp.setActiveSlot((byte) 0);
                store.getExternalData().getWorld().execute(() -> {
                    playerRef.getPacketHandler().writeNoCache(new SetActiveSlot(-5, 0));
                });
            } else {
                hotbarContainer.addItemStackToSlot((short) 1, secWeapon);
            }
        }
        for (int i = 0; i <= 3; i++) {
            ItemStack armor = ClassItems.classItems[playerClass.getCurrentClass()][2][playerClass.getArmorUpgrade()][i];
            if (armor != null) armorContainer.addItemStackToSlot((short) i, armor);
        }

        // Heal missing health
        world.execute(() ->{
            EntityStatMap entityStatMapComponent = store.getComponent(ref, STAT_MAP_COMPONENT_TYPE);
            assert entityStatMapComponent != null;
            entityStatMapComponent.addStatValue(EntityStatType.getAssetMap().getIndex("Health"), 200);
        });
    }

    public static void giveClassStarterPotions(@NonNullDecl PlayerRef playerRef) {
        Ref<EntityStore> ref = playerRef.getReference();
        var quantity = 0;
        assert ref != null;
        Store<EntityStore> store = ref.getStore();
        var hotbar = InventoryComponent.getComponentTypeById(-1);
        var playerClass = store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        assert hotbar != null;
        var hotbarInventory = store.getComponent(ref, hotbar);
        assert hotbarInventory != null;
        ItemContainer hotbarContainer = hotbarInventory.getInventory();
        assert playerClass != null;
        assert hotbarContainer != null;

        World world = store.getExternalData().getWorld();
        if (world.getPlayerCount() > 4) {
            quantity = 2;
        }
        else {
            quantity = 4 - world.getPlayerCount() + 2;
        }

        //give potions to the corresponding classes with quantity depending on the amount of players
        if (playerClass.getCurrentClass() == 0) {
            hotbarContainer.addItemStackToSlot((short) 2, new ItemStack("Potion_Health", quantity));
            hotbarContainer.addItemStackToSlot((short) 3, new ItemStack("Potion_Swiftness", 1));
        }
        else if (playerClass.getCurrentClass() == 1) {
            hotbarContainer.addItemStackToSlot((short) 2, new ItemStack("Potion_Health", quantity));
            hotbarContainer.addItemStackToSlot((short) 3, new ItemStack("Potion_Stamina", 1));
        }
        else if (playerClass.getCurrentClass() == 2) {
            hotbarContainer.addItemStackToSlot((short) 2, new ItemStack("Potion_Health", quantity));
            hotbarContainer.addItemStackToSlot((short) 3, new ItemStack("Potion_Strength", 1));

        }
        else if (playerClass.getCurrentClass() == 3) {
            hotbarContainer.addItemStackToSlot((short) 2, new ItemStack("Potion_Health", quantity));
            hotbarContainer.addItemStackToSlot((short) 3, new ItemStack("Potion_Mana", 1));
        }
    }
}
