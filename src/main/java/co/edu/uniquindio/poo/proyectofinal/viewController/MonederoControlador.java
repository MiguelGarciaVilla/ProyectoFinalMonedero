package co.edu.uniquindio.poo.proyectofinal.viewController;


import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class MonederoControlador {

    @FXML private Label labelClienteNombre;
    @FXML private Label labelPuntos;
    @FXML private Label labelRango;
    @FXML private Label labelSaldoActual;
    @FXML private ComboBox<String> comboMonederos;
    @FXML private TextArea areaLog;

    @FXML private TextField fieldDepositoMonto;
    @FXML private Button btnDepositar;

    @FXML private TextField fieldRetiroMonto;
    @FXML private Button btnRetirar;

    @FXML private TextField fieldTransferMonto;
    @FXML private TextField fieldTransferDestino;

    @FXML private Button btnTransferir;
    @FXML private TextField fieldMontoProgramado;
    @FXML private TextField fieldDestinoProgramado;
    @FXML private DatePicker datePickerFecha;
    @FXML private ComboBox<Periodicidad> comboPeriodicidad;
    @FXML private Button btnProgramarTx;
    @FXML private ListView<String> listBeneficios;
    @FXML private Button btnCanjear;
    @FXML private Button btnEjecutarAnalisis;


    private Cliente clientePrincipal;
    private Cliente clienteDestinoDemo;
    private ServicioTransacciones servicioTx;
    private ServicioPuntos servicioPuntos;
    private GestorProgramador gestorProgramador;
    private ServicioAnalitica servicioAnalitica;
    private Map<String, Monedero> monederoMap = new HashMap<>();
    private Map<String, Beneficio> beneficioMap = new HashMap<>();

    @FXML
    public void initialize() {
        configurar();
        INotificador notificadorGUI = (cliente, mensaje) -> {
            Platform.runLater(() -> log(mensaje));
        };
        servicioTx.registrarNotificador(notificadorGUI);
        servicioTx.registrarNotificador(new NotificadorWhatsApp());
        cargarDatosCliente();
        configurarComboPeriodicidad();
        cargarBeneficiosDisponibles();
        comboMonederos.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> {
                    if (newValue != null) {
                        actualizarSaldoLabel();
                    }
                }
        );
    }

    private void configurar() {
        this.servicioTx = new ServicioTransacciones();
        this.servicioPuntos = new ServicioPuntos();
        this.gestorProgramador = new GestorProgramador(servicioTx);
        this.servicioAnalitica = new ServicioAnalitica();
        this.clientePrincipal = new Cliente("C001", "Miguel Garcia", "kokakola2508@gmail.com");
        this.clienteDestinoDemo = new Cliente("C002", "Luis Torres", "ju4nd4707@gmail.com");

        Monedero mAhorroMig = new Monedero("MIG-01", 1500.0, "Ahorros");
        Monedero mDiarioMig = new Monedero("MIG-02", 300.0, "Gastos Diarios");
        clientePrincipal.agregarMonedero(mAhorroMig);
        clientePrincipal.agregarMonedero(mDiarioMig);

        Monedero mDiarioLuis = new Monedero("LUIS-01",200.0 , "Gastos Diarios");
        clienteDestinoDemo.agregarMonedero(mDiarioLuis);

        log("Sistema iniciado. Clientes de demostración cargados.");
    }

    private void configurarComboPeriodicidad() {
        comboPeriodicidad.getItems().setAll(Periodicidad.values());
        comboPeriodicidad.getSelectionModel().selectFirst();
    }

    private void cargarBeneficiosDisponibles() {
        beneficioMap.clear();
        listBeneficios.getItems().clear();

        for (Beneficio b : servicioPuntos.getBeneficiosDisponibles()) {
            String displayText = String.format("%s (Costo: %d puntos)",
                    b.getDescripcion(), b.getPuntosRequeridos());
            beneficioMap.put(displayText, b);
            listBeneficios.getItems().add(displayText);
        }
    }



    private void cargarDatosCliente() {
        labelClienteNombre.setText(clientePrincipal.getNombre());
        monederoMap.clear();
        comboMonederos.getItems().clear();
        for (Monedero m : clientePrincipal.getMonederos()) {
            String claveCombo = String.format("%s (%s)", m.getTipo(), m.getIdMonedero());
            monederoMap.put(claveCombo, m);
            comboMonederos.getItems().add(claveCombo);
        }
        if (!comboMonederos.getItems().isEmpty()) {
            comboMonederos.getSelectionModel().selectFirst();
        }
        actualizarLabelsCliente();
    }

    private void actualizarLabelsCliente() {
        labelPuntos.setText(String.valueOf(clientePrincipal.getPuntos()));
        labelRango.setText(clientePrincipal.getRango().name());
        actualizarSaldoLabel();
    }

    private void actualizarSaldoLabel() {
        Monedero seleccionado = getMonederoSeleccionado();
        if (seleccionado != null) {
            labelSaldoActual.setText(String.format("$ %.2f", seleccionado.getSaldo()));
        } else {
            labelSaldoActual.setText("$. --");
        }
    }

    @FXML
    private void handleDepositar() {
        Monedero m = getMonederoSeleccionado();
        if (m == null) {
            log("Error: Debe seleccionar un monedero para depositar.");
            return;
        }
        Optional<Double> monto = parseDouble(fieldDepositoMonto.getText());
        if (monto.isEmpty() || monto.get() <= 0) {
            log("Error: Ingrese un monto de depósito válido.");
            return;
        }
        servicioTx.realizarDeposito(clientePrincipal, m, monto.get(), LocalDate.now());
        fieldDepositoMonto.clear();
        actualizarLabelsCliente();
    }

    @FXML
    private void handleRetirar() {
        Monedero m = getMonederoSeleccionado();
        if (m == null) {
            log("Error: Debe seleccionar un monedero para retirar.");
            return;
        }
        Optional<Double> monto = parseDouble(fieldRetiroMonto.getText());
        if (monto.isEmpty() || monto.get() <= 0) {
            log("Error: Ingrese un monto de retiro válido.");
            return;
        }
        servicioTx.realizarRetiro(clientePrincipal, m, monto.get(), LocalDate.now());
        fieldRetiroMonto.clear();
        actualizarLabelsCliente();
    }

    @FXML
    private void handleTransferir() {
        Monedero mOrigen = getMonederoSeleccionado();
        if (mOrigen == null) {
            log("Error: Debe seleccionar un monedero de origen.");
            return;
        }
        Optional<Double> monto = parseDouble(fieldTransferMonto.getText());
        if (monto.isEmpty() || monto.get() <= 0) {
            log("Error: Ingrese un monto de transferencia válido.");
            return;
        }
        String idDestino = fieldTransferDestino.getText();
        if (idDestino == null || idDestino.trim().isEmpty()) {
            log("Error: Debe ingresar un ID de monedero de destino.");
            return;
        }
        Monedero mDestino = buscarMonederoDemo(idDestino);
        if(mDestino == null) {
            log("Error: No se encontró el monedero de destino con ID: " + idDestino);
            return;
        }
        servicioTx.realizarTransferencia(clientePrincipal, mOrigen, clienteDestinoDemo, mDestino, monto.get(), LocalDate.now());
        fieldTransferMonto.clear();
        fieldTransferDestino.clear();
        actualizarLabelsCliente();
    }

    @FXML
    private void handleProgramarTransferencia() {
        Monedero mOrigen = getMonederoSeleccionado();
        Optional<Double> monto = parseDouble(fieldMontoProgramado.getText());
        String idDestino = fieldDestinoProgramado.getText();
        LocalDate fecha = datePickerFecha.getValue();
        Periodicidad periodicidad = comboPeriodicidad.getValue();

        if (mOrigen == null) { log("Error: Debe seleccionar un monedero de origen."); return; }
        if (monto.isEmpty() || monto.get() <= 0) { log("Error: Ingrese un monto válido."); return; }
        if (idDestino == null || idDestino.trim().isEmpty()) { log("Error: Debe ingresar un ID de destino."); return; }
        Monedero mDestino = buscarMonederoDemo(idDestino);
        if (mDestino == null) { log("Error: No se encontró el monedero de destino: " + idDestino); return; }
        if (fecha == null || fecha.isBefore(LocalDate.now())) { log("Error: Seleccione una fecha futura."); return; }
        if (periodicidad == null) { log("Error: Seleccione una periodicidad."); return; }

        TransaccionProgramada tx = new TransaccionProgramada(
                clientePrincipal, mOrigen, mDestino, monto.get(), fecha, periodicidad
        );
        gestorProgramador.programarTransaccion(tx);

        log(String.format("Éxito: Transferencia de $%.2f a %s programada para %s.",
                monto.get(), idDestino, fecha.toString()));

        fieldMontoProgramado.clear();
        fieldDestinoProgramado.clear();
        datePickerFecha.setValue(null);
    }

    @FXML
    private void handleCanjearBeneficio() {
        String itemSeleccionado = listBeneficios.getSelectionModel().getSelectedItem();
        if (itemSeleccionado == null) {
            log("Error: Debe seleccionar un beneficio de la lista.");
            return;
        }
        Beneficio beneficio = beneficioMap.get(itemSeleccionado);
        boolean exito = servicioPuntos.canjearBeneficio(clientePrincipal, beneficio);
        if (exito) {
            log(String.format("¡Canje Exitoso! Se gastaron %d puntos.", beneficio.getPuntosRequeridos()));
            Monedero monederoDefecto = getMonederoSeleccionado();
            if (monederoDefecto == null) monederoDefecto = clientePrincipal.getMonederos().get(0);

            servicioTx.aplicarBono(clientePrincipal, monederoDefecto, beneficio.getMontoBono(), LocalDate.now());
            log(String.format("Has recibido un bono de $%.2f en tu monedero.", beneficio.getMontoBono()));
            actualizarLabelsCliente();

        } else {
            log(String.format("Error: No tienes suficientes puntos. (Necesitas %d, Tienes %d)",
                    beneficio.getPuntosRequeridos(), clientePrincipal.getPuntos()));
        }
    }

    @FXML
    private void handleEjecutarAnalisis() {
        LocalDate fin = LocalDate.now();
        LocalDate inicio = fin.minusDays(30);
        log("\n--- Solicitando Reporte de Gastos (Últimos 30 días) ---");
        String reporte = servicioAnalitica.generarReporteGasto(clientePrincipal, inicio, fin);
        log(reporte);
    }


    private Monedero getMonederoSeleccionado() {
        String claveSeleccionada = comboMonederos.getSelectionModel().getSelectedItem();
        if (claveSeleccionada == null) {
            return null;
        }
        return monederoMap.get(claveSeleccionada);
    }

    private Monedero buscarMonederoDemo(String id) {
        for (Monedero m : clienteDestinoDemo.getMonederos()) {
            if (m.getIdMonedero().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    private void log(String mensaje) {
        areaLog.appendText(mensaje + "\n");
    }

    private Optional<Double> parseDouble(String texto) {
        try {
            double valor = Double.parseDouble(texto);
            return Optional.of(valor);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
