package com.kamesuta.worldguardpreventlwc;

import com.griefcraft.lwc.LWC;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldguardPreventLwc extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        LWC.getInstance().getModuleLoader().registerModule(this, new WorldguardPreventLwcModule());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LWC.getInstance().getModuleLoader().removeModules(this);
    }
}
