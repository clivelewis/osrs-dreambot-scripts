import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.Entity;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import java.awt.geom.Area;

/**
 * @author Clive on 9/22/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.COMBAT, version = 1.0, name = "Dummy Clicker")
public class Main extends AbstractScript {
    private Tile nearDummyTile = new Tile(3250, 3437);
    public static final Filter<GameObject> DUMMY_FILTER = npc -> {
        if(npc == null) return false;
        return npc.getName().equals("Dummy");
    };

    @Override
    public int onLoop() {

        if(!getLocalPlayer().getTile().equals(nearDummyTile)){
            getWalking().walk(nearDummyTile);
            return 4000;
        }else{
            Entity dummy = getGameObjects().closest(DUMMY_FILTER);
            if(dummy != null) dummy.interact("Attack");
            return 2000;
        }
    }
}
