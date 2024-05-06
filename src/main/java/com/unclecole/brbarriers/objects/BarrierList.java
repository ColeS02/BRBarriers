package com.unclecole.brbarriers.objects;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class BarrierList {

    private String uuid;
    private ArrayList<String> barriers;

    public BarrierList(String uuid) {
        this.uuid = uuid;
        this.barriers = new ArrayList<>();
    }

    public boolean contains(String s) {
        return barriers.contains(s);
    }

    public void add(String s) {
        barriers.add(s);
    }
}
