package com.tmIndicadores.view;

public enum IndicadorExpEnum {
    NUMERO_BUSES("NB","Buses"),
    KM_COMERCIALES("KC","Kilometros Comerciales"),
    KM_VACIO("KV","Kilometros en Vacio"),
    POR_VACIOS("PV","Porcentaje Vacio %"),
    KM_VACIO_VPA("KP","Kilometros en Vacio VPa"),
    KM_VACIO_VEX("KE","Kilometros en Vacio VEx"),
    KM_VACIO_VH("KH","Kilometros en Vacio VP"),
    ;

    private final String identifier;
    private final String nombre;

    /**
     * Constructor.
     *
     * @param identifier
     *            - identfier.
     */
    private IndicadorExpEnum(String identifier,String nombre) {
        this.identifier = identifier;
        this.nombre = nombre;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return identifier;
    }

    public static String getEnumNameForValue(Object value){
        IndicadorEnum[] values = IndicadorEnum.values();
        String enumValue = null;
        for(IndicadorEnum eachValue : values) {
            enumValue =eachValue.toString();

            if (enumValue.equals(value)) {
                return eachValue.name();
            }
        }
        return enumValue;
    }

    public String getNombre() {
        return nombre;
    }
}
