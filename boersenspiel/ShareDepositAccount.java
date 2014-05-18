package boersenspiel;

import java.util.ArrayList;
import java.util.List;

public class ShareDepositAccount extends Asset {
    private ShareItem[] collection = new ShareItem[0];
    

    
    

    public ShareDepositAccount(String name) {
        this.setName(name);

    }
    
    public long getValue(){
        long value = 0;
        for(int index = 0; index < this.getCollection().length; index++){
            value += getCollection()[index].getValue();
        }
        
        return value;
    }

    public ShareItem[] getCollection() {
        return this.collection;
    }

    public void setCollection(ShareItem[] newCollection) {
        this.collection = newCollection;
    }

    public void setCollection(int index, ShareItem item){
        this.collection[index].setSAmount(item.getSAmount());
        this.collection[index].setValue(item.getValue());
    }
    
    public void expandCollection(Player bob, ShareItem item){
        ShareItem[] temp = expandArray(bob.getSAcc().collection);
        this.collection = copyMore(bob.getSAcc().collection, temp, item);
    }  
    
    public void shortenCollection(Player bob, ShareItem item){
        ShareItem[] temp = shortenArray(bob.getSAcc().collection);
        this.collection = copyLess(bob.getSAcc().collection, temp, item);
    }
    
    
    
    public String toString() {
        return "Name: " + getName() + ", Gesamteinkaufswert: " + (double)this.getValue()/100 + "€.";
    }

    /**
     * Durchsucht ein Array nach einem Aktiennamen und gibt die Stelle im Array aus, wenn er
     * gefunden wird
     */
    public boolean search(ShareItem[] collection, Share toCompare) {

        if (collection.length > 0) {
            for (int index = 0; index < collection.length; index++) {
                if (collection[index].getName() == toCompare.getName()) {
                    return true;
                }
            }
        }
        return false;
    }
    



    /** Erstellt ein neues Array, das um einen Index größer ist als das alte */
    public ShareItem[] expandArray(ShareItem[] oldArray) {
        ShareItem[] newArray = new ShareItem[oldArray.length + 1];
        return newArray;
    }
    
    /** Erstellt ein neues Array, das um einen Index kleiner ist als das alte */
    public ShareItem[] shortenArray(ShareItem[] oldArray) {
        ShareItem[] newArray = new ShareItem[oldArray.length - 1];
        return newArray;
    }

    /**
     * Kopiert das alte Array in das neue und fügt an letzter Position das neue
     * ShareItem Objekt ein
     */
    public ShareItem[] copyMore(ShareItem[] collection, ShareItem[] newArray, ShareItem item) {
        for (int index = 0; index < newArray.length; index++) {
            if (index != newArray.length - 1) {
                newArray[index] = collection[index];
            } else {
                newArray[index] = item;
            }
        }
        return newArray;
    }

    /**
     * Kopiert das alte Array in das neue und löscht das gewünschte Aktienpaket
     */
    public ShareItem[] copyLess(ShareItem[] collection, ShareItem[] newArray, ShareItem item) {
        int oldIndex = 0;
        
        for (int newIndex = 0; newIndex < newArray.length; newIndex++) {
          if (!collection[newIndex].getName().equals(item.getName())) {
              newArray[newIndex] = collection[oldIndex];
              oldIndex++;
          } else {
              oldIndex++;
              newIndex--;
          }
      }                
        return newArray;
    }
    
    
}