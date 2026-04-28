package me.shygars.actions.zolahva;

import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import me.shygars.actions.manon.BuilderActionSetForcedWeather;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class ActionSetForcedWeather extends ActionBase {

    protected final String weatherId;

    public ActionSetForcedWeather(@NonNullDecl BuilderActionSetForcedWeather builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.weatherId = builder.getWeatherId(support);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        World world = store.getExternalData().getWorld();

        WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
        weatherResource.setForcedWeather(this.weatherId);
        WorldConfig config = world.getWorldConfig();
        config.setForcedWeather(this.weatherId);
        config.markChanged();

        return true;
    }
}
