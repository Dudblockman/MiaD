package dudblockman.miad.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IHasModel {

	default public void initModels(ModelRegistryEvent e) {
		ResourceLocation name = ((IForgeRegistryEntry<?>) this).getRegistryName();
		if (this instanceof Item) ModelLoader.setCustomModelResourceLocation((Item) this, 0, new ModelResourceLocation(name, "inventory"));
		else if (this instanceof Block) ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) this), 0, new ModelResourceLocation(name, "inventory"));
		else throw new IllegalStateException("wat are u doin");
	}

}
