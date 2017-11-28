package helpgenerator.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import org.controlsfx.control.Notifications;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Alertas {
	Notifications notificacao;
	
	//Dialogs dialog;
	
	public Alertas(){}
	
	@SuppressWarnings("static-access")
	public void notificacaoSucessoSalvarDados(String titulo){
		notificacao.create()
        .title(titulo)
        .text("Sucesso ao salvar dados!")
        .showInformation(); 
	}
	
	@SuppressWarnings("static-access")
	public void notificacaoSucesso(String titulo, String texto){
		notificacao.create()
        .title(titulo)
        .text(texto)
        .showInformation();     		
	}

	@SuppressWarnings("static-access")
	public void notificacaoErro(String titulo, String texto){
		notificacao.create()
        .title(titulo)
        .text(texto)
        .showError();     		
	}
	
	@SuppressWarnings("static-access")
	public void notificacaoAlerta(String titulo, String texto){
		notificacao.create()
        .title(titulo)
        .text(texto)
        .showWarning();     		
	}
	
	public void alertaErro(String titulo, String texto){
		Alert alertErro = new Alert(AlertType.ERROR);
		setIcon(alertErro);
        alertErro.setTitle(titulo);
        alertErro.setHeaderText(texto);
        //fecharTela(alertErro);
        alertErro.showAndWait();
	}
	
	public Optional<ButtonType> alertaErro2(String titulo, String texto){
		Alert alertErro = new Alert(AlertType.ERROR);
		setIcon(alertErro);
        alertErro.setTitle(titulo);
        alertErro.setHeaderText(texto);
        //fecharTela(alertErro);
        Optional<ButtonType> result = alertErro.showAndWait();
        return result;
	}
	
	public void alertaErro1(String titulo, String texto){
		Alert alertErro = new Alert(AlertType.ERROR);
		setIcon(alertErro);
        alertErro.setTitle(titulo);
        alertErro.setHeaderText(texto);
        //fecharTela(alertErro);
        Optional<ButtonType> result = alertErro.showAndWait();
        fecharTela(alertErro, result);
	}
	
	public void alertaSucesso(String titulo, String texto){
		Alert alertInformacao = new Alert(AlertType.INFORMATION);
		setIcon(alertInformacao);
		alertInformacao.setTitle(titulo);
		alertInformacao.setHeaderText(texto);
		alertInformacao.showAndWait();
	}
	
	public void alertaSucessoSalvarDados(String titulo){
		Alert alertInformacao = new Alert(AlertType.INFORMATION);
		setIcon(alertInformacao);
		alertInformacao.setTitle(titulo);
		alertInformacao.setHeaderText("Sucesso ao salvar dados!");
		alertInformacao.showAndWait();
	}
	
	public void alertaAviso(String titulo, String texto){
		Alert alertAviso = new Alert(AlertType.WARNING);
		setIcon(alertAviso);
		alertAviso.setTitle(titulo);
		alertAviso.setHeaderText(texto);
		alertAviso.showAndWait();
	}
	
	public Optional<ButtonType> alertaConfirmacao(String texto){
		Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
		setIcon(alertConfirmacao);
		alertConfirmacao.setTitle("Confirmação");
		alertConfirmacao.setHeaderText(texto);
		alertConfirmacao.setContentText(null);
		Optional<ButtonType> result = alertConfirmacao.showAndWait();
		return result;
	}
 
	public Optional<ButtonType> alertaConfirmacaoSair(){
		Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
		setIcon(alertConfirmacao);
		alertConfirmacao.setTitle("Confirmação");
		alertConfirmacao.setHeaderText("Deseja realmente sair?");
		alertConfirmacao.setContentText(null);	
		Optional<ButtonType> result = alertConfirmacao.showAndWait();
		return result;
	}
	
	public Optional<ButtonType> alertaConfirmacaoSairTelaCadastro(){
		Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
		setIcon(alertConfirmacao);
		alertConfirmacao.setTitle("Confirmação");
		alertConfirmacao.setHeaderText("Deseja realmente sair e perder os dados que não foram salvos?");
		alertConfirmacao.setContentText(null);	
		Optional<ButtonType> result = alertConfirmacao.showAndWait();
		return result;
	}
	
	private void fecharTela(Alert alert, Optional<ButtonType> o){
		alert.onCloseRequestProperty();
		
		//System.out.println(alert.);
		/*Scene scene = (Scene) alert.getDialogPane().getScene();
		System.out.println(scene);
		System.out.println(scene.getRoot().getParent());
		BorderPane pane = (BorderPane) scene.lookup("#paneCarregando");
		pane.setVisible(false);*/
	}
	
	private void setIcon(Alert alert){
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:resources/images/icon-principal.png"));
	}
	
	public void showErrorDialog(Throwable ex){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Ops! =(");
		alert.setHeaderText("Ocorreu um erro ao executar a ação solicitada. Tente novamente mais tarde.\nSe o erro persistir, contate o Administrador do sistema.");
		alert.setContentText("Por favor ao solicitar suporte envie o rastreamento da exceção abaixo.");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Rastreamento da exceção:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		
		//System.out.println(ex.getMessage());

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		setIcon(alert);
		
		alert.showAndWait();
	}
}
