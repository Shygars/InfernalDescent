package me.shygars.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryChangeEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.ClassItems;
import me.shygars.game.classes.ClassItemsDistribution;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Arrays;

public class UpgradeItemsTradeSystem extends EntityEventSystem<EntityStore, InventoryChangeEvent> {
    public UpgradeItemsTradeSystem() {
        super(InventoryChangeEvent.class);
    }

    public boolean contains2dItemStack(ItemStack[][] array, ItemStack itemStack) {
        var numRows = array.length;
        var numCols = 0;
        for (int row = 0; row < numRows; row++) {
            numCols = array[row].length;
            for (int col = 0; col < numCols; col++) {
                if (array[row][col] == itemStack) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains3dItemStack(ItemStack[][][] array, ItemStack itemStack) {
        var numRows = array.length;
        var numCols = 0;
        var numPlas = 0;
        for (int row = 0; row < numRows; row++) {
            numCols = array[row].length;
            for (int col = 0; col < numCols; col++) {
                numPlas = array[row][col].length;
                for (int pla = 0; pla < numPlas; pla++) {
                    if (array[row][col][pla] == itemStack) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void handle(
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
            @NonNullDecl InventoryChangeEvent event) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        if (player != null) {
            if (playerClass != null) {
                int playerCurrentClass = playerClass.getCurrentClass();
                if (playerCurrentClass != 4) {
                    if (event.getItemContainer().getCapacity() == 9 || event.getItemContainer().getCapacity() == 36) {
                        for (int i = 0; i < event.getItemContainer().getCapacity(); i++) {
                            ItemStack selectedSlotItemStack = event.getItemContainer().getItemStack((short) i);
                            if (selectedSlotItemStack != null) {
                                if (Arrays.asList(ClassItems.classItemUpgrades[playerCurrentClass][0]).contains(selectedSlotItemStack)) {
                                    if (playerClass.getMainWeaponUpgrade() < ClassItems.getUpgradeTierIndex(selectedSlotItemStack)) {
                                        playerClass.setMainWeaponUpgrade(ClassItems.getUpgradeTierIndex(selectedSlotItemStack));
                                        ClassItemsDistribution.giveClassItems(player);
                                        player.sendMessage(Message.raw("Your main weapon was upgraded to Tier " + (ClassItems.getUpgradeTierIndex(selectedSlotItemStack) + 1)));
                                    }
                                    else player.sendMessage(Message.raw("You can't downgrade your main weapon! (what a waste of souls...)"));
                                    event.getItemContainer().removeItemStackFromSlot((short) i);
                                }
                                else if (Arrays.asList(ClassItems.classItemUpgrades[playerCurrentClass][1]).contains(selectedSlotItemStack)) {
                                    if (playerClass.getSecWeaponUpgrade() < ClassItems.getUpgradeTierIndex(selectedSlotItemStack)) {
                                        playerClass.setSecWeaponUpgrade(ClassItems.getUpgradeTierIndex(selectedSlotItemStack));
                                        ClassItemsDistribution.giveClassItems(player);
                                        player.sendMessage(Message.raw("Your secondary weapon was upgraded to Tier " + (ClassItems.getUpgradeTierIndex(selectedSlotItemStack) + 1)));
                                    }
                                    else player.sendMessage(Message.raw("You can't downgrade your secondary weapon! (dumbass)"));
                                    event.getItemContainer().removeItemStackFromSlot((short) i);
                                }
                                else if (Arrays.asList(ClassItems.classItemUpgrades[playerCurrentClass][2]).contains(selectedSlotItemStack)) {
                                    if (playerClass.getArmorUpgrade() < ClassItems.getUpgradeTierIndex(selectedSlotItemStack)) {
                                        playerClass.setArmorUpgrade(ClassItems.getUpgradeTierIndex(selectedSlotItemStack));
                                        ClassItemsDistribution.giveClassItems(player);
                                        player.sendMessage(Message.raw("Your armor was upgraded to Tier " + (ClassItems.getUpgradeTierIndex(selectedSlotItemStack) + 1)));
                                    }
                                    else player.sendMessage(Message.raw("You can't downgrade your armor! (not a bright idea you've got here)"));
                                    event.getItemContainer().removeItemStackFromSlot((short) i);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(InfernalDescent.instance.getIsPlayer());
    }
}
