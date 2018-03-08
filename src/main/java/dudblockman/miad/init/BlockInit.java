package dudblockman.miad.init;

import java.util.ArrayList;
import java.util.List;

import dudblockman.miad.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {

	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block MyBlock = new BlockBase("dudblock", Material.IRON);

}
