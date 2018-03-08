package dudblockman.miad;

import dudblockman.miad.init.BlockInit;
import dudblockman.miad.init.ItemInit;
import dudblockman.miad.proxy.CommonProxy;
import dudblockman.miad.util.IHasRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = MiaD.MODID, name = MiaD.NAME, version = MiaD.VERSION)
public class MiaD {

	public static final String MODID = "miad";
	public static final String NAME = "My imaginary angry Duck";
	public static final String VERSION = "0.0.1";

	@Instance
	public static MiaD instance;

	@SidedProxy(clientSide = "dudblockman.miad.proxy.ClientProxy", serverSide = "dudblockman.miad.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	@SubscribeEvent
	public void items(Register<Item> e) {
		for (Item i : ItemInit.ITEMS)
			e.getRegistry().register(i);
	}

	@SubscribeEvent
	public void blocks(Register<Block> e) {
		for (Block b : BlockInit.BLOCKS)
			e.getRegistry().register(b);
	}

	@SubscribeEvent
	public void recipes(Register<IRecipe> e) {
		for (Item i : ItemInit.ITEMS)
			if (i instanceof IHasRecipe) ((IHasRecipe) i).initRecipes(e);
		for (Block b : BlockInit.BLOCKS)
			if (b instanceof IHasRecipe) ((IHasRecipe) b).initRecipes(e);
	}

}
