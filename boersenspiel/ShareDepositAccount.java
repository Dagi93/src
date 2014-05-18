package boersenspiel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import exceptions.ShareNotFoundException;

public class ShareDepositAccount extends Asset {

    private Map<String, ShareItem> items = new HashMap<String, ShareItem>();

    public ShareDepositAccount(String name) {
        this.setName(name);

    }

    public Map<String, ShareItem> getItems() {
        return items;
    }

    public void setItems(Map<String, ShareItem> items) {
        this.items = items;
    }
    
    public void add(ShareItem item) {
        if (items.containsKey(item.getName())) {
            for (ShareItem s : items.values())
                if (s.getName().equals(item.getName())) {
                    s.addAmount(item.getAmount());
                }
        } else {
            items.put(item.getName(), item);
        }

    }
    
    public void sub(ShareItem item) {
        for (ShareItem s : items.values())
            if (s.getName().equals(item.getName())) {
                s.addAmount(item.getAmount()*(-1));
            }
    }

    public void remove(ShareItem item) throws ShareNotFoundException {
        if (items.containsKey(item.getName())) {
            items.remove(item.getName());
        } else {
            throw new ShareNotFoundException("Diese Aktie befindet sich nicht im Aktiendepot des Spielers.");
        }
    }

    public long getValue() {
        long value = 0;
        for (ShareItem s : items.values()) {
            value += s.getValue();
        }
        
        return value;
    }
    
    public String toString() {
        return "Name: " + getName() + ", Gesamteinkaufswert: " + (double) this.getValue() / 100 + "€.";
    }
}