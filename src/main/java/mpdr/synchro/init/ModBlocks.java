package mpdr.synchro.init;


import mpdr.synchro.blocks.SynchroChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static Block sChest;

    public static final void init(){
        sChest = new SynchroChestBlock(Material.WOOD);
    }

    public static final void register(){
        registerBlock(sChest);
    }

    private static final void registerBlock(Block block){
        GameRegistry.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

    public static void registerRenders(){
        registerRender(sChest);
    }

    private static void registerRender(Block block){
        Minecraft
                .getMinecraft()
                .getRenderItem()
                .getItemModelMesher()
                .register(
                        Item.getItemFromBlock(block),
                        0,
                        new ModelResourceLocation(
                                block.getRegistryName(),
                                "inventory"));
    }
}
