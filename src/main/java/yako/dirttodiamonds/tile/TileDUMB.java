package yako.dirttodiamonds.tile;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import yako.dirttodiamonds.blocks.BlockDUMB;
import yako.dirttodiamonds.init.Config;

public class TileDUMB extends TileEntity implements ITickable {

	public static final int SIZE = 2;

	public Boolean lit;
	public int ilit;
	public int PROGRESS = 0;

	protected ItemStackHandler inputSlot = new ItemStackHandler() {
		@Override
		protected void onContentsChanged(int slot) {
			TileDUMB.this.markDirty();
		}
	};
	protected ItemStackHandler outputSlot = new ItemStackHandler() {
		@Override
		protected void onContentsChanged(int slot) {
			TileDUMB.this.markDirty();
		}

	};

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

			if (world != null && world.getBlockState(pos).getBlock() != getBlockType()) {

				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
						.cast(new CombinedInvWrapper(inputSlot, outputSlot));
			}

			if (facing == EnumFacing.UP) {

				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputSlot);

			} else if (facing == null) {

				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
						.cast(new CombinedInvWrapper(inputSlot, outputSlot));

			}

			else
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputSlot);

		}
		return super.getCapability(capability, facing);
	}

	public int getProgress() {
		return this.PROGRESS;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("ydtd:input")) {
			inputSlot.deserializeNBT((NBTTagCompound) compound.getTag("ydtd:input"));
		}

		if (compound.hasKey("ydtd:output")) {
			outputSlot.deserializeNBT((NBTTagCompound) compound.getTag("ydtd:output"));
		}
		if (compound.hasKey("progress")) {

			this.PROGRESS = compound.getInteger("progress");

		}
	}

	public void setProgress(int data) {
		this.PROGRESS = data;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {

		if (oldState.getBlock() == newState.getBlock()) {

			return false;

		} else
			return true;

	}

	@Override
	public void update() {

		if (!getWorld().isRemote) {

			lit = getLitState(this);

			ilit = lit ? 1 : 0;

			if (lit) {

				if (PROGRESS > 0) {

					if (PROGRESS == Config.TOTAL_PROGRESS_TIME) {

						IItemHandler capability = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

						ItemStack istack = new ItemStack(Item.getByNameOrId("minecraft:diamond"), 1);

						if (capability.insertItem(1, istack, false).getCount() == 0) {
							PROGRESS = 0;
						}

						this.markDirty();
						return;

					} else {
						PROGRESS++;
						return;
					}

				} else {

					IItemHandler capability = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

					if (capability.getStackInSlot(0) != null && Config.ACCEPTED_BLOCKS
							.contains(capability.getStackInSlot(0).getItem().getRegistryName().toString())) {

						capability.extractItem(0, 1, false);

						PROGRESS++;

						this.markDirty();
						return;

					}

				}

			}
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("ydtd:input", inputSlot.serializeNBT());
		compound.setTag("ydtd:output", outputSlot.serializeNBT());
		compound.setInteger("progress", this.PROGRESS);
		return compound;
	}

	private Boolean getLitState(TileDUMB te) {

		ImmutableMap<IProperty<?>, Comparable<?>> map = te.getWorld().getBlockState(te.getPos()).getProperties();

		Comparable<?> comp = map.get(BlockDUMB.LIT);

		if (comp instanceof Boolean) {

			return (Boolean) comp;

		} else
			return false;

	}

}
