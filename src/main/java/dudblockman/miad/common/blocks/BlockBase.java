package dudblockman.miad.common.blocks;

import dudblockman.miad.MiaD;
import dudblockman.miad.common.util.IHasModel;
import dudblockman.miad.init.BlockInit;
import dudblockman.miad.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {
	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		MiaD.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
