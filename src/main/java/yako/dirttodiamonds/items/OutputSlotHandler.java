package yako.dirttodiamonds.items;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class OutputSlotHandler extends ItemStackHandler {

	@Override
	@Nonnull
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

		return actualInsertItem(slot, stack, simulate, false);

	}

	@Nonnull
	public ItemStack actualInsertItem(int slot, @Nonnull ItemStack stack, boolean simulate, boolean special) {
		if (!special)
			return stack;

		return super.insertItem(slot, stack, simulate);
	}

}
