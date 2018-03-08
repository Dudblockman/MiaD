package dudblockman.miad.common.items;

import javax.annotation.Nonnull;

import dudblockman.miad.MiaD;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ItemCrafting extends ItemBase implements IItemGUI {

	public ItemCrafting(String name) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(1600);
		setNoRepair();
		setCreativeTab(CreativeTabs.TOOLS);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		//itemstack.damageItem(80, player);
		if (!world.isRemote) {
			EntityEquipmentSlot slot = hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND;
			System.out.println("Alpha");
			player.openGui(MiaD.instance, 100 * slot.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	}

	@Override
	public int getGuiID(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setContainedItems(ItemStack stack, NonNullList<ItemStack> inventory) {
		IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if (handler instanceof IItemHandlerModifiable) {
			if (inventory.size() != handler.getSlots()) throw new IllegalArgumentException("Parameter inventory has " + inventory.size() + " slots, capability inventory has " + handler.getSlots());
			for (int i = 0; i < handler.getSlots(); i++)
				((IItemHandlerModifiable) handler).setStackInSlot(i, inventory.get(i));
		}
		//else
		//	IELogger.warn("No valid inventory handler found for "+stack);
	}

	public NonNullList<ItemStack> getContainedItems(ItemStack stack) {
		IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if (handler != null) {
			NonNullList<ItemStack> inv = NonNullList.withSize(handler.getSlots(), ItemStack.EMPTY);
			for (int i = 0; i < handler.getSlots(); i++)
				inv.set(i, handler.getStackInSlot(i));
			return inv;
		}
		//else
		//	IELogger.info("No valid inventory handler found for "+stack);
		return NonNullList.create();
	}

	//	@Override
	//	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	//	{
	//		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	//		//Update old inventories to caps
	//		if (ItemNBTHelper.hasKey(stack, "Inv"))
	//		{
	//			NBTTagList list = ItemNBTHelper.getTag(stack).getTagList("Inv", 10);
	//			setContainedItems(stack, Utils.readInventory(list, getSlotCount(stack)));
	//			ItemNBTHelper.remove(stack, "Inv");
	//			//Sync the changes
	//			if (entityIn instanceof EntityPlayerMP && !worldIn.isRemote)
	//				((EntityPlayerMP) entityIn).connection.sendPacket(new SPacketSetSlot(-2, itemSlot, stack));
	//		}
	//	}
}
