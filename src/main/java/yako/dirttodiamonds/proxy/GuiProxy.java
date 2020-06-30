package yako.dirttodiamonds.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import yako.dirttodiamonds.container.ContainerDUMB;
import yako.dirttodiamonds.gui.GUI_DUMB;
import yako.dirttodiamonds.tile.TileDUMB;

public class GuiProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

		switch (ID) {

		case 1:
			return new ContainerDUMB(player.inventory, (TileDUMB) te);

		default:
			return null;

		}

	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		switch (ID) {

		case 1:
			return new GUI_DUMB((TileDUMB) te, new ContainerDUMB(player.inventory, (TileDUMB) te));

		default:
			return null;

		}
	}

}
