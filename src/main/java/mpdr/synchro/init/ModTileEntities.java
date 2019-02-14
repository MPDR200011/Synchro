package mpdr.synchro.init;


import mpdr.synchro.tileentities.TileEntitySynchroChest;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void init(){
        GameRegistry.registerTileEntity(TileEntitySynchroChest.class, "synchrochest_tile_entity");
    }
}
