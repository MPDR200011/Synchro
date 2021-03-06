package mpdr.synchro.network;


import mpdr.synchro.gui.GuiSynchroChest;
import mpdr.synchro.gui.container.ContainerSynchroChest;
import mpdr.synchro.tileentities.TileEntitySynchroChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class SynchroGUIHandler implements IGuiHandler{

    public static final int TILE_ENTITY_GUI_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == TILE_ENTITY_GUI_ID){
            return new ContainerSynchroChest(player.inventory, (TileEntitySynchroChest) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == TILE_ENTITY_GUI_ID){
            return new GuiSynchroChest(player.inventory, (TileEntitySynchroChest) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
