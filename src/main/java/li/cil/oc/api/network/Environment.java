package li.cil.oc.api.network;

public interface Environment {
    Node node();
    void onConnect(Node node);
    void onDisconnect(Node node);
    void onMessage(Message message);
}
