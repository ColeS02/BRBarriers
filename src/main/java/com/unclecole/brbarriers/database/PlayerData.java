package com.unclecole.brbarriers.database;

import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.database.serializer.Persist;
import com.unclecole.brbarriers.database.serializer.Serializer;
import com.unclecole.brbarriers.objects.BarrierList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    public static transient PlayerData instance = new PlayerData();

    public static BarrierList barrierList;

    public static void save(String uuid) {
        barrierList = BRBarriers.getInstance().getUnlockedBarriersList().get(UUID.fromString(uuid));
        new Persist().save(instance, "/playerdata/" + uuid);
    }

    public static void load(String uuid) {
        barrierList = new BarrierList(uuid);
        new Serializer().load(instance, PlayerData.class, "/playerdata/" + uuid);
            BRBarriers.getInstance().getUnlockedBarriersList().put(UUID.fromString(uuid), barrierList);
    }

}