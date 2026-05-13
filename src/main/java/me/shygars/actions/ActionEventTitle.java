package me.shygars.actions;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class ActionEventTitle extends ActionBase {
    protected final Boolean toEveryone;
    protected final String messageString;
    protected final String secondaryMessageString;
    protected final Boolean isMajor;
    protected final float duration;
    protected final float fadeInDuration;
    protected final float fadeOutDuration;
    protected final String eventSound;

    public ActionEventTitle(@NonNullDecl BuilderActionEventTitle builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.toEveryone = builder.getIsToEveryone(support);
        this.messageString = builder.getMessageString(support);
        this.secondaryMessageString = builder.getSecondaryMessageString(support);
        this.isMajor = builder.getIsMajor(support);
        this.duration = builder.getDuration(support);
        this.fadeInDuration = builder.getFadeInDuration(support);
        this.fadeOutDuration = builder.getFadeOutDuration(support);
        this.eventSound = builder.getEventSound(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        if (toEveryone) {
            EventTitleUtil.showEventTitleToUniverse(Message.raw(messageString), Message.raw(secondaryMessageString), isMajor, null, duration, fadeInDuration, fadeOutDuration);
            if (eventSound != null) {
                SoundUtil.playSoundEvent2d(SoundEvent.getAssetMap().getIndex(eventSound), SoundCategory.SFX, store);
            }
        }
        else {
            Ref<EntityStore> refES = role.getStateSupport().getInteractionIterationTarget();
            if (refES == null) return false;
            PlayerRef playerRef = store.getComponent(refES, PlayerRef.getComponentType());
            if (playerRef == null) return false;
            EventTitleUtil.showEventTitleToPlayer(playerRef, Message.raw(messageString), Message.raw(secondaryMessageString), isMajor, null, duration, fadeInDuration, fadeOutDuration);
            if (eventSound != null) {
                SoundUtil.playSoundEvent2dToPlayer(playerRef, SoundEvent.getAssetMap().getIndex(eventSound), SoundCategory.SFX);
            }
        }
        return true;
    }
}
