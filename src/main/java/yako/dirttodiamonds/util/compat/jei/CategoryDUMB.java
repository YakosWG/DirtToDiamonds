package yako.dirttodiamonds.util.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableBuilder;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import yako.dirttodiamonds.DirtToDiamonds;
import yako.dirttodiamonds.init.Config;

public class CategoryDUMB implements IRecipeCategory<IRecipeWrapper> {

	protected static final int input = 0;
	protected static final int output = 1;
	public static String UID = DirtToDiamonds.MODID + ".dumb";

	protected static final ResourceLocation textures = new ResourceLocation(DirtToDiamonds.MODID,
			"textures/gui/container/dumb.png");

	protected final IDrawableAnimated animatedArrow;
	protected final IDrawable backround;
	protected final IDrawable staticFlame;
	protected final String name;

	public static class RecipeWrapperDUMB implements IRecipeWrapper {

		private final ItemStack recipeInput;
		private final ItemStack recipeOutput;

		public RecipeWrapperDUMB(ItemStack recipeInput, ItemStack recipeOutput) {

			this.recipeInput = recipeInput;
			this.recipeOutput = recipeOutput;

		}

		@Override
		public void getIngredients(IIngredients ingredients) {

			ingredients.setInput(VanillaTypes.ITEM, recipeInput);
			ingredients.setOutput(VanillaTypes.ITEM, recipeOutput);

		}

		@Override
		public List<String> getTooltipStrings(int mouseX, int mouseY) {

			if (34 <= mouseX && mouseX < 34 + 24 && 19 <= mouseY && mouseY < 19 + 17) {

				List<String> col = new ArrayList<String>();
				col.add(Config.TOTAL_PROGRESS_TIME + " Ticks");

				return col;

			} else
				return IRecipeWrapper.super.getTooltipStrings(mouseX, mouseY);

		}

		public static List<RecipeWrapperDUMB> getRecipes(IJeiHelpers jeihelpers) {

			List<String> accptInputs = Config.ACCEPTED_BLOCKS;
			List<RecipeWrapperDUMB> jeiRecipes = Lists.newArrayList();

			for (String s : accptInputs) {

				if (Item.getByNameOrId(s) != null) {

					jeiRecipes.add(
							new RecipeWrapperDUMB(new ItemStack(Item.getByNameOrId(s), 1, OreDictionary.WILDCARD_VALUE),
									new ItemStack(Items.DIAMOND)));

				}

			}

			return jeiRecipes;

		}

	}

	public CategoryDUMB(IGuiHelper guihelper) {

		IDrawableBuilder staticArrow = guihelper.drawableBuilder(textures, 176, 14, 24, 17);
		this.animatedArrow = staticArrow.buildAnimated(Config.TOTAL_PROGRESS_TIME, StartDirection.LEFT, false);

		this.staticFlame = guihelper.drawableBuilder(textures, 176, 0, 14, 14).build();

		backround = guihelper.drawableBuilder(textures, 45, 15, 100, 50).build();
		name = "D.U.M.B";

	}

	@Override
	public String getUid() {
		return CategoryDUMB.UID;
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public String getModName() {
		return DirtToDiamonds.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return backround;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

		stacks.init(input, true, 55 - 45, 34 - 15);
		stacks.init(output, false, 115 - 45, 34 - 15);
		stacks.set(ingredients);

	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedArrow.draw(minecraft, 34, 19);
		staticFlame.draw(minecraft, 82 - 45, 18 - 15);
	}

}
