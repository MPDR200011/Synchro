package mpdr.synchro;

import mpdr.synchro.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.id, name = Main.name, version = Main.version)
public class Main {

    public static final String id = "synchro";
    public static final String name = "Synchro";
    public static final String version = "1.0.0";

    @Mod.Instance
    public static Main instance = new Main();

    @SidedProxy(clientSide = "mpdr.synchro.proxy.ClientProxy", serverSide = "mpdr.synchro.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent e){
        proxy.Init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        proxy.postInit(e);
    }

}
