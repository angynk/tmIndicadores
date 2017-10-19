package com.tmIndicadores.view;

import com.tmIndicadores.controller.*;
import com.tmIndicadores.controller.servicios.FechasAsociadasServicios;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

@ManagedBean(name = "cargarBean")
@ViewScoped
public class CargarIndicadoresBean {

    private String tipoGeneracion;
    private String razonProgramacion;
    private String lineasCargadas;
    private String periocidad;
    private String tipologia;
    private String tipoDEF;
    private String path;
    private String modo;
    private List<ListObject> modos;
    private List<ListObject> tipologias;
    private String cuadro;
    private String cuadroDef;
    private Date fechaProgramacion;
    private Date fechaProgramacionDef;
    private Date fechaIniBusquedaDef;
    private Date fechaFinBusquedaDef;
    private UploadedFile traceLog;
    private List<LogDatos> logDatos;
    private DualListModel<Programacion> progList;
    private Map<String,Programacion> progMap;
    private boolean resultadosVisibles;
    private String tipoCarga;
    private boolean cargaArchivo;
    private boolean generarDEF;
    private boolean listaProg;
    private String fechas;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    @ManagedProperty("#{GoalProcessor}")
    private IndicadoresGoalProcessor idProcessor;

    @ManagedProperty(value="#{ProgramacionServicios}")
    private ProgramacionServicios programacionServicios;

    @ManagedProperty(value="#{FechasAsociadasServicios}")
    private FechasAsociadasServicios fechasAsociadasServicios;


    public CargarIndicadoresBean() {
    }


    @PostConstruct
    public void init() {
        tipoGeneracion ="1";
        periocidad ="HABIL";
        tipologia = "ART";
        resultadosVisibles = false;
        tipoCarga = "NC";
        tipoDEF = "DEF";
        cargaArchivo = false;
        generarDEF = false;
        progList = new DualListModel<Programacion>();
        logDatos = new ArrayList<LogDatos>();
        modo = "TRO";
        cargarListaModos();
        cargarListaTipologiaTroncal();

    }

    private void cargarListaTipologiaTroncal() {
        tipologias = ModosUtil.cargarListaTipologiaTroncal();
    }

    private void cargarListaTipologiaDual() {
        tipologias = ModosUtil.cargarListaTipologiaDual();
    }

    public void cargarListaModos(){
        modos = ModosUtil.cargarListaModos();
    }


    public void updateTipologias(){
        if(modo.equals("TRO")){
            cargarListaTipologiaTroncal();
        }else{
            cargarListaTipologiaDual();
        }
    }

    public void habilitarTipoCarga(){
        if(tipoCarga.equals("NC")){
            cargaArchivo = true;
            generarDEF =false;
        }else{
            cargaArchivo =false;
            generarDEF = true;
        }

    }

    public void reiniciar (){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/cargarIdicadores.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscar(){

        if(fechaIniBusquedaDef!=null && fechaFinBusquedaDef!=null){
            List<Programacion> listaSource = programacionServicios.getProgramacionbyAttributes(fechaIniBusquedaDef,fechaFinBusquedaDef);
            progMap = crearMapaProg(listaSource);
            List<Programacion> listaTarget =  new ArrayList<Programacion>();
            progList = new DualListModel<Programacion>(listaSource, listaTarget);
            listaProg = true;
            generarDEF = false;
        }else{
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,"Seleccione fechas para buscar programaciones para el calculo del indicador definitivo");
            listaProg = false;
        }


    }

    private Map<String, Programacion> crearMapaProg(List<Programacion> listaSource) {
        progMap = new HashMap<String,Programacion>();
        for(Programacion prog:listaSource){
            progMap.put(prog.toString(),prog);
        }
        return progMap;
    }

    public void continuarCalculo(){
        //Eliminar Programacion Existente
        Programacion defAlready = programacionServicios.getDEFAlready(fechaProgramacionDef, tipoDEF);
        programacionServicios.deleteProgramacion(defAlready);
        //Calcular Nuevo DEF
        calcularProgramacionDefinitiva();
        //refresh

    }

