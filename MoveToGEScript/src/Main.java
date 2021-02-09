import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

/**
 * @author Clive on 9/18/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.MISC, name = "Go To GE", version = 1.0)
public class Main extends AbstractScript {




    @Override
    public int onLoop() {
        getWalking().walk(new Tile(3165 ,3484));
        if(getLocalPlayer().isStandingStill()) getGrandExchange().open();
        return 5000;
    }
}
