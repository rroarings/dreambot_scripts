package pfthiever.src.activity.branch.worldhop;

import org.dreambot.api.Client;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class WorldHopBranch extends Branch {

    @Override
    public boolean isValid() {
        return Players.all().size() > 1 && Client.isLoggedIn();
    }
}