    public void calcularProgramacionDefinitiva(){
        List<Programacion> programaciones = progList.getTarget();
        Programacion nueva = new Programacion();
        nueva.setTipologia(tipoDEF);
        nueva.setModo(ModosUtil.getModoPorDEF(tipoDEF));
        nueva.setCuadro(cuadroDef);
        nueva.setFecha(fechaProgramacionDef);
        copiarInformacionBase(programaciones, nueva);
        nueva = calcularValorProgramacion(programaciones,nueva);
        programacionServicios.addProgramacion(nueva);
        agregarAsociacionesFecha(nueva,progMap.get(programaciones.get(0)));
        logDatos.add(new LogDatos("Nueva Programacion "+tipoDEF, TipoLog.INFO));
        messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,"Nuevo Indicador Definitivo");
        resultadosVisibles = true;
    }

    public void calcularDEF(){
        logDatos.add(new LogDatos("<<Inicio Calculo Indicador Definitivo>>", TipoLog.INFO));
        if(progList.getTarget().size()>0 && cuadroDef!=null && fechaProgramacionDef!=null){
        if(programacionServicios.isDEFAlready(fechaProgramacionDef,tipoDEF)){
            RequestContext.getCurrentInstance().execute("PF('reemplazarDEFDialog').show();");
        }else{
               calcularProgramacionDefinitiva();
        }
        }else{
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,"Complete todos los campos");
            resultadosVisibles = false;
        }

        logDatos.add(new LogDatos("<<Fin Calculo Indicador Definitivo>>", TipoLog.INFO));
    }

    private void agregarAsociacionesFecha(Programacion nueva, Programacion programacion) {
        List<FechaAsociada> fechasAsociadasProgramacion = fechasAsociadasServicios.getFechasAsociadasProgramacion(programacion);
        for(FechaAsociada fecha: fechasAsociadasProgramacion){
            FechaAsociada nuevaFecha = new FechaAsociada();
            nuevaFecha.setProgramacion(nueva);
            nuevaFecha.setFecha(fecha.getFecha());
            fechasAsociadasServicios.addFechaAsociada(nuevaFecha);
        }
    }

    private void copiarInformacionBase(List<Programacion> programaciones, Programacion nueva) {
        Programacion programacion = progMap.get(programaciones.get(0));
        if(programacion!=null){
            nueva.setPeriodicidad(programacion.getPeriodicidad());
            nueva.setRazonCambio(programacion.getRazonCambio());
            nueva.setTipoProgramacion(programacion.getTipoProgramacion());
            nueva.setFechaPadre(programacion.getFechaPadre());
            nueva.setFechaDuplicada(programacion.getFechaDuplicada());
        }
    }

    private Programacion calcularValorProgramacion(List<Programacion> programaciones,Programacion nueva) {
        int numBuses = 0;
        double kmComercialesFin = 0;
        double kmVacioFin = 0;
        double kmComerialesInicio = 0;
        double kmVacioInicio = 0;
        int lineasCargas = 0;
        int expComercial = 0;
        int numCambios = 0;
        double busesPorHora = 0.0;
        double tiempoExp = 0.0;
        double tiempoVacio = 0.0;
        for(int z=0;z<programaciones.size();z++){
            Programacion prog = progMap.get(programaciones.get(z));
            numBuses = numBuses + prog.getBuses();
            kmComercialesFin = kmComercialesFin + prog.getKmComercialFin();
            kmVacioFin = kmVacioFin + prog.getKmVacioFin();
            kmComerialesInicio = kmComerialesInicio + validarDoubleNulo(prog.getKmComercialIncio());
            kmVacioInicio = kmVacioInicio + validarDoubleNulo(prog.getKmVacioInicio());
            expComercial = expComercial + validarIntNulo(prog.getExpedicionComercial());
            lineasCargas = lineasCargas + prog.getLineasCargadas();
            numCambios = numCambios + getCambioLinea(prog.getNumCambioLinea());
            busesPorHora = busesPorHora + getHorasPorBus(prog.getHorasPorBuses());
            tiempoExp = tiempoExp + ProcessorUtils.convertirFormatoHoraADouble(prog.getTiempoExpedicion());
            tiempoVacio = tiempoVacio + ProcessorUtils.convertirFormatoHoraADouble(prog.getTiempoVacio());
        }

        nueva.setTiempoExpedicion(ProcessorUtils.convertirDoubleaFormatoHora(tiempoExp));
        nueva.setTiempoVacio(ProcessorUtils.convertirDoubleaFormatoHora(tiempoVacio));
        nueva.setBuses(numBuses);
        nueva.setKmComercialFin(kmComercialesFin);
        nueva.setKmVacioFin(kmVacioFin);
        nueva.setKmVacioInicio(kmVacioInicio);
        nueva.setKmComercialIncio(kmComerialesInicio);
        nueva.setLineasCargadas(lineasCargas);
        nueva.setExpedicionComercial(expComercial);
        nueva.setPorcentajeVacioFinal(nueva.getKmVacioFin()/nueva.getKmComercialFin());
        nueva.setNumCambioLinea(numCambios);
        nueva.setVelocidadComercial(calcularVelocidadComercial(nueva.getKmComercialFin(),nueva.getTiempoExpedicion()));
        nueva.setHorasPorBuses(calcularHorasPorBuses(nueva.getTiempoExpedicion(),nueva.getBuses(),nueva.getTiempoVacio()));
        if(kmComerialesInicio!= 0){
            nueva.setPorcentajeVacioInicio(kmVacioInicio/kmComerialesInicio);
        }
        return nueva;
    }

    private double getHorasPorBus(Double horasPorBuses) {
        if(horasPorBuses==null) return 0.0;
        return horasPorBuses;
    }

    private int getCambioLinea(Integer numCambioLinea) {
        if(numCambioLinea==null) return 0;
        return numCambioLinea;
    }

    private Double calcularHorasPorBuses(String tiempoExpedicion, Integer buses, String valores) {
        Double totalHoras = ProcessorUtils.convertirFormatoHoraADouble(tiempoExpedicion);
        Double horasVacio = ProcessorUtils.convertirFormatoHoraADouble(valores);
        Double horasBus = totalHoras+horasVacio;
        return horasBus/buses;
    }

    private Double calcularVelocidadComercial(Double kmComercialFin, String tiempoExpedicion) {
        Double horasTotales =  ProcessorUtils.convertirFormatoHoraADouble(tiempoExpedicion);
        return ProcessorUtils.round(kmComercialFin/horasTotales,2);
    }

    public Double validarDoubleNulo(Double valor){
        if(valor == null){
            return 0.0;
        }
        return valor;
    }

    public Integer validarIntNulo(Integer valor){
        if(valor == null){
            return 0;
        }
        return valor;
    }

    public void cargarArchivo(){
        if(valid()){

                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                fechas = ec.getRequestParameterMap().get("fechas");
            try {
                path = idProcessor.copyFile(traceLog.getFileName(), traceLog.getInputstream());
                if(sinDatosParaLaFecha(fechaProgramacion,tipologia,periocidad)){
                    cargarDatos(path);
                }else{
                    RequestContext.getCurrentInstance().execute("PF('reemplazarDialog').show();");
                }
            } catch (IOException e) {
                messagesView.error(Messages.MENSAJE_ARCHIVO_NO_EXCEL,Messages.ACCION_ARCHIVO_NO_EXCEL);
            }

        }else{
            messagesView.error(Messages.MENSAJE_CAMPOS_INCOMPLETOS,Messages.ACCION_CAMPOS_INCOMPLETOS);
        }
    }

    public void cargarDatos(String path){
        logDatos  = idProcessor.processDataFromFile(traceLog.getFileName(), fechaProgramacion,
        razonProgramacion, tipologia, periocidad, lineasCargadas,cuadro,modo,fechas,path);
        resultadosVisibles = true;
        if(logDatos.size()>2){
                messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.ACCION_INDICADORES_REVISAR);
        }else{
                messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,Messages.ACCION_INDICADORES_ALMACENADOS);
        }
    }

    public void continuarCarga(){
        cargarDatos(path);
    }
    public void finalizarCarga(){
        messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.ACCION_INDICADORES_REVISAR);
    }

    private boolean sinDatosParaLaFecha(Date fechaProgramacion, String tipologia, String periocidad) {
        List<Programacion> programaciones = programacionServicios.getProgramacionbyFechaTipologiaPeriocidad(fechaProgramacion, tipologia, periocidad);
        if(programaciones.size()> 0) {
            return false;
        }
        return true;
    }

    private boolean valid() {
        if(fechaProgramacion!= null && razonProgramacion!=null && tipologia!=null && periocidad!=null ){
            if(lineasCargadas!=null){
                try{
                    Integer.parseInt(lineasCargadas);
                }catch (Exception e){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    public String getTipoGeneracion() {
        return tipoGeneracion;
    }


    public void setTipoGeneracion(String tipoGeneracion) {
        this.tipoGeneracion = tipoGeneracion;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public UploadedFile getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(UploadedFile traceLog) {
        this.traceLog = traceLog;
    }

    public String getRazonProgramacion() {
        return razonProgramacion;
    }

    public void setRazonProgramacion(String razonProgramacion) {
        this.razonProgramacion = razonProgramacion;
    }

    public IndicadoresGoalProcessor getIdProcessor() {
        return idProcessor;
    }

    public void setIdProcessor(IndicadoresGoalProcessor idProcessor) {
        this.idProcessor = idProcessor;
    }

    public String getPeriocidad() {
        return periocidad;
    }

    public void setPeriocidad(String periocidad) {
        this.periocidad = periocidad;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }


    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getLineasCargadas() {
        return lineasCargadas;
    }

    public void setLineasCargadas(String lineasCargadas) {
        this.lineasCargadas = lineasCargadas;
    }

    public String getCuadro() {
        return cuadro;
    }

    public void setCuadro(String cuadro) {
        this.cuadro = cuadro;
    }

    public List<LogDatos> getLogDatos() {
        return logDatos;
    }

    public void setLogDatos(List<LogDatos> logDatos) {
        this.logDatos = logDatos;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public boolean isCargaArchivo() {
        return cargaArchivo;
    }

    public void setCargaArchivo(boolean cargaArchivo) {
        this.cargaArchivo = cargaArchivo;
    }

    public String getCuadroDef() {
        return cuadroDef;
    }

    public void setCuadroDef(String cuadroDef) {
        this.cuadroDef = cuadroDef;
    }

    public Date getFechaProgramacionDef() {
        return fechaProgramacionDef;
    }

    public void setFechaProgramacionDef(Date fechaProgramacionDef) {
        this.fechaProgramacionDef = fechaProgramacionDef;
    }

    public Date getFechaIniBusquedaDef() {
        return fechaIniBusquedaDef;
    }

    public void setFechaIniBusquedaDef(Date fechaIniBusquedaDef) {
        this.fechaIniBusquedaDef = fechaIniBusquedaDef;
    }

    public Date getFechaFinBusquedaDef() {
        return fechaFinBusquedaDef;
    }

    public void setFechaFinBusquedaDef(Date fechaFinBusquedaDef) {
        this.fechaFinBusquedaDef = fechaFinBusquedaDef;
    }

    public boolean isListaProg() {
        return listaProg;
    }

    public void setListaProg(boolean listaProg) {
        this.listaProg = listaProg;
    }

    public DualListModel<Programacion> getProgList() {
        return progList;
    }

    public void setProgList(DualListModel<Programacion> progList) {
        this.progList = progList;
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public boolean isGenerarDEF() {
        return generarDEF;
    }

    public void setGenerarDEF(boolean generarDEF) {
        this.generarDEF = generarDEF;
    }

    public String getTipoDEF() {
        return tipoDEF;
    }

    public void setTipoDEF(String tipoDEF) {
        this.tipoDEF = tipoDEF;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public List<ListObject> getModos() {
        return modos;
    }

    public void setModos(List<ListObject> modos) {
        this.modos = modos;
    }

    public List<ListObject> getTipologias() {
        return tipologias;
    }

    public void setTipologias(List<ListObject> tipologias) {
        this.tipologias = tipologias;
    }

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public FechasAsociadasServicios getFechasAsociadasServicios() {
        return fechasAsociadasServicios;
    }

    public void setFechasAsociadasServicios(FechasAsociadasServicios fechasAsociadasServicios) {
        this.fechasAsociadasServicios = fechasAsociadasServicios;
    }
}
