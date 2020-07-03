package yako.dirttodiamonds.util.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import yako.dirttodiamonds.DirtToDiamonds;
import yako.dirttodiamonds.gui.GUI_DUMB;
import yako.dirttodiamonds.init.ModBlocks;
import yako.dirttodiamonds.util.compat.jei.CategoryDUMB.RecipeWrapperDUMB;

@JEIPlugin
public class JeiCompat implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {

		final IJeiHelpers jeihelpers = registry.getJeiHelpers();

		registry.addRecipes(RecipeWrapperDUMB.getRecipes(jeihelpers), DirtToDiamonds.MODID + ".dumb");
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.DUMB, 1, 0), DirtToDiamonds.MODID + ".dumb");
		
		registry.addRecipeClickArea(GUI_DUMB.class, 79, 35, 24, 17, DirtToDiamonds.MODID + ".dumb");

	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		final IJeiHelpers helpers = registry.getJeiHelpers();
		final IGuiHelper guihelper = helpers.getGuiHelper();

		registry.addRecipeCategories(new CategoryDUMB(guihelper));

	}

}
