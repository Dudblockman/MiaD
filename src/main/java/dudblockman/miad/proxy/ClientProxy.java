package dudblockman.miad.proxy;

import dudblockman.miad.client.gui.GuiWorkbench;
import dudblockman.miad.common.items.IItemGUI;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID / 100];
		System.out.println("GUIc" + Integer.toString(ID));
		ID %= 100;//Slot determined, get actual ID
		ItemStack item = player.getItemStackFromSlot(slot);
		if (!item.isEmpty() && item.getItem() instanceof IItemGUI && ((IItemGUI) item.getItem()).getGuiID(item) == ID) { return new GuiWorkbench(player.inventory, world, slot, item); }

		//		item = ItemStack.EMPTY;
		//		for (EnumHand hand : EnumHand.values())
		//		{
		//			ItemStack held = player.getHeldItem(hand);
		//			if(!held.isEmpty() && held.getItem() instanceof IItemGUI && ((IItemGUI)held.getItem()).getGuiID(held)==ID)
		//				item = held;
		//		}
		//		if(!item.isEmpty())
		//		{
		//
		//		}
		return null;
	}

}
