package com.unclecole.brbarriers.database;

import com.unclecole.brbarriers.database.serializer.Serializer;
import com.unclecole.brbarriers.objects.Barrier;
import com.unclecole.brbarriers.objects.Key;
import lombok.Getter;

import java.util.HashMap;

public class BarrierData {

    public static transient BarrierData instance = new BarrierData();

    public static HashMap<String, Key> barrierKeys = new HashMap<>();
    public static HashMap<String, Barrier> barrierList = new HashMap<>();

    public static void save() {
        new Serializer().save(instance);
    }

    public static void load() {
        new Serializer().load(instance, BarrierData.class, "barrierdata");
    }

}