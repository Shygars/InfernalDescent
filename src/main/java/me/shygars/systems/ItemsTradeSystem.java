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
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.ClassItems;
import me.shygars.game.classes.ClassItemsDistribution;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Arrays;

public class ItemsTradeSystem extends EntityEventSystem<EntityStore, InventoryChangeEvent> {
    public ItemsTradeSystem() {
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

    public short refundMainTotal(int playerClass, int upgradeTier) {
        if (playerClass == 0 || playerClass == 2) {
            if (upgradeTier == 1) {
                return 32;
            }
            else if (upgradeTier == 2) {
                return 64;
            }
            else return 160;
        }
        else if (playerClass == 1) {
            if (upgradeTier == 1) {
                return 56;
            }
            else if (upgradeTier == 2) {
                return 112;
            }
            else return 280;
        }
        else if (playerClass == 3) {
            if (upgradeTier == 1) {
                return 36;
            }
            else if (upgradeTier == 2) {
                return 68;
            }
            else return 165;
        }
        return 0;
    }

    public short refundSecondaryTotal(int playerClass, int upgradeTier) {
        if (playerClass == 0) {
            if (upgradeTier == 1) {
                return 24;
            }
            else if (upgradeTier == 2) {
                return 48;
            }
            else return 120;
        }
        else if (playerClass == 2) {
            if (upgradeTier == 1) {
                return 18;
            }
            else if (upgradeTier == 2) {
                return 36;
            }
            else return 90;
        }
        else if (playerClass == 3) {
            if (upgradeTier == 1) {
                return 28;
            }
            else if (upgradeTier == 2) {
                return 52;
            }
            else return 125;
        }
        return 0;
    }

    public short refundArmorTotal(int playerClass, int upgradeTier) {
        if (playerClass == 0 || playerClass == 1) {
            if (upgradeTier == 1) {
                return 27;
            }
            else if (upgradeTier == 2) {
                return 54;
            }
            else return 135;
        }
        else if (playerClass == 2) {
            if (upgradeTier == 1) {
                return 33;
            }
            else if (upgradeTier == 2) {
                return 66;
            }
            else return 165;
        }
        else if (playerClass == 3) {
            if (upgradeTier == 1) {
                return 19;
            }
            else if (upgradeTier == 2) {
                return 46;
            }
            else return 125;
        }
        return 0;
    }

    @Override
    public void handle(
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
            @NonNullDecl InventoryChangeEvent event) {
        World world = store.getExternalData().getWorld();
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        if (player != null) {
            if (playerClass != null) {
                int playerCurrentClass = playerClass.getCurrentClass();
                if (playerCurrentClass != 4) {
                    if (event.getItemContainer().getCapacity() == 9 || event.getItemContainer().getCapacity() == 36) {
                        for (short i = 0; i < event.getItemContainer().getCapacity(); i++) {
                            ItemStack selectedSlotItemStack = event.getItemContainer().getItemStack(i);
                            if (selectedSlotItemStack != null) {
                                // Give discharged lost crucifix
                                if (selectedSlotItemStack.getItemId().equals("Lost_Crucifix_Pickup")) {
                                    ItemStack stack = new ItemStack("Lost_Crucifix", 1, 0, 1000, null);
                                    event.getItemContainer().replaceItemStackInSlot(i, selectedSlotItemStack, stack);
                                    break;
                                }
                                // Change shop items to class gears
                                if (Arrays.asList(ClassItems.classItemUpgrades[playerCurrentClass][0]).contains(selectedSlotItemStack)) {
                                    if (playerClass.getMainWeaponUpgrade() < ClassItems.getUpgradeTierIndex(selectedSlotItemStack)) {
                                        playerClass.setMainWeaponUpgrade(ClassItems.getUpgradeTierIndex(selectedSlotItemStack));
                                        ClassItemsDistribution.giveClassItems(player);
                                        player.sendMessage(Message.raw("Your main weapon was upgraded to Tier " + (ClassItems.getUpgradeTierIndex(selectedSlotItemStack) + 1)));
                                    }
                                    else {
                                        world.execute(() -> {
                                            ItemStack stack = (new ItemStack("Infernal_Soul", refundMainTotal(playerClass.getCurrentClass(), ClassItems.getUpgradeTierIndex(selectedSlotItemStack))));
                                            player.giveItem(stack, ref, store);
                                        });
                                        player.sendMessage(Message.raw("You can't downgrade your main weapon!"));
                                    }
                                    event.getItemContainer().removeItemStackFromSlot(i);
                                    break;
                                }
                                else if (Arrays.asList(ClassItems.classItemUpgrades[playerCurrentClass][1]).contains(selectedSlotItemStack)) {
                                    if (playerClass.getSecWeaponUpgrade() < ClassItems.getUpgradeTierIndex(selectedSlotItemStack)) {
                                        playerClass.setSecWeaponUpgrade(ClassItems.getUpgradeTierIndex(selectedSlotItemStack));
                                        ClassItemsDistribution.giveClassItems(player);
                                        player.sendMessage(Message.raw("Your secondary weapon was upgraded to Tier " + (ClassItems.getUpgradeTierIndex(selectedSlotItemStack) + 1)));
                                    }
                                    else {
                                        world.execute(() -> {
                                            ItemStack stack = (new ItemStack("Infernal_Soul", refundSecondaryTotal(playerClass.getCurrentClass(), ClassItems.getUpgradeTierIndex(selectedSlotItemStack))));
                                            player.giveItem(stack, ref, store);
                                        });
                                        player.sendMessage(Message.raw("You can't downgrade your secondary weapon!"));
                                    }
                                    event.getItemContainer().removeItemStackFromSlot(i);
                                    break;
                                }
                                else if (Arrays.asList(ClassItems.classItemUpgrades[playerCurrentClass][2]).contains(selectedSlotItemStack)) {
                                    if (playerClass.getArmorUpgrade() < ClassItems.getUpgradeTierIndex(selectedSlotItemStack)) {
                                        playerClass.setArmorUpgrade(ClassItems.getUpgradeTierIndex(selectedSlotItemStack));
                                        ClassItemsDistribution.giveClassItems(player);
                                        player.sendMessage(Message.raw("Your armor was upgraded to Tier " + (ClassItems.getUpgradeTierIndex(selectedSlotItemStack) + 1)));
                                    }
                                    else {
                                        world.execute(() -> {
                                            ItemStack stack = (new ItemStack("Infernal_Soul", refundArmorTotal(playerClass.getCurrentClass(), ClassItems.getUpgradeTierIndex(selectedSlotItemStack))));
                                            player.giveItem(stack, ref, store);
                                        });
                                        player.sendMessage(Message.raw("You can't downgrade your armor!"));
                                    }
                                    event.getItemContainer().removeItemStackFromSlot(i);
                                    break;
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
