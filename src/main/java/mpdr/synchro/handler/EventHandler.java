package mpdr.synchro.handler;

import mpdr.synchro.manager.SynchroChestManager;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.io.*;

public class EventHandler {
    private SynchroChestManager manager = SynchroChestManager.manager;

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event){

        try {
            File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "synchro/chest_inventories.dat");
            if (file.exists()) {
                FileInputStream in = new FileInputStream(file);
                manager.setSaveTag(CompressedStreamTools.readCompressed(in));
                in.close();
                manager.load();
            } else {
            	manager.setSaveTag(new NBTTagCompound());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event){
        try{
            if (manager.save() && !manager.getSaveTag().hasNoTags()) {
                File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "synchro/chest_inventories.dat");
                if (!file.exists()){
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(file));
                CompressedStreamTools.writeCompressed(manager.getSaveTag(), dataOut);
                dataOut.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
