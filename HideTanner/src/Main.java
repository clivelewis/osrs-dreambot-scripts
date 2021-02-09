import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;

/**
 * @author Clive on 9/19/2019
 */

@ScriptManifest(author = "CliveLewis", category = Category.CRAFTING, name = "Soft Hide Tanner", version = 1.0)
public class Main extends AbstractScript {
    private BankHandler bankHandler;
    private TannerHandler tannerHandler;
    private CurrentStatus currentStatus;

    @Override
    public void onStart() {
        super.onStart();
        bankHandler = new BankHandler(this);
        tannerHandler = new TannerHandler(this);
        currentStatus = CurrentStatus.INITIALIZING;
    }

    @Override
    public int onLoop() {
        switch (currentStatus){
            case INITIALIZING:
                boolean insideBank = bankHandler.insideBankingArea(getLocalPlayer());
                if(insideBank) currentStatus = CurrentStatus.BANKING;
                break;
            case WALKING:
                handleWalking();
                break;
            case BANKING:
                handleBanking();
                break;
            case TANNING:
                handleTanning();
                break;
        }

        if (currentStatus == CurrentStatus.WALKING || currentStatus == CurrentStatus.INITIALIZING) {
            return Calculations.random(1000, 5000);
        } else {
            return Calculations.random(1000, 3000);
        }
    }

    private void handleBanking() {

        bankHandler.openBank();
        sleepUntil(() -> getBank().isOpen(), 3500);

        if (!bankHandler.hideExistsInBank()){
            stop();
        }

        bankHandler.depositAll();
        sleep(3000);
        bankHandler.withdrawHides();
        sleep(2000);

        currentStatus = CurrentStatus.WALKING;


    }

    private void handleWalking() {

        //Travel to tanner
        tannerHandler.goToTanner();

        NPC desertTanner = getNpcs().closest(tanner -> tanner != null && tanner.hasAction("Trade"));

//        tanner.handleDoorOutside(tannerArea);

        //Ensure the tanner is present and the player is in the tanning area
        if (desertTanner != null && desertTanner.isOnScreen() && tannerHandler.insideTanningArea(getLocalPlayer())) {
            currentStatus = CurrentStatus.TANNING;
        }
    }
    private void handleTanning() {

        tannerHandler.initiateTrade(124);
        sleep(3000);
        tannerHandler.tanAllHides(124);

        //See if the process completed successfully
        if (tannerHandler.checkHidesTanned()) {

            log(" ==== All hides tanned sucessfully!");

            //            if (tannerHandler.handleDoorInside()) {
//
//                log("Handled the door and exited the tanner.");
//
//                //Restart the work-flow
//                currentStatus = CurrentStatus.INITIALIZING;
//            }
            currentStatus = CurrentStatus.INITIALIZING;
        }
    }
}


