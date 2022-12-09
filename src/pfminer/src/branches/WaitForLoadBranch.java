package pfminer.src.branches;

import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import pfminer.src.framework.Branch;

public class WaitForLoadBranch extends Branch {

    @Override
    public boolean isValid() {
        return  Client.getGameState() == GameState.LOADING ||
                Client.getGameState() == GameState.GAME_LOADING ||
                Client.getGameState() != GameState.LOGGED_IN;
    };

}
