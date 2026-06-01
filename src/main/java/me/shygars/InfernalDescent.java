package me.shygars;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import lombok.Getter;
import me.shygars.actions.*;
import me.shygars.actions.manon.*;
import me.shygars.actions.zolahva.BuilderActionSetForcedWeather;
import me.shygars.commands.*;
import me.shygars.components.*;
import me.shygars.interactions.*;
import me.shygars.game.SlotLock;
import me.shygars.systems.*;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class InfernalDescent extends JavaPlugin {
    public static InfernalDescent instance;
    private PacketFilter inboundFilter;

    @Getter
    private ComponentType<EntityStore, IsPlayer> isPlayer;
    @Getter
    private ComponentType<EntityStore, PlayerClass> playerClassComponent;
    @Getter
    private ComponentType<EntityStore, SoulForm> soulFormComponent;
    @Getter
    private ComponentType<EntityStore, ReturningSurface> returningSurface;
    @Getter
    private ComponentType<EntityStore, BlackFeather> blackFeather;

    public InfernalDescent(@NonNullDecl JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        //Initialize Commands
        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new ToggleIsPlayer());
        commandRegistry.registerCommand(new SetPlayerClass());
        commandRegistry.registerCommand(new GetPlayerClass());
        commandRegistry.registerCommand(new ResetPlayerClass());
        commandRegistry.registerCommand(new GiveClassStarterItems());
        commandRegistry.registerCommand(new ToggleSoulForm());
        commandRegistry.registerCommand(new CancelQuest());
        commandRegistry.registerCommand(new GetPlayerClassStats());

        commandRegistry.registerCommand(new TempCommand());

        //Initialize Custom UI
        //this.getCodecRegistry(OpenCustomUIInteraction.PAGE_CODEC).register("ClassSelectionConfirmation", ClassSelectionConfirmationSupplier.class, ClassSelectionConfirmationSupplier.CODEC);

        //Initialize Interactions
        this.getCodecRegistry(Interaction.CODEC).register("ClassSelection", ClassSelectionInteraction.class, ClassSelectionInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("ClassCancel", ClassCancelInteraction.class, ClassCancelInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("SendEventTitle", SendEventTitleInteraction.class, SendEventTitleInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("TeleportLayer", TeleportLayerInteraction.class, TeleportLayerInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("SimpleTeleportation", SimpleTeleportInteraction.class, SimpleTeleportInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("NewGame", NewGameInteraction.class, NewGameInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("StatsUp", StatsUpInteraction.class, StatsUpInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("BlackFeather", BlackFeatherInteraction.class, BlackFeatherInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("CheckBlackFeather", CheckBlackFeatherInteraction.class, CheckBlackFeatherInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("CheckItemInActiveSlot", CheckItemInActiveSlotInteraction.class, CheckItemInActiveSlotInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("CastDamagingCircle", CastDamagingCircleInteraction.class, CastDamagingCircleInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("ChangeItemInActiveSlot", ChangeItemInActiveSlotInteraction.class, ChangeItemInActiveSlotInteraction.CODEC);

        //Initialize Components
        this.isPlayer = this.getEntityStoreRegistry().registerComponent(IsPlayer.class, "IsPlayer", IsPlayer.CODEC);
        this.playerClassComponent = this.getEntityStoreRegistry().registerComponent(PlayerClass.class, "PlayerClass", PlayerClass.CODEC);
        this.soulFormComponent = this.getEntityStoreRegistry().registerComponent(SoulForm.class, "SoulForm", SoulForm.CODEC);
        this.returningSurface = this.getEntityStoreRegistry().registerComponent(ReturningSurface.class, "ReturningSurface", ReturningSurface.CODEC);
        this.blackFeather = this.getEntityStoreRegistry().registerComponent(BlackFeather.class, "BlackFeather", BlackFeather.CODEC);

        //Initialize Sensors
        NPCPlugin.get().registerCoreComponentType("QuestStarted", BuilderSensorQuestStarted::new);
        NPCPlugin.get().registerCoreComponentType("DeadPlayers", BuilderSensorDeadPlayers::new);

        //Initialize Actions
        NPCPlugin.get().registerCoreComponentType("Message", BuilderActionMessage::new);
        NPCPlugin.get().registerCoreComponentType("StartQuest", BuilderActionStartQuest::new);
        NPCPlugin.get().registerCoreComponentType("RevivePlayers", BuilderActionRevivePlayers::new);
        NPCPlugin.get().registerCoreComponentType("ReviveBanner", BuilderActionReviveBanner::new);
        NPCPlugin.get().registerCoreComponentType("OpenLayerPortal", BuilderActionOpenLayerPortal::new);
        NPCPlugin.get().registerCoreComponentType("CompletedWaveHeal", BuilderActionCompletedWaveHeal::new);
        NPCPlugin.get().registerCoreComponentType("FinalWaveRevive", BuilderActionFinalWaveRevive::new);
        NPCPlugin.get().registerCoreComponentType("Teleport", BuilderActionTeleport::new);
        NPCPlugin.get().registerCoreComponentType("SetForcedWeather", BuilderActionSetForcedWeather::new);
        NPCPlugin.get().registerCoreComponentType("EventTitle", BuilderActionEventTitle::new);

        //Initialize Systems
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerJoinEventSystem::onPlayerReady);
        this.getEntityStoreRegistry().registerSystem(new OnDeathEventSystem());
        this.getEntityStoreRegistry().registerSystem(new SoulFormSystem());
        this.getEntityStoreRegistry().registerSystem(new FailureSystem());
        this.getEntityStoreRegistry().registerSystem(new DespawnMobSystem());
        this.getEntityStoreRegistry().registerSystem(new DropBlocker());
        this.getEntityStoreRegistry().registerSystem(new ItemsTradeSystem());
        this.getEntityStoreRegistry().registerSystem(new DamageModifiersSystem());
        this.getEntityStoreRegistry().registerSystem(new PlayerClassStatsSystem());
        this.getEntityStoreRegistry().registerSystem(new HUDTickRefresh());
        this.getEntityStoreRegistry().registerSystem(new LuckEffectSystem());

        //Initialize Packet Handlers
        SlotLock.registerPacketCounters();
    }

    @Override
    protected void shutdown() {
        if (inboundFilter != null) {
            PacketAdapters.deregisterInbound(inboundFilter);
        }
    }
}

//Thanks Ali!
//Thanks hytalemodding.dev!
//Thanks TroubleDEV!