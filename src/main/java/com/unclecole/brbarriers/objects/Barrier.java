package com.unclecole.brbarriers.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class Barrier {

    @Getter private String name;
    @Setter @Getter private String corner1;
    @Setter @Getter private String corner2;

    public Barrier(String name) {
        this.name = name;
        corner1 = null;
        corner2 = null;
    }


}
