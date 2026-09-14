package train.common.generation;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class ComponentVillageTrainstation extends StructureVillagePieces.Village {

	private int averageGroundLevel = -1;

	public ComponentVillageTrainstation() {}

	public ComponentVillageTrainstation(StructureVillagePieces.Start par1, int par2, Random par3Random, StructureBoundingBox par4, int par5) {
		super(par1, par2);
		this.boundingBox = par4;
	}

	public static ComponentVillageTrainstation buildComponent(StructureVillagePieces.Start start, List list, Random random, int x, int y, int z, int facing, int type) {
		EnumFacing ef = EnumFacing.byHorizontalIndex(facing);
		StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 9, 9, 10, ef);
		return canVillageGoDeeper(box) && StructureComponent.findIntersecting(list, box) == null ? new ComponentVillageTrainstation(start, type, random, box, facing) : null;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox box) {
		return true;
	}

	protected int getVillagerType(int par1) {
		return 86;
	}
}
