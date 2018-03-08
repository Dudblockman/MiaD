package dudblockman.miad.common.gui;

import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class GuiHeldFurnace extends GuiFurnace {

	public GuiHeldFurnace(EntityPlayer player, World world, EnumHand hand) {
		super(player.inventory, new InventoryBasic("Furnace", false, 3));
		this.inventorySlots = new ContainerHeldFurnace(player, world, hand);
	}

}
