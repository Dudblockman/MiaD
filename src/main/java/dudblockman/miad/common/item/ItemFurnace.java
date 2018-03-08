package dudblockman.miad.common.item;

import dudblockman.miad.MiaD;
import dudblockman.miad.util.IHasRecipe;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFurnace extends ItemBase implements IHasRecipe {

	public ItemFurnace(String name) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(1600);
		setNoRepair();
		setCreativeTab(CreativeTabs.TOOLS);
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
}
