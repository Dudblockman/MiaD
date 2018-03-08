package dudblockman.miad.proxy;

import dudblockman.miad.common.items.IItemGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID / 100];
		System.out.println("GUIs" + Integer.toString(ID));
		ID %= 100;//Slot determined, get actual ID
		ItemStack item = player.getItemStackFromSlot(slot);
		if (!item.isEmpty() && item.getItem() instanceof IItemGUI && ((IItemGUI) item.getItem()).getGuiID(item) == ID) {
			//return new ContainerWorkbench(player.inventory, world, slot, item);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerItemRenderer(Item item, int meta, String id) {
		// TODO Auto-generated method stub

	}

}
