package pfcows.src;

import org.dreambot.api.Client;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import pfcows.src.action.branch.bank.DepositLootBranch;
import pfcows.src.action.branch.loot.LootHidesBranch;
import pfcows.src.action.branch.traversal.WalkEastCowsBranch;
import pfcows.src.action.branch.traversal.WalkLumbridgeBankBranch;
import pfcows.src.action.leaf.bank.DepositLootLeaf;
import pfcows.src.action.leaf.combat.LootCow;
import pfcows.src.action.leaf.traversal.WalkEastCowsLeaf;
import pfcows.src.action.leaf.traversal.WalkLumbridgeBankLeaf;
import pfcows.src.data.CowsConfig;
import pfcows.src.paint.PaintHandler;
import pfcows.src.util.PricedItem;

import java.awt.*;

@ScriptManifest(category = Category.COMBAT, name = "PF AIO Cows", author = "pharaoh", version = 1.1)
public class PFCows extends TreeScript {

    private long startTime;

    private PaintHandler paintHandler;

    public long getStartTime() {
        return startTime;
    }

    public int getStartingAttackExp() {
        return startingAttackExp;
    }

    public int getStartingStrengthExp() {
        return startingStrengthExp;
    }

    private int startingAttackExp;
    private int startingStrengthExp;

    @Override
    public void onStart() {
        paintHandler = new PaintHandler(this);
        Client.getCanvas().addMouseListener(paintHandler);
        startTime = System.currentTimeMillis();
        addBranches(
                new WalkEastCowsBranch().addLeaves(new WalkEastCowsLeaf()),
                new LootHidesBranch().addLeaves(new LootCow()),
                new WalkLumbridgeBankBranch().addLeaves(new WalkLumbridgeBankLeaf()),
                new DepositLootBranch().addLeaves(new DepositLootLeaf())
        );

        if (Client.isLoggedIn()) {
            SkillTracker.start(Skill.ATTACK, Skill.HITPOINTS, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC);
            startingAttackExp = SkillTracker.getStartExperience(Skill.ATTACK);
            startingStrengthExp = SkillTracker.getStartExperience(Skill.STRENGTH);
            CowsConfig.getCowsConfig().setPricedItem(new PricedItem("Cowhide", true));
        }
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onPaint(Graphics g) {
        paintHandler.paint(g);
    }
}
