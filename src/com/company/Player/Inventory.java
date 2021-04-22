package com.company.Player;

import com.company.Items.AbstractCompanion;
import com.company.Items.AbstractItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {

    // use dict to track slots?
    private HashMap<AbstractItem,Integer> slots = new HashMap<AbstractItem,Integer>();
    private List<AbstractCompanion> companions = new ArrayList<AbstractCompanion>();

}
