package li.cil.oc.api.machine;

public interface Arguments {
    boolean isInteger(int index);
    int checkInteger(int index);
    boolean isDouble(int index);
    double checkDouble(int index);
    boolean isBoolean(int index);
    boolean checkBoolean(int index);
    boolean isString(int index);
    String checkString(int index);
    int count();
}
