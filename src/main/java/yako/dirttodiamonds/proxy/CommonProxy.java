package yako.dirttodiamonds.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yako.dirttodiamonds.DirtToDiamonds;
import yako.dirttodiamonds.blocks.BlockDUMB;
import yako.dirttodiamonds.init.ModBlocks;
import yako.dirttodiamonds.tile.TileDUMB;

@EventBusSubscriber
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		
	}
	
	public void init(FMLInitializationEvent e) {
		
		NetworkRegistry.INSTANCE.registerGuiHandler(DirtToDiamonds.instance, new GuiProxy());
		
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
		
	}
	
	@SubscribeEvent
	public static void registerItems(Register<Item> e) {
		
		final Item[] items = {};
		final Item[] itemBlocks = {new ItemBlock(ModBlocks.DUMB).setRegistryName(ModBlocks.DUMB.getRegistryName())};
		
		e.getRegistry().registerAll(items);
		e.getRegistry().registerAll(itemBlocks);
		
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void registerBlocks(Register<Block> e) {
		
		final Block[] blocks = {new BlockDUMB()};
		
		e.getRegistry().registerAll(blocks);
		GameRegistry.registerTileEntity(TileDUMB.class, DirtToDiamonds.MODID + "_tileDumb");
		
	}
	



}
