package yako.dirttodiamonds;

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yako.dirttodiamonds.init.Config;
import yako.dirttodiamonds.proxy.CommonProxy;

@Mod(modid = DirtToDiamonds.MODID, name = DirtToDiamonds.NAME, version = DirtToDiamonds.VERSION)
public class DirtToDiamonds {
	public static final String MODID = "ydtd";
	public static final String NAME = "Yako's Dirt to Diamonds";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "yako.dirttodiamonds.proxy.ClientProxy", serverSide = "yako.dirttodiamonds.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Instance
	public static DirtToDiamonds instance;

	public static Logger logger;
	public static Configuration config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);

		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "ydtd.cfg"));
		Config.readConfig();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);

		if (config.hasChanged())
			config.save();
	}
}
