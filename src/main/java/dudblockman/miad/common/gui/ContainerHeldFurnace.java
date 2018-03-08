package dudblockman.miad.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerHeldFurnace extends ContainerFurnace {

	EnumHand hand;
	IInventory furnaceInventory;
    
	public ContainerHeldFurnace(EntityPlayer player, World world, EnumHand hand, IInventory newInventory) {
		super(player.inventory, newInventory);
		this.furnaceInventory = newInventory;
		this.hand = hand;
		ItemStack stack = player.getHeldItem(hand);
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("inv");
			for (int i = 0; i < 3; i++) {
				this.furnaceInventory.setInventorySlotContents(i, new ItemStack(tag.getCompoundTag("item_" + i)));
			}
		}
	}
	public ContainerHeldFurnace(EntityPlayer player, World world, EnumHand hand) {
		this(player, world, hand, new InventoryBasic("Furnace", false, 3));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < 3; i++) {
			nbt.setTag("item_" + i, furnaceInventory.getStackInSlot(i).serializeNBT());
		}
		stack.getTagCompound().setTag("inv", nbt);
	}

}
