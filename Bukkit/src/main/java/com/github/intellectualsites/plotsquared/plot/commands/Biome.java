package com.github.intellectualsites.plotsquared.plot.commands;

import com.github.intellectualsites.plotsquared.commands.CommandDeclaration;
import com.github.intellectualsites.plotsquared.plot.config.C;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.util.MainUtil;
import com.github.intellectualsites.plotsquared.plot.util.StringMan;
import com.github.intellectualsites.plotsquared.plot.util.WorldUtil;

@CommandDeclaration(command = "setbiome", permission = "plots.set.biome",
    description = "Set the plot biome", usage = "/plot biome [biome]",
    aliases = {"biome", "sb", "setb", "b"}, category = CommandCategory.APPEARANCE,
    requiredType = RequiredType.NONE) public class Biome extends SetCommand {

    @Override public boolean set(final PlotPlayer player, final Plot plot, final String value) {
        int biome = WorldUtil.IMP.getBiomeFromString(value);
        if (biome == -1) {
            String biomes =
                StringMan.join(WorldUtil.IMP.getBiomeList(), C.BLOCK_LIST_SEPARATER.s());
            C.NEED_BIOME.send(player);
            MainUtil.sendMessage(player, C.SUBCOMMAND_SET_OPTIONS_HEADER.s() + biomes);
            return false;
        }
        if (plot.getRunning() > 0) {
            MainUtil.sendMessage(player, C.WAIT_FOR_TIMER);
            return false;
        }
        plot.addRunning();
        plot.setBiome(value.toUpperCase(), new Runnable() {
            @Override public void run() {
                plot.removeRunning();
                MainUtil.sendMessage(player, C.BIOME_SET_TO.s() + value.toLowerCase());
            }
        });
        return true;
    }
}
