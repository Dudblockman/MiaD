package dudblockman.miad.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerHeldBench extends ContainerWorkbench {

	EnumHand hand;

	public ContainerHeldBench(EntityPlayer player, World world, EnumHand hand) {
		super(player.inventory, world, BlockPos.ORIGIN);
		this.hand = hand;
		ItemStack stack = player.getHeldItem(hand);
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("inv");
			for (int i = 0; i < 9; i++) {
				this.craftMatrix.setInventorySlotContents(i, new ItemStack(tag.getCompoundTag("item_" + i)));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	protected void clearContainer(EntityPlayer player, World world, IInventory inv) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < 9; i++) {
			nbt.setTag("item_" + i, inv.getStackInSlot(i).serializeNBT());
		}
		stack.getTagCompound().setTag("inv", nbt);
	}

}
