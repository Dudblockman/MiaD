package dudblockman.miad.init;

import java.util.ArrayList;
import java.util.List;

import dudblockman.miad.common.item.*;
import net.minecraft.item.Item;

public class ItemInit {

	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item MyItem = new ItemBase("duditem");
	public static final Item PortCraft = new ItemCrafting("crafter");
	public static final Item PortFurnace = new ItemFurnace("smelter");
	public static final Item Cap = new ItemCapacitor("capacitor");
}
