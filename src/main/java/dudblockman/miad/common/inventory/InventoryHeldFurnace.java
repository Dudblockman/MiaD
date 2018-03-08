package dudblockman.miad.common.inventory;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class InventoryHeldFurnace {
    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    public ItemStack stack;
	private int furnaceBurnTime;
	private int cookTime;
	private int totalCookTime;
	private Object currentItemBurnTime;
	private String furnaceCustomName;
	
	public InventoryHeldFurnace( ItemStack stack ) {
		
	}
	
    public void readFromNBT(NBTTagCompound compound)
    {
        this.furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        //this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));

    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);


        return compound;
    }
}
