package li.cil.oc.api.machine;

public interface Context {
    boolean isPaused();
    boolean pause(double seconds);
    boolean signal(String name, Object... args);
    boolean start();
    boolean stop();
}
