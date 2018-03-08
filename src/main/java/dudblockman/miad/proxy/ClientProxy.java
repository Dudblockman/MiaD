package dudblockman.miad.proxy;

import dudblockman.miad.init.BlockInit;
import dudblockman.miad.init.ItemInit;
import dudblockman.miad.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void models(ModelRegistryEvent e) {
		for (Item i : ItemInit.ITEMS)
			if (i instanceof IHasModel) ((IHasModel) i).initModels(e);
		for (Block b : BlockInit.BLOCKS)
			if (b instanceof IHasModel) ((IHasModel) b).initModels(e);
	}
}
