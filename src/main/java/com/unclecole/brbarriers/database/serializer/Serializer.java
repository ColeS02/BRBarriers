package com.unclecole.brbarriers.database.serializer;

import com.unclecole.brbarriers.BRBarriers;

public class Serializer {


    /**
     * Saves your class to a .json file.
     */
    public void save(Object instance) {
        BRBarriers.getPersist().save(instance);
    }

    /**
     * Loads your class from a json file
     *
   */
    public <T> T load(T def, Class<T> clazz, String name) {
        return BRBarriers.getPersist().loadOrSaveDefault(def, clazz, name);
    }



}
