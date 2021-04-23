package com.company.Player;

import com.company.Items.AbstractItem;
import com.company.Items.ItemAddResult;
import com.company.Items.Slot;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Slot> slots;
    private int maxSlotCountWithoutPenalty;

    public Inventory() {
        this.slots = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            slots.add(new Slot());
        }
        maxSlotCountWithoutPenalty = 8;
    }

    public Inventory(int slotCount,int maxSlotCountWithoutPenalty) {
        this.slots = new ArrayList<>();
        for(int i = 0; i < slotCount; i++) {
            this.slots.add(new Slot());
        }
        this.maxSlotCountWithoutPenalty = maxSlotCountWithoutPenalty;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void addItem(AbstractItem item) {
        boolean carry = false;
        for(Slot slot : slots) {
            if(slot.getName().equals(item.getName())) {
                ItemAddResult result = slot.addItem(item);
                if(result == ItemAddResult.SUCCESS) {
                    carry = false;
                    return;
                }else if(result == ItemAddResult.NOT_STACKABLE || result == ItemAddResult.SLOT_FULL) {
                    carry = true;
                    continue;
                }
            }
        }
        if(carry) {
            for(Slot slot : slots) {
                if(slot.getName().equals("Empty")) {
                    slot.addItem(item);
                    return;
                }
            }
            Slot s = new Slot();
            s.addItem(item);
            slots.add(s);
        }
    }

    public void removeItem(AbstractItem item) {
        for(Slot slot : slots) {
            if(slot.getName().equals(item.getName())) {
                slot.decreaseHeldCount();
            }
        }
    }

    public void removeItem(String itemName) {
        for(Slot slot : slots) {
            if(slot.getName().equals(itemName)) {
                slot.decreaseHeldCount();
            }
        }
    }

    public int getMaxSlotCountWithoutPenalty() {
        return maxSlotCountWithoutPenalty;
    }

    public void setMaxSlotCountWithoutPenalty(int maxSlotCountWithoutPenalty) {
        this.maxSlotCountWithoutPenalty = maxSlotCountWithoutPenalty;
    }

    public float getOverCommitmentPenalty() {
        int numOfNonEmptySlots = 0;
        for(Slot slot : slots) {
            if(!slot.getName().equals("Empty")) {
                numOfNonEmptySlots++;
            }
        }
        if(numOfNonEmptySlots <= maxSlotCountWithoutPenalty) {
            return 0.0f;
        }else {
            return (numOfNonEmptySlots - maxSlotCountWithoutPenalty) * 0.2f;
        }
    }
}
