package me.shygars.systems;

import com.hypixel.hytale.common.util.ArrayUtil;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.gameplay.RespawnConfig;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.components.SoulForm;
import me.shygars.game.ItemRewards;
import me.shygars.game.classes.ClassItemsDistribution;
import me.shygars.game.hud.StatsUpHUD;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PlayerJoinEventSystem {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public static void onPlayerReady(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        assert player.getReference() != null;
        Ref<EntityStore> ref = player.getReference();
        Store<EntityStore> store = ref.getStore();
        PlayerClass playerClass = store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        World world = store.getExternalData().getWorld();
        assert playerClass != null;
        assert playerRef != null;
        // Manage HUD
        HudManager hudManager = player.getHudManager();
        if (hudManager.getVisibleHudComponents().contains(HudComponent.Compass)) {
            hudManager.hideHudComponents(playerRef, HudComponent.Compass);
        }
        if (store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent()) == null) {
            // Set PlayerClass component (playerClassData = 4 so it's set to no class selected yet)
            store.putComponent(ref, InfernalDescent.instance.getPlayerClassComponent(), new PlayerClass());

            // Send message to tell the classless player to choose a class
            EventTitleUtil.showEventTitleToPlayer(playerRef, Message.raw("Please choose your Class"), Message.raw("You won't be able to change your class once you accept the quest"), false, null, 15, 1, 1);

            // Set the respawn points on every layers
            PlayerRespawnPointData[] deadLandsRespawnPoints = player.getPlayerConfigData().getPerWorldData("TheDeadLands").getRespawnPoints();
            PlayerRespawnPointData[] lavaSpringsRespawnPoints = player.getPlayerConfigData().getPerWorldData("LavaSprings").getRespawnPoints();
            PlayerRespawnPointData[] infernalCastleRespawnPoints = player.getPlayerConfigData().getPerWorldData("InfernalCastle").getRespawnPoints();

            PlayerWorldData perWorldDataDeadLands = player.getPlayerConfigData().getPerWorldData("TheDeadLands");
            PlayerWorldData perWorldDataLavaSprings = player.getPlayerConfigData().getPerWorldData("LavaSprings");
            PlayerWorldData perWorldDataInfernalCastle = player.getPlayerConfigData().getPerWorldData("InfernalCastle");

            perWorldDataDeadLands.setRespawnPoints(ArrayUtil.append(deadLandsRespawnPoints, new PlayerRespawnPointData(new Vector3i(1, 94, -6), new Vector3d(1, 95, -6), "Dead Lands Respawn Point")));
            perWorldDataLavaSprings.setRespawnPoints(ArrayUtil.append(lavaSpringsRespawnPoints, new PlayerRespawnPointData(new Vector3i(0, 97, 1), new Vector3d(0, 98, 1), "Lava Springs Respawn Point")));
            perWorldDataInfernalCastle.setRespawnPoints(ArrayUtil.append(infernalCastleRespawnPoints, new PlayerRespawnPointData(new Vector3i(32, 105, -1), new Vector3d(32, 106, -1), "Infernal Castle Respawn Point")));
        }
        else if (Objects.requireNonNull(store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent())).getCurrentClass() == 4) {
            EventTitleUtil.showEventTitleToPlayer(playerRef, Message.raw("Please choose your Class"), Message.raw("You won't be able to change your class once you accept the quest"), false, null, 15, 1, 1);
            PlayerRespawnPointData[] deadLandsRespawnPoints = player.getPlayerConfigData().getPerWorldData("TheDeadLands").getRespawnPoints();
            PlayerRespawnPointData[] lavaSpringsRespawnPoints = player.getPlayerConfigData().getPerWorldData("LavaSprings").getRespawnPoints();
            PlayerRespawnPointData[] infernalCastleRespawnPoints = player.getPlayerConfigData().getPerWorldData("InfernalCastle").getRespawnPoints();

            PlayerWorldData perWorldDataDeadLands = player.getPlayerConfigData().getPerWorldData("TheDeadLands");
            PlayerWorldData perWorldDataLavaSprings = player.getPlayerConfigData().getPerWorldData("LavaSprings");
            PlayerWorldData perWorldDataInfernalCastle = player.getPlayerConfigData().getPerWorldData("InfernalCastle");

            perWorldDataDeadLands.setRespawnPoints(ArrayUtil.append(deadLandsRespawnPoints, new PlayerRespawnPointData(new Vector3i(1, 94, -6), new Vector3d(1, 95, -6), "Dead Lands Respawn Point")));
            perWorldDataLavaSprings.setRespawnPoints(ArrayUtil.append(lavaSpringsRespawnPoints, new PlayerRespawnPointData(new Vector3i(0, 97, 1), new Vector3d(0, 98, 1), "Lava Springs Respawn Point")));
            perWorldDataInfernalCastle.setRespawnPoints(ArrayUtil.append(infernalCastleRespawnPoints, new PlayerRespawnPointData(new Vector3i(32, 105, -1), new Vector3d(32, 106, -1), "Infernal Castle Respawn Point")));
        }
        if (store.getComponent(ref, InfernalDescent.instance.getSoulFormComponent()) != null) {
            world.execute(() -> {
                // Give back soul model to dead player that may have disconnected
                if (playerClass.getCurrentClass() == 0) store.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Demon_Hunter_Soul", 1, null, false).toModel()));
                if (playerClass.getCurrentClass() == 1) store.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Paladin_Of_Light_Soul", 1, null, false).toModel()));
                if (playerClass.getCurrentClass() == 2) store.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Shadow_Knight_Soul", 1, null, false).toModel()));
                if (playerClass.getCurrentClass() == 3) store.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Ethereal_Mage_Soul", 1, null, false).toModel()));

                // Remove Items when returning to surface
                if (world.getName().equals("default")) {
                    // Reset Stats Ups
                    PlayerClass newPlayerClass = new PlayerClass(playerClass.getCurrentClass(), playerClass.getMainWeaponUpgrade(), playerClass.getSecWeaponUpgrade(), playerClass.getArmorUpgrade());
                    store.replaceComponent(ref, InfernalDescent.instance.getPlayerClassComponent(), newPlayerClass);

                    // Remove Black feather effect
                    store.tryRemoveComponent(ref, InfernalDescent.instance.getBlackFeather());

                    ItemContainer hotbarContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-1)), ref, store);
                    ItemContainer generalContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-2)), ref, store);
                    assert hotbarContainer != null;
                    ItemRewards.removeLosableItemRewards(hotbarContainer, generalContainer);
                }
            });
        }
    }
}