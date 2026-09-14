package li.cil.oc.api;

import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.tileentity.TileEntity;

public final class Network {
    public static NodeBuilder.NodeBuilderWithComponent newNode(Environment host, Visibility reachability) {
        return new NodeBuilder.NodeBuilderWithComponent();
    }

    public static void joinOrCreateNetwork(TileEntity te) {}

    public static class NodeBuilder {
        public NodeBuilder withConnector(int bufferSize) { return this; }
        public Node create() { return null; }

        public static class NodeBuilderWithComponent extends NodeBuilder {
            public NodeBuilderWithComponent withComponent(String name) { return this; }
            public NodeBuilderWithComponent withConnector(int bufferSize) { return this; }
        }
    }
}
