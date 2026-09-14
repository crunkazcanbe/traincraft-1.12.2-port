package train.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import train.common.Traincraft;

public class BlockOrePetrol extends Block {

	private int tx;

	public BlockOrePetrol(int j) {
		super(Material.ROCK);
		tx = j;
		setCreativeTab(Traincraft.tcTab);
	}
}
