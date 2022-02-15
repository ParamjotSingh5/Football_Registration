package Utilities;

public enum CustomDefaultsValues {
    STRING_DEFAULT("default"),
    INT_DEFAULT(-2),
    INT_ARRAY_DEFAULT("NULL");

    private String value = "";
    private int int_value = 0;

    private CustomDefaultsValues(String Value) {
        value = Value;
    }
    private CustomDefaultsValues(int Value) {
        int_value = Value;
    }

    public String toString() {
        return this.value;
    }

    public int toInt() {
        return this.int_value;
    }
}
