package pfcows.src.action.branch.combat;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.wrappers.interactive.Player;
import pfcows.src.data.CowsConfig;
import pfcows.src.data.Location;

public class EnterCombat extends Branch {


    @Override
    public boolean isValid() {
        Player player = Players.getLocal();
        if (Skills.getBoostedLevel(Skill.HITPOINTS) >= 5 && !player.isInCombat()) {
            return true;
        }
        return false;
    }
}
