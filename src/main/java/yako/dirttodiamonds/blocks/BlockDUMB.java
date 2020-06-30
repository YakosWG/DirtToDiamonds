package yako.dirttodiamonds.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import yako.dirttodiamonds.DirtToDiamonds;
import yako.dirttodiamonds.tile.TileDUMB;

public class BlockDUMB extends Block implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LIT = PropertyBool.create("lit");
	public static final int GUI_ID = 1;

	public BlockDUMB() {
		super(Material.GROUND);
		setUnlocalizedName(DirtToDiamonds.MODID + ".dumb");
		setRegistryName(DirtToDiamonds.MODID, "dumb");
		
		setHarvestLevel("shovel", 0);
		setHardness(1.5f);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {

		if (worldIn.getBlockState(pos.down()).getBlock() == Block.getBlockFromName("diamond_block")) {

			IBlockState staterino = state.withProperty(LIT, true);

			worldIn.setBlockState(pos, staterino.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		} else
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

	}

	@Override
	public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock,
			BlockPos changedBlockPos) {

		if (world.getBlockState(observerPos.down()).getBlock() == Block.getBlockFromName("diamond_block")) {

			world.setBlockState(observerPos, observerState.withProperty(LIT, true));

		} else
			world.setBlockState(observerPos, observerState.withProperty(LIT, false));

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta & 7);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(LIT, (meta & 8) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex() + (state.getValue(LIT) ? 8 : 0);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, LIT);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDUMB();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileDUMB)) {
			return false;
		}
		player.openGui(DirtToDiamonds.instance, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileDUMB) {

			IItemHandler cap = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			for (int i = 0; i < cap.getSlots(); i++) {

				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), cap.getStackInSlot(i));

			}

		}

		super.breakBlock(worldIn, pos, state);
	}

}
