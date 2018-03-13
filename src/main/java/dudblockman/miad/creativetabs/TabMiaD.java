package dudblockman.miad.creativetabs;

import dudblockman.miad.init.BlockInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabMiaD extends CreativeTabs {

	public TabMiaD() {
		super("miad");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockInit.MyBlock));
	}

}
