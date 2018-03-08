package dudblockman.miad.util;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;

public interface IHasRecipe {

	void initRecipes(Register<IRecipe> e);

}
