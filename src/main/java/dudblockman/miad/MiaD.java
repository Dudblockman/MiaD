package dudblockman.miad;

import dudblockman.miad.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = MiaD.MODID, name = MiaD.NAME, version = MiaD.VERSION)
public class MiaD {

	public static final String MODID = "MiaD";
	public static final String NAME = "My imaginary angry Duck";
	public static final String VERSION = "0.0.1";

	@Instance
	public static MiaD instance;

	@SidedProxy(clientSide = "dudblockman.miad.proxy.ClientProxy", serverSide = "dudblockman.miad.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
