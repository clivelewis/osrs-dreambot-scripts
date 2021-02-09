package main.nodes.antiban;

import main.Main;
import main.Node;
import main.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Timer;

/**
 * @author Clive on 9/26/2019
 */
public class WorldHopperNode extends Node {
    private Timer worldHopTimer;
    private final long MINUTES = 60000;

    public WorldHopperNode(Main main) {
        super(main);
        worldHopTimer = new Timer();
        worldHopTimer.setRunTime(30 * MINUTES);
    }

    @Override
    public boolean validate() {
        return (worldHopTimer.finished() ||  main.shouldHopWorld) && main.getState() != STATE.LONG_BREAK
                && !main.getLocalPlayer().isInCombat() && !Bank.isOpen();
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.WORLD_CHANGE);
        MethodProvider.sleep(1500);

        WorldHopper.hopWorld(Worlds.getRandomWorld(world -> world != null && world.isF2P() && !world.isTournamentWorld() && world.getWorld() != 400 && !world.isPVP() && world.getMinimumLevel() < 100));

        worldHopTimer = new Timer();
        worldHopTimer.setRunTime(Calculations.random(25 * MINUTES, 35 * MINUTES));
        main.shouldHopWorld = false;

        return Calculations.random(5000, 10000);
    }

    public String getWorldHopTimeLeft(){
        return main.getUtils().timerToString(worldHopTimer);
    }


}
