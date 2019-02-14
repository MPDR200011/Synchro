package mpdr.synchro.manager;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;

public class SynchroChestManager{
    public static SynchroChestManager manager = new SynchroChestManager();

    private Map<String, ItemStack[]> storageMap = Collections.synchronizedMap(new HashMap<String, ItemStack[]>());
    private List<String> dirtyStorage = Collections.synchronizedList(new ArrayList<String>());
    private NBTTagCompound saveTag;

    public boolean save(){
        if (!this.dirtyStorage.isEmpty()){
            for (String key : dirtyStorage){
                this.saveTag.setTag(key, this.saveItemArray(key));
            }
            dirtyStorage.clear();
            return true;
        }
        return false;
    }

    public void load(){
        for (String key: this.saveTag.getKeySet()){
            ItemStack[] storage = new ItemStack[54];
            this.loadItemArray(storage, this.saveTag.getCompoundTag(key));
            storageMap.put(key, storage);
        }
    }

    public ItemStack[] getStorage(String frequency){
        ItemStack[] storage = storageMap.get(frequency);
        if (storage == null){
            storage = new ItemStack[54];
            if (saveTag.hasKey(frequency)){
                loadItemArray(storage, this.saveTag.getCompoundTag(frequency));;
            }
            storageMap.put(frequency, storage);
        }
        return storage;
    }

    public void requestSave(String frequency, ItemStack[] storage){
        dirtyStorage.add(frequency);
    }

    public NBTTagCompound getSaveTag(){
        return this.saveTag;
    }

    public void setSaveTag(NBTTagCompound saveTag) {
        this.saveTag = saveTag;
    }
    
    private NBTTagCompound saveItemArray(String frequency) {
    	NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        ItemStack[] items = this.storageMap.get(frequency);
        if (items != null) {
			for (int i = 0; i < 54; ++i) {
				if (items[i] != null) {
					NBTTagCompound stackTag = new NBTTagCompound();
					stackTag.setByte("Slot", (byte) i);
					items[i].writeToNBT(stackTag);
					list.appendTag(stackTag);
				}
			} 
        }
		compound.setTag("Items", list);
        return compound;
    }
    
    private void loadItemArray(ItemStack[] storage, NBTTagCompound nbt) {
    	NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i){
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int index = stackTag.getByte("Slot") & 255;
            storage[index] = ItemStack.loadItemStackFromNBT(stackTag);
        }
    }

    public boolean checkDirty(String frequency) {
    	return this.dirtyStorage.contains(frequency);
    }
    
}
