package dudblockman.miad.common.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class InventoryHeldFurnace {

	private ItemStack item;
	private NBTTagCompound itemNBT;
	
	public InventoryHeldFurnace( ItemStack stack ) {
		item = stack;
		itemNBT = stack.getTagCompound();
	}
	public void close() {
		item.setTagCompound(itemNBT);
	}
    
    private int getItemBurnTime(ItemStack itemstack) {
        return TileEntityFurnace.getItemBurnTime(itemstack);
    }
    
	public boolean isBurning() {
        return getFurnaceBurnTime() > 0;
    }
	
	private boolean canSmelt()
    {
        if (((ItemStack)getItem(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(getItem(0));

            if (smeltingResult.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = getItem(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(smeltingResult))
                {
                    return false;
                }
                else if (itemstack1.getCount() + smeltingResult.getCount() <= 64 && itemstack1.getCount() + smeltingResult.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + smeltingResult.getCount() <= smeltingResult.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }
	
	private void smeltItem() {
        if (this.canSmelt())
        {
            ItemStack itemstack = getItem(0);
            ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
            ItemStack itemstack2 = getItem(2);

            if (itemstack2.isEmpty())
            {
            	setItem(2, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
                setItem(2, itemstack2);
            }

            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !((ItemStack)getItem(1)).isEmpty() && ((ItemStack)getItem(1)).getItem() == Items.BUCKET)
            {
            	setItem(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            setItem(0, itemstack);
        }
	}
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		boolean flag = this.isBurning();
	    boolean flag1 = false;
	
	    if (this.isBurning())
	    {
	    	decrementFurnaceBurnTime();
	    }
	
	    if (!world.isRemote)
	    {
	        ItemStack itemstack = getItem(1);
	
	        if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)getItem(0)).isEmpty())
	        {
	            if (!this.isBurning() && this.canSmelt())
	            {
	
	            	setFurnaceBurnTime(getItemBurnTime(itemstack));
	            	setCurrentItemBurnTime(getFurnaceBurnTime());
	
	                if (this.isBurning())
	                {
	                    flag1 = true;
	
	                    if (!itemstack.isEmpty())
	                    {
	                        Item item = itemstack.getItem();
	                        itemstack.shrink(1);
	
	                        if (!itemstack.isEmpty()) {
	                        	setItem(1, itemstack);
	                        } else {
	                            ItemStack item1 = item.getContainerItem(itemstack);
	                            setItem(1, item1);
	                        }
	                    }
	                }
	            }
	
	            if (this.isBurning() && this.canSmelt())
	            {
	            	incrementCookTime();
	
	                if (getCookTime() == getTotalCookTime())
	                {
	                	setCookTime(0);
	                    this.smeltItem();
	                }
	            }
	            else
	            {
	            	setCookTime(0);
	            }
	        }
	        else if (!this.isBurning() && getCookTime() > 0)
	        {
	        	setCookTime(MathHelper.clamp(getCookTime() - 2, 0, getTotalCookTime()));
	        }
	    }
	}

	//Getters
	public ItemStack getItem(int slot) {
		return  new ItemStack(itemNBT.getCompoundTag("inv").getCompoundTag("item_" + slot));
	}
	public int getFurnaceBurnTime() {
		return itemNBT.getInteger("BurnTime");
	}
	public int getCookTime() {
		
		return itemNBT.getInteger("CookTime");
	}
	public int getTotalCookTime() {
		return itemNBT.getInteger("TotalCookTime");
	}
	public int getCurrentItemBurnTime() {
		return itemNBT.getInteger("CurrentItemBurnTime");
	}
	
	//Setters
	public void setItem(int slot, ItemStack item) {
		itemNBT.getCompoundTag("inv").setTag("item_" + slot, item.serializeNBT());
	}
	
	public void setFurnaceBurnTime(int val) {
		itemNBT.setShort("BurnTime", (short)val);
	}
	
	public void decrementFurnaceBurnTime() {
		setFurnaceBurnTime(getFurnaceBurnTime()-1);
	}
	
	public void setCookTime(int val) {
		itemNBT.setShort("CookTime", (short)val);
	}
	
	public void incrementCookTime() {
		setCookTime(getCookTime()+1);
	}
	
	public void setTotalCookTime(int val) {
		itemNBT.setShort("TotalCookTime", (short)val);
	}
	
	public void setCurrentItemBurnTime(int val) {
		itemNBT.setShort("CurrentItemBurnTime", (short)val);
	}
}
