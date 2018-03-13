package dudblockman.miad.jei;

import dudblockman.miad.common.gui.ContainerHeldBench;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@JEIPlugin
public class MiaDJEIPlugin implements IModPlugin {
	
	@Override
	public void register(IModRegistry r) {
		r.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerHeldBench.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
	}

}
