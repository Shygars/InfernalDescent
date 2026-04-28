package me.shygars.game;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.inventory.MoveItemStack;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.handlers.game.GamePacketHandler;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.IsPlayer;
import me.shygars.components.PlayerClass;

import java.util.Objects;

public class SlotLock {
    private static IsPlayer getIsPlayer(PacketHandler handler) {
        if (handler instanceof GamePacketHandler gpHandler) {
            return gpHandler.getPlayerRef().getComponent(InfernalDescent.instance.getIsPlayer());
            }
        return null;
    }

    public static void registerPacketCounters() {
        // Listener for received packets (Inbound) (Client to Server)
        PacketAdapters.registerInbound((PacketHandler handler, Packet packet) -> {
            // Prevents Classes Items (Weapons and Armors) from leaving their corresponding slots
                if (packet instanceof MoveItemStack movedItem) {
                    if (getIsPlayer(handler) != null) {
                    // Prevents moving the item out of the locked slot (Hotbar)
                    if (movedItem.fromSectionId == -1) {
                        if (movedItem.fromSlotId == 0 || movedItem.fromSlotId == 1) {
                            movedItem.fromSlotId = movedItem.toSlotId;
                            movedItem.fromSectionId = movedItem.toSectionId;
                            return true;
                        }
                    }
                    // Prevents moving the item out of the locked slot (Armor)
                    if (movedItem.fromSectionId == -3) {
                        movedItem.fromSlotId = movedItem.toSlotId;
                        movedItem.fromSectionId = movedItem.toSectionId;
                        return true;
                    }
                        // Prevents moving the item out of the locked slot (Utility)
                        if (movedItem.fromSectionId == -5) {
                            movedItem.fromSlotId = movedItem.toSlotId;
                            movedItem.fromSectionId = movedItem.toSectionId;
                            return true;
                        }
                    // Prevents moving an item into the locked slot (Hotbar)
                    if (movedItem.toSectionId == -1) {
                        if (movedItem.toSlotId == 0 || movedItem.toSlotId == 1) {
                            movedItem.toSlotId = movedItem.fromSlotId;
                            movedItem.toSectionId = movedItem.fromSectionId;
                            return true;
                        }
                    }
                }
            }
            return false;
        });

        // Listener for received packets (Outbound) (Server to Client)
        PacketAdapters.registerOutbound((PacketHandler handler, Packet packet) -> {
        });
    }
}
