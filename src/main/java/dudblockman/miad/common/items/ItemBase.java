package dudblockman.miad.common.items;

import dudblockman.miad.MiaD;
import dudblockman.miad.common.util.IHasModel;
import dudblockman.miad.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MISC);
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		MiaD.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
