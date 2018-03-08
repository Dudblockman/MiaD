package dudblockman.miad.gui;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class GuiHeldBench extends GuiCrafting {

	public GuiHeldBench(EntityPlayer player, World world, EnumHand hand) {
		super(player.inventory, world);
		this.inventorySlots = new ContainerHeldBench(player, world, hand);
	}

}
