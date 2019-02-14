package mpdr.synchro.proxy;


import mpdr.synchro.Main;
import mpdr.synchro.handler.EventHandler;
import mpdr.synchro.init.ModBlocks;
import mpdr.synchro.init.ModGui;
import mpdr.synchro.init.ModTileEntities;
import mpdr.synchro.network.SynchroGUIHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e){
        ModBlocks.init();
        ModBlocks.register();

        ModTileEntities.init();
    }

    public void Init(FMLInitializationEvent e){
        ModGui.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new SynchroGUIHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void postInit(FMLPostInitializationEvent e){

    }
}
