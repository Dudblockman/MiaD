package dudblockman.miad.common.item;

import dudblockman.miad.MiaD;
import dudblockman.miad.common.inventory.InventoryHeldFurnace;
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

	public ItemFurnace(String name) {
		super(name);
		setMaxStackSize(1);
		setNoRepair();
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
		if (stack.hasTagCompound())
			return 1.0-(stack.getTagCompound().getInteger("CookTime") / 200);
		return 1.0;
    }
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		InventoryHeldFurnace wrapper = new InventoryHeldFurnace(stack);
		wrapper.onUpdate(stack, world, entity, itemSlot, isSelected);
		wrapper.close();
    }
}