package dudblockman.miad.common.item;

import dudblockman.miad.MiaD;
import dudblockman.miad.init.ItemInit;
import dudblockman.miad.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MiaD.creativetab);
		ItemInit.ITEMS.add(this);
	}

}
