package yako.dirttodiamonds.init;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import yako.dirttodiamonds.DirtToDiamonds;

public class Config {

	private static final String CATEGORY_GENERAL = "general";

	public static int TOTAL_PROGRESS_TIME;
	public static List<String> ACCEPTED_BLOCKS = Arrays.asList(new String[] { "minecraft:dirt" });

	public static void readConfig() {
		Configuration cfg = DirtToDiamonds.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e1) {
			DirtToDiamonds.logger.log(Level.ERROR, "Problem loading config file!", e1);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");

		TOTAL_PROGRESS_TIME = cfg.getInt("TOTAL_PROGRESS_TIME", CATEGORY_GENERAL, 80, 1, (int) (Math.pow(2, 16) - 1),
				"Time the D.U.M.B needs to process one Dirt into one Diamond in ticks.");
		ACCEPTED_BLOCKS = Arrays
				.asList(cfg.getStringList("ACCEPTED_BLOCKS", CATEGORY_GENERAL, new String[] { "minecraft:dirt" },
						"List of Items that can be turned into Diamonds by the D.U.M.B (modid:Itemid)"));

	}

}
