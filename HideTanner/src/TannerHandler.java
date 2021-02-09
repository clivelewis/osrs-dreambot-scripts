import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;

/**
 * @author Clive on 9/21/2019
 */
public class TannerHandler {

    private AbstractScript main;
    private Area tannerArea = new Area(3271, 3189, 3275, 3193, 0);


    public TannerHandler(AbstractScript main) {
        this.main = main;
    }

    public boolean checkHidesTanned() {

        if (main.getInventory().contains(hides -> hides != null && hides.getName().contains("Leather"))) {
            MethodProvider.log(" ==== The hides were tanned!");
            return true;
        } else {
            MethodProvider.log(" ==== The hides were NOT tanned!");
            return false;
        }
    }
    public void initiateTrade(int child_widget_int) {

        //Get the desert tanner dude
        //TO:DO add this into a try-catch block
        NPC desertTanner = null;
        if (main.getNpcs().closest(tanner -> tanner != null && tanner.hasAction("Trade") && tanner.getName().contains("Ellis")) != null) {
            desertTanner = main.getNpcs().closest(tanner -> tanner != null && tanner.hasAction("Trade") && tanner.getName().contains("Ellis"));
        }

        //Tan Tanner
        if (desertTanner != null) {
            try {
                MethodProvider.log(" ==== Attempting to trade");
                desertTanner.interact("Trade");
            } catch (Exception e) {
                MethodProvider.log(e.toString());
            }
        } else {
            MethodProvider.log(" ==== Tanner is null ERROR");
        }
    }

    public void tanAllHides(int child_widget_int) {

        MethodProvider.sleepUntil(() -> main.getWidgets().getWidgetChild(324, child_widget_int).isVisible(), 1000);

        if (main.getWidgets().getWidgetChild(324, child_widget_int) !=null){
            main.getWidgets().getWidgetChild(324, child_widget_int).interact("Tan All");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MethodProvider.log(" ==== making hide");
        } else {
            MethodProvider.log(" ==== widget is null");
        }
    }

    public void goToTanner() {
        main.getWalking().walk(tannerArea.getRandomTile());
    }

    public boolean insideTanningArea(Player currentPlayerLocation) {
        return tannerArea.contains(currentPlayerLocation);
    }

}
