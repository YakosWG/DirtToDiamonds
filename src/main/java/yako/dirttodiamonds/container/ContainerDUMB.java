package yako.dirttodiamonds.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import yako.dirttodiamonds.tile.TileDUMB;

public class ContainerDUMB extends Container {

	private TileDUMB te;
	private int progress;
	private int ilit;

	public ContainerDUMB(IInventory playerInventory, TileDUMB te) {

		this.te = te;

		addContainerSlots();
		addPlayerSlots(playerInventory, 8, 84);

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {

		return te.canInteractWith(playerIn);

	}

	private void addPlayerSlots(IInventory playerInventory, int offset_x, int offset_y) {

		// Slots for the hotbar
		for (int row = 0; row < 9; ++row) {
			int x = offset_x + row * 18;
			int y = 58 + offset_y;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}

		// Slots for the main inventory
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = offset_x + col * 18;
				int y = row * 18 + offset_y;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
			}
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 1, te.PROGRESS);
		listener.sendWindowProperty(this, 2, te.ilit);
	}

	private void addContainerSlots() {

		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 56, 35));
		addSlotToContainer(new SlotItemHandler(itemHandler, 1, 116, 35));

	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.progress != this.te.getProgress()) {
				icontainerlistener.sendWindowProperty(this, 1, this.te.getProgress());
			}

			if (this.ilit != te.ilit) {

				icontainerlistener.sendWindowProperty(this, 2, te.ilit);
				
			}

		}

		this.progress = this.te.getProgress();
		this.ilit = this.te.ilit;

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		if (id == 1)
			this.te.setProgress(data);
		if (id == 2)
			this.te.ilit = data;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0 || index == 1) {

				if (!this.mergeItemStack(itemstack1, 2, this.getInventory().size(), true)) {

					return ItemStack.EMPTY;

				}

			} else if (index >= 0 + TileDUMB.SIZE && index <= 8 + TileDUMB.SIZE) {

				if (this.mergeItemStack(itemstack1, 0, 1, false)) {

					return itemstack;

				}

				if (!this.mergeItemStack(itemstack1, 9 + TileDUMB.SIZE, 36 + TileDUMB.SIZE, false)) {

					return ItemStack.EMPTY;
				}

			} else if (index >= 9 + TileDUMB.SIZE && index <= 35 + TileDUMB.SIZE) {

				if (this.mergeItemStack(itemstack1, 0, 1, false)) {

					return itemstack;

				}

				if (!this.mergeItemStack(itemstack1, 0 + TileDUMB.SIZE, 9 + TileDUMB.SIZE, false)) {

					return ItemStack.EMPTY;
				}

			} else if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}

		}

		slot.onSlotChanged();
		return itemstack;
	}

}
