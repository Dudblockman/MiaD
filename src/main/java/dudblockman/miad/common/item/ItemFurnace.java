package dudblockman.miad.common.item;

import dudblockman.miad.MiaD;
import dudblockman.miad.util.IHasRecipe;
import net.minecraft.block.BlockFurnace;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFurnace extends ItemBase implements IHasRecipe {

    protected int furnaceBurnTime;
    protected int currentItemBurnTime;
    protected int cookTime;
    protected int totalCookTime;
    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    
	public ItemFurnace(String name) {
		super(name);
		setMaxStackSize(1);
		setNoRepair();
	    furnaceBurnTime = 0;
	    currentItemBurnTime = 0;
	    cookTime = 0;
	    totalCookTime = 200;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) player.openGui(MiaD.instance, 1, world, 0, hand.ordinal(), 0);
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void initRecipes(Register<IRecipe> e) {
		GameRegistry.addShapedRecipe(getRegistryName(), null, new ItemStack(this), "ddd", "dgd", "ddd", 'd', Blocks.FURNACE, 'g', Blocks.CHEST);
	}    

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
		return 1.0-(this.cookTime / 200);
    }
	
	public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }
	
	private boolean canSmelt()
    {
        if (((ItemStack)this.furnaceItemStacks.get(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(0));

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.furnaceItemStacks.get(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= 64 && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }
	
	private void smeltItem() {
        if (this.canSmelt())
        {
            ItemStack itemstack = this.furnaceItemStacks.get(0);
            ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
            ItemStack itemstack2 = this.furnaceItemStacks.get(2);

            if (itemstack2.isEmpty())
            {
                this.furnaceItemStacks.set(2, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }

            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !((ItemStack)this.furnaceItemStacks.get(1)).isEmpty() && ((ItemStack)this.furnaceItemStacks.get(1)).getItem() == Items.BUCKET)
            {
                this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		//FurnaceRecipes.instance().getSmeltingResult(stack);
		if (stack.hasTagCompound()) {
			this.readFromNBT(stack.getTagCompound());

	        boolean flag = this.isBurning();
	        boolean flag1 = false;
	
	        if (this.isBurning())
	        {
	            --this.furnaceBurnTime;
	        }
	
	        if (!world.isRemote)
	        {
	            ItemStack itemstack = furnaceItemStacks.get(1);
	
	            if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)this.furnaceItemStacks.get(0)).isEmpty())
	            {
	                if (!this.isBurning() && this.canSmelt())
	                {

                    	this.furnaceBurnTime = getItemBurnTime(itemstack);
	                    if (this.furnaceBurnTime == -1) {
	                    	this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(itemstack);
	                    }
	                    this.currentItemBurnTime = this.furnaceBurnTime;
	
	                    if (this.isBurning())
	                    {
	                        flag1 = true;
	
	                        if (!itemstack.isEmpty())
	                        {
	                            Item item = itemstack.getItem();
	                            itemstack.shrink(1);
	
	                            if (itemstack.isEmpty())
	                            {
	                                ItemStack item1 = item.getContainerItem(itemstack);
	                                this.furnaceItemStacks.set(1, item1);
	                            }
	                        }
	                    }
	                }
	
	                if (this.isBurning() && this.canSmelt())
	                {
	                    ++this.cookTime;
	
	                    if (this.cookTime == this.totalCookTime)
	                    {
	                        this.cookTime = 0;
	                        this.totalCookTime = 200;
	                        this.smeltItem();
	                        flag1 = true;
	                    }
	                }
	                else
	                {
	                    this.cookTime = 0;
	                }
	            }
	            else if (!this.isBurning() && this.cookTime > 0)
	            {
	                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
	            }
	
	            if (flag != this.isBurning())
	            {
	                flag1 = true;
	            }
	        }
	
	        //if (flag1)
	        //{
	        	stack.setTagCompound(writeToNBT(new NBTTagCompound()));
	        //}
		} else {
        	stack.setTagCompound(writeToNBT(new NBTTagCompound()));
		}
    }
	
	public void readFromNBT(NBTTagCompound compound)
	{
	    //super.readFromNBT(compound);
	    this.furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);

		NBTTagCompound tag = compound.getCompoundTag("inv");
		for (int i = 0; i < 3; i++) {
			this.furnaceItemStacks.set(i, new ItemStack(tag.getCompoundTag("item_" + i)));
		}
	    ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
	    this.furnaceBurnTime = compound.getInteger("BurnTime");
	    this.cookTime = compound.getInteger("CookTime");
	    //this.totalCookTime = compound.getInteger("CookTimeTotal");
	    this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));
	
	    //if (compound.hasKey("CustomName", 8))
	    //{
	    //    this.furnaceCustomName = compound.getString("CustomName");
	    //}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
	    //super.writeToNBT(compound);
		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < 3; i++) {
			nbt.setTag("item_" + i, this.furnaceItemStacks.get(i).serializeNBT());
		}
		compound.setTag("inv", nbt);
	    compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
	    compound.setInteger("CookTime", (short)this.cookTime);
	    //compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
	    //ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);
	
	    //if (this.hasCustomName())
	    //{
	    //    compound.setString("CustomName", this.furnaceCustomName);
	    //}
	
	    return compound;
	}
}