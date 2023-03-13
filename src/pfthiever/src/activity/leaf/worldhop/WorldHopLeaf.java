package pfthiever.src.activity.leaf.worldhop;

import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfthiever.src.activity.branch.worldhop.WorldHopBranch;

public class WorldHopLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !Client.getGameState().equals(GameState.LOADING) || !Client.getGameState().equals(GameState.GAME_LOADING);
    }

    @Override
    public int onLoop() {
        World hopWorld = Worlds.getRandomWorld(world -> world.isNormal() && world.isMembers() && world.getMinimumLevel() < Players.getLocal().getLevel());

        if (Players.all().size() > 1) {
            if (!WorldHopper.isWorldHopperOpen()) {
                WorldHopper.openWorldHopper();
            }
            if (WorldHopper.isWorldHopperOpen()) {
                if (WorldHopper.hopWorld(hopWorld)) {
                    Sleep.sleepUntil(Client::isLoggedIn, 6000, 100);
                }
            }
        }
        return 600;
    }
}
