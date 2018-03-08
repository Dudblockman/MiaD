package dudblockman.miad.proxy;

import dudblockman.miad.common.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	public void preInit(FMLPreInitializationEvent e) {

	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == 0) return new ContainerHeldBench(player, world, EnumHand.values()[y]);
		if (id == 1) return new ContainerHeldFurnace(player, world, EnumHand.values()[y]);
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == 0) return new GuiHeldBench(player, world, EnumHand.values()[y]);
		if (id == 1) return new GuiHeldFurnace(player, world, EnumHand.values()[y]);
		return null;
	}

}
