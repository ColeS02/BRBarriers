package com.unclecole.brbarriers.tasks;

import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.database.BarrierData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleTask implements Runnable {

    @Override
    public void run() {

        if(BRBarriers.getInstance().getViewBarrierList().isEmpty()) return;

        BRBarriers.getInstance().getViewBarrierList().forEach((uuid, s) -> {
            if(!Bukkit.getPlayer(uuid).isOnline()) BRBarriers.getInstance().getViewBarrierList().remove(uuid);

            getHollowCube(BRBarriers.getInstance().getLocationUtility().parseToLocation(BarrierData.barrierList.get(s).getCorner1()),
                    BRBarriers.getInstance().getLocationUtility().parseToLocation(BarrierData.barrierList.get(s).getCorner2())).forEach(location -> {
                ParticleEffect.REDSTONE.display(location,Color.GREEN,Bukkit.getPlayer(uuid));
            });
        });
    }


    public List<Location> getHollowCube(Location corner1, Location corner2) {
        List<Location> result = new ArrayList<>();
        World world = corner1.getWorld();
        int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int minY = Math.min(corner1.getBlockY(), corner2.getBlockY());
        int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
        int maxY = Math.max(corner1.getBlockY(), corner2.getBlockY());
        int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());

        // 2 areas
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                result.add(new Location(world, x, minY, z));
                result.add(new Location(world, x, maxY, z));
            }
        }

        // 2 sides (front & back)
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                result.add(new Location(world, x, y, minZ));
                result.add(new Location(world, x, y, maxZ));
            }
        }

        // 2 sides (left & right)
        for (int z = minZ; z <= maxZ; z++) {
            for (int y = minY; y <= maxY; y++) {
                result.add(new Location(world, minX, y, z));
                result.add(new Location(world, maxX, y, z));
            }
        }

        return result;
    }
}
