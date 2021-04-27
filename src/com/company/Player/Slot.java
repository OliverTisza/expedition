package com.company.Player;

import com.company.Items.AbstractItem;
import com.company.Items.ItemAddResult;

public class Slot {

    private AbstractItem heldItem;
    private int heldCount;
    private int size;

    public Slot() {
        this.heldItem = null;
        this.heldCount = 0;
        this.size = 7;
    }

    public Slot(AbstractItem heldItem, int heldCount, int size) {
        this.heldItem = heldItem;
        this.heldCount = heldCount;
        this.size = size;
    }

    public AbstractItem getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(AbstractItem heldItem) {
        this.heldItem = heldItem;
    }

    public int getHeldCount() {
        return heldCount;
    }

    public void setHeldCount(int heldCount) {
        this.heldCount = heldCount;
    }

    public String getName() {
        if(heldItem != null) {
            return heldItem.getName();
        }else {
            return "Empty";
        }
    }

    /**
     * A tárhely egyik rekeszébe tesz egy tárgyat amennyiben az azonos a már rekeszben lévő tárggyal,
     * vagy üres rekeszbe rakja egyébként
     *
     * @param item az eltárolandó tárgy
     * @return az eltárolandó tárgy és az adott rekesz viszonya
     */
    public ItemAddResult addItem(AbstractItem item){
        if(heldCount == 0) {
            heldItem = item;
            heldCount++;
            return ItemAddResult.SUCCESS;
        }

        if(heldItem.getName().equals(item.getName())) {
            if (item.IsStackable()) {
                if (heldCount + 1 <= size) {
                    this.heldCount++;
                    return ItemAddResult.SUCCESS;
                }
                return ItemAddResult.SLOT_FULL;
            }
            return ItemAddResult.NOT_STACKABLE;
        }
        return  ItemAddResult.WRONG_ITEM;
    }


    /**
     * Az adott rekeszből kiveszi az itt tárolt tárgyat
     */
    public void decreaseHeldCount()
    {
        this.heldCount--;
        if(heldCount == 0)
        {
            heldItem = null;
        }
    }
}
