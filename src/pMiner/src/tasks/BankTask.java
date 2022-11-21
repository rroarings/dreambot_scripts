package pMiner.src.tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.walking.pathfinding.impl.PathFinder;
import org.dreambot.api.methods.walking.pathfinding.impl.local.LocalPathFinder;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebPathNode;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pMiner.src.PMiner;

public class BankTask extends TaskNode {

    private final Area soulsBankArea = new Area(2210, 2860, 2214, 2857);
    private Tile bankTile = new Tile(2212, 2859, 0);
    private Tile quarryTile = new Tile(2193, 2797, 0);

    AbstractWebNode quarryNode = WebFinder.getWebFinder().getNearest(quarryTile, 15);
    AbstractWebNode bankNode = WebFinder.getWebFinder().getNearest(bankTile, 15);

    @Override
    public boolean accept() {
        return Inventory.isFull();
    }

    @Override
    public int execute() {

        if (Inventory.isFull()) {
               if (Walking.shouldWalk()) {
                   Walking.walk(bankTile);
                   PMiner.status = "walking to bank";
                   log("[info] walking to bank");
                   Sleep.sleepUntil(() -> soulsBankArea.contains(Players.getLocal()), 5000);
               }

        }

       if (soulsBankArea.contains(Players.getLocal())) {
            if (!Bank.isOpen()) {
                Bank.open();
                log("[info] open bank");
                PMiner.status = "open bank";
                Sleep.sleepUntil(Bank::isOpen, 2500);
            }
        }

        if (Bank.isOpen()) {
            Bank.depositAllExcept(item -> item.getName().contains(" pickaxe"));
            log("[info] deposit items");
            PMiner.status = "deposit items";
            Sleep.sleepUntil(() -> !Inventory.contains(" ore"), 2000);
            Bank.close();
        }

        return Calculations.random(600, 900);
    }
}
