package pfenchanter.src.activity.stopping;

import org.dreambot.api.methods.skills.Skill;

import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;

import pfenchanter.src.data.EnchanterConfig;
import pfenchanter.src.framework.Leaf;

public class StopScriptLeaf extends Leaf {

    EnchanterConfig ec = EnchanterConfig.getInstance();

    @Override
    public boolean isValid() {
        if (Skill.MAGIC.getLevel() == ec.getStopAtLevel()) { return true; }
        return false;
    }

    @Override
    public int onLoop() {
        if (Skill.MAGIC.getLevel() == ec.getStopAtLevel()) {
            Logger.log("Stopping script - reached desired Magic level");
            ScriptManager.getScriptManager().stop();
        }
        return 600;
    }
}
