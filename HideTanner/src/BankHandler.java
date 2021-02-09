import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;

/**
 * @author Clive on 9/21/2019
 */
public class BankHandler {

    private AbstractScript main;
    private Area alkharidBank = new Area(3269, 3161, 3271, 3170, 0);

    public BankHandler(AbstractScript main){
        this.main = main;
    }


    public void openBank(){
        MethodProvider.log(" ===== Opening Bank");
        NPC bank = main.getNpcs().closest(banker -> banker != null && banker.hasAction("Bank"));
        if(bank != null) bank.interact("Bank");
    }

    public void depositAll(){
        MethodProvider.log(" ==== Depositing all items");
        MethodProvider.sleepUntil(() -> main.getBank().isOpen(), 6000);
        main.getBank().depositAllExcept(coins -> coins != null && coins.getName().contains("Coins"));
    }

    public void withdrawHides() {
        //Get all your hides in the inventory to prep for tanning
        if(!main.getBank().contains("Cowhide")) main.stop();
        if(!main.getInventory().contains("Coins")) main.stop();

        main.getBank().withdrawAll(hides -> hides != null && hides.getName().contains("Cowhide"));
    }

    public boolean hideExistsInBank() {

        //TO-DO - FINISH THIS FUNCTION

        if(main.getBank().contains(hides -> hides != null && hides.getName().contains("Cowhide"))) {
            MethodProvider.log(" ==== we have more hides!");
            return true;
        } else {
            MethodProvider.log(" ==== we are out of hides!");
            return false;
        }

    }

    public void goToBank(){
        main.getWalking().walk(alkharidBank.getRandomTile());
    }

    public boolean insideBankingArea(Player currentPlayerLocation) {

        if (!alkharidBank.contains(currentPlayerLocation)) {
            goToBank();
            return false;
        } else {
            return true;
        }
    }


}
