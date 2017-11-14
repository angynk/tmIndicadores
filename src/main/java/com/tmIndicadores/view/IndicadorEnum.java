package com.tmIndicadores.view;


public enum IndicadorEnum {

    NUMERO_BUSES("NB","Buses"),
    KM_COMERCIALES("KC","Kilometros Comerciales"),
    KM_VACIO("KV","Kilometros en Vacio"),
    EXP_COMERCIAL("EC","Expediciónes Comerciales"),
    LINEA_CARGADA("LC","Lineas Cargadas"),
    VELOCIDAD_COMERCIAL("VC","Velocidad Comercial"),
    HORAS_BUSES("HB","Horas Por Buses"),
    POR_VACIOS("PV","Porcentaje Vacio %"),
    TI_NUMERO_BUSES("NB","Buses"),
    TI_KM_COMERCIALES("KC","KM/H"),
    TI_KM_VACIO("KV","KM/H"),
    TI_EXP_COMERCIAL("EC","Expediciones Comerciales"),
    TI_LINEA_CARGADA("LC","Lineas Cargadas"),
    TI_VELOCIDAD_COMERCIAL("VC","KM/H"),
    TI_HORAS_BUSES("HB","Hora/Buses"),
    TI_POR_VACIOS("PV","%");

    private final String identifier;
    private final String nombre;

    /**
     * Constructor.
     *
     * @param identifier
     *            - identfier.
     */
    private IndicadorEnum(String identifier,String nombre) {
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
