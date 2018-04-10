package impl;

public enum CornerNamesEnum {

    BL("BL"),
    BR("BR"),
    TL("TL"),
    TR("TR"),
    SQ("SQ"),
    TLTR("TLTR"),
    BLBR("BLBR"),
    TLBL("TLBL"),
    TRBR("TRBR");

    String value;

    public String getValue() {
        return value;
    }

    CornerNamesEnum(String value) {
        this.value = value;
    }

}
