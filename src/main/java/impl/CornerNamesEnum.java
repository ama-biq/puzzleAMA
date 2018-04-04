package impl;

public enum CornerNamesEnum {

    BL("BL"),
    BR("BR"),
    TL("TL"),
    TR("TR");

    String value;

    public String getValue() {
        return value;
    }

    CornerNamesEnum(String value) {
        this.value = value;
    }

}
