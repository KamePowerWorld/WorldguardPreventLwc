package com.kamesuta.worldguardpreventlwc;

import com.griefcraft.scripting.JavaModule;
import com.griefcraft.scripting.event.LWCProtectionRegisterEvent;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class WorldguardPreventLwcModule extends JavaModule {
    @Override
    public void onRegisterProtection(LWCProtectionRegisterEvent event) {
        Block block = event.getBlock();

        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(block.getWorld()));
        if (regionManager == null) return;

        BlockVector3 blockVector = BukkitAdapter.asBlockVector(block.getLocation());
        List<String> regionSet = regionManager.getApplicableRegionsIDs(blockVector);

        if (!regionSet.isEmpty()) {
            // 設置時の保護のみ無効にしたい。そのためちょっと汚いが、StackTraceから呼び出し元を判定する。
            // onBlockPlaceMonitorから呼び出されたときのみ、キャンセルする
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            boolean isOnPlace = Arrays.stream(stackTraceElements)
                    .anyMatch(stackTraceElement ->
                            stackTraceElement.getClassName().equals("com.griefcraft.listeners.LWCBlockListener")
                                    && stackTraceElement.getMethodName().equals("onBlockPlaceMonitor")
                    );
            if (isOnPlace) {
                event.setCancelled(true);
            }
        }
    }
}
