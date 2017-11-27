package helpgenerator.view;

import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.controlsfx.control.MaskerPane;
import helpgenerator.controller.HelpGeneratorService;
import helpgenerator.utils.Alertas;
import helpgenerator.utils.AplicacaoDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

public class GeradorHelpsBlueController {
	@FXML
	private ComboBox<AplicacaoDto> comboAplicacao = new ComboBox<>();
	
	@FXML
	private Button botaoGerarSelecionado;
	
	@FXML
	private Button botaoGerarTodos;
	
	@FXML
	private TextArea scriptRetornado;
	
	private HelpGeneratorService servico = new HelpGeneratorService();
	
	private Alertas alerta = new Alertas();
	
	private ObservableList<AplicacaoDto> originalItems;
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	private BorderPane paneCarregando;
	
	@SuppressWarnings("rawtypes")
	private Task gerarScript;
	
	String filter = "";
	
	private boolean geraTodos;
	
	@FXML
	public void initialize() {
		ObservableList<AplicacaoDto> itens = FXCollections.observableArrayList(servico.getAplicacoesQuePossuemHelp());
		originalItems = itens;
		comboAplicacao.getItems().addAll(itens);
		
		comboAplicacao.setTooltip(new Tooltip());
		comboAplicacao.setOnKeyPressed(this::handleOnKeyPressed);
		comboAplicacao.setOnHidden(this::handleOnHiding);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handleOnKeyPressed(KeyEvent e) {
		ObservableList filteredList = FXCollections.observableArrayList();
		KeyCode code = e.getCode();
				
		if (code.isLetterKey() || code.isDigitKey() || code == KeyCode.MINUS || code.isKeypadKey()) {
			filter += e.getText();
		}
		if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
			filter = filter.substring(0, filter.length() - 1);
			comboAplicacao.getItems().setAll(originalItems);
		}
		if (code == KeyCode.ESCAPE) {
			filter = "";
		}
		if (filter.length() == 0) {
			filteredList = originalItems;
			comboAplicacao.getTooltip().hide();
		} else {
			Stream<AplicacaoDto> itens = comboAplicacao.getItems().stream();
			String txtUsr = filter.toString().toLowerCase();
			itens.filter(el -> unAccent(el.toString()).toLowerCase().contains(txtUsr)).forEach(filteredList::add);
			comboAplicacao.getTooltip().setText(txtUsr);
			Window stage = comboAplicacao.getScene().getWindow();
			double posX = stage.getX() + comboAplicacao.getBoundsInParent().getMinX();
			double posY = stage.getY() + comboAplicacao.getBoundsInParent().getMinY();
			comboAplicacao.getTooltip().show(stage, posX, posY);
			comboAplicacao.show();
		}
		comboAplicacao.getItems().setAll(filteredList);
	}
	
	public void handleOnHiding(Event e) {
		filter = "";
		comboAplicacao.getTooltip().hide();
		AplicacaoDto s = comboAplicacao.getSelectionModel().getSelectedItem();
		comboAplicacao.getItems().setAll(originalItems);
		comboAplicacao.getSelectionModel().select(s);
	}
	
	public static String unAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
	
	@SuppressWarnings("rawtypes")
	public Task taskGerarScript() {
        return new Task() {
            @Override
            protected String call() throws Exception {
        		return getScript();
            }
            
			@Override
    		protected void succeeded() {
            	String result = (String) getValue();
            	scriptRetornado.setText(result);
            	alterarEstadoCampos(false);
            	alerta.notificacaoSucesso("Exportar script de helps", "Sucesso ao exportar script dos helps cadastrados!");
    		}
        };
    }
	
	private String getScript() {
		AplicacaoDto registro = comboAplicacao.getSelectionModel().getSelectedItem();
		if(geraTodos)
			return servico.gerarScriptHelps(0, true);
		else 
			return servico.gerarScriptHelps(registro.getIdAplicacao(), false);
	}
	
	@FXML
	private void gerarSelecionado() {
		AplicacaoDto registro = comboAplicacao.getSelectionModel().getSelectedItem();
		
		if(registro == null) {
			alerta.notificacaoAlerta("Gerar help de uma aplicação específica", "É necessário selecionar uma aplicação na combo para executar esta operação.");
			return;
		}else {
			geraTodos = false;
			alterarEstadoCampos(true);
			gerarScript = taskGerarScript();
			Thread t = new Thread(gerarScript);
			t.setDaemon(true);
			t.start();
		}
	}
	
	@FXML
	private void gerarTodos() {
		geraTodos = true;
		alterarEstadoCampos(true);
		gerarScript = taskGerarScript();
		Thread t = new Thread(gerarScript);
		t.setDaemon(true);
		t.start();
	}
	
	private void alterarEstadoCampos(boolean estado){
		avisoCarregando.setText("Gerando script... aguarde!");
		avisoCarregando.setVisible(estado);
		paneCarregando.setVisible(estado);
	}
}
