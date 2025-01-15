package si.um.feri.fras.global;

public enum ColorTheme {
    BASIC, AQUA, MAGMA;

    public static ColorTheme fromString(String colorTheme) {
        for (ColorTheme theme : ColorTheme.values()) {
            if (theme.name().equalsIgnoreCase(colorTheme)) {
                return theme;
            }
        }
        throw new IllegalArgumentException("No enum constant " + ColorTheme.class.getCanonicalName() + "." + colorTheme);
    }

}

