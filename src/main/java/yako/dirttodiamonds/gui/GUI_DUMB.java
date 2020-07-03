package yako.dirttodiamonds.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yako.dirttodiamonds.DirtToDiamonds;
import yako.dirttodiamonds.container.ContainerDUMB;
import yako.dirttodiamonds.init.Config;
import yako.dirttodiamonds.tile.TileDUMB;

@SideOnly(Side.CLIENT)
public class GUI_DUMB extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	private TileDUMB te;

	private static final ResourceLocation background = new ResourceLocation(DirtToDiamonds.MODID,
			"textures/gui/container/dumb.png");

	public GUI_DUMB(TileDUMB tileEntity, ContainerDUMB container) {
		super(container);

		this.te = tileEntity;

		xSize = WIDTH;
		ySize = HEIGHT;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + 79, guiTop + 35, 176, 14, getScaledProgress(), 17);

		if (te.ilit == 1) {

			drawTexturedModalRect(guiLeft + 83, guiTop + 19, 176, 0, 14, 14);

		}
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//Never let me write GUI code again

		this.fontRenderer.drawString("D.U.M.B",8 , 4, 4210752); 
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	private int getScaledProgress() {

		int i = te.getProgress();
		int j = Config.TOTAL_PROGRESS_TIME;

		return (j != 0 ? i * 24 / j : 0);

	}
	
}
