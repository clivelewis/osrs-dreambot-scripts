package tradescript;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.AdvancedMessageListener;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "CliveLewis", category = Category.MISC, name = "TradeScript", version = 1.0, description = "Nothing")
public class Main extends AbstractScript implements AdvancedMessageListener {
    private final String checkoutTarget = "";
    @Override
    public int onLoop() {
        processTrade();
        return 1000;
    }

    private boolean processTrade(){
        if(getTrade().isOpen()){
            if(getTrade().contains(true, "Logs")){
                getTrade().acceptTrade();
            }else{
                getTrade().addItem("Logs", 1);
            }
        }
        return true;
    }
    @Override
    public void onAutoMessage(Message message) {
        log("OnAutoMessage: " + message);
    }

    @Override
    public void onPrivateInfoMessage(Message message) {
        log("onPrivateInfoMessage: " + message);
    }

    @Override
    public void onClanMessage(Message message) {
        log("onClanMessage: " + message);
    }

    @Override
    public void onGameMessage(Message message) {
        log("onGameMessage: " + message);
    }

    @Override
    public void onPlayerMessage(Message message) {
        log("onPlayerMessage: " + message);
    }

    @Override
    public void onTradeMessage(Message message) {
        log("onTradeMessage: " + message);
        String tradePartner = message.getUsername();
        if(tradePartner.equals("Hour2937")) {
            getTrade().tradeWithPlayer(tradePartner);
        }

    }

    @Override
    public void onPrivateInMessage(Message message) {
        log("onPrivateInMessage: " + message);
    }

    @Override
    public void onPrivateOutMessage(Message message) {
        log("onPrivateOutMessage: " + message);
    }
}
