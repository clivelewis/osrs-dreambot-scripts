package main.nodes.antiban;

import main.Main;
import main.Node;
import main.Utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
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
                && !main.getLocalPlayer().isInCombat() && !main.getBank().isOpen()
                && main.getLocalPlayer().distance(main.getBank().getClosestBankLocation().getCenter()) > 10;
    }

    @Override
    public int execute() {
        main.setCurrentState(STATE.WORLD_CHANGE);

        MethodProvider.sleep(11000);
        main.getWorldHopper().hopWorld(main.getWorlds().getRandomWorld(world -> world != null && world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 100));

        worldHopTimer = new Timer();
        worldHopTimer.setRunTime(Calculations.random(10 * MINUTES, 15 * MINUTES));
        main.shouldHopWorld = false;

        return Calculations.random(5000, 10000);
    }

    public String getWorldHopTimeLeft(){
        return main.getUtils().timerToString(worldHopTimer);
    }


}
