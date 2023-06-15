package Interfaces;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TelaSobre implements Tela {
	private FlowPane principal = new FlowPane();
	private Executor executor;
	
	public TelaSobre(Executor executor) {
		this.executor = executor;
	}
	
	@Override
	public void start() {
		VBox vbox = new VBox();
		
		Label titulo = new Label("Integrantes");
		
		vbox.getChildren().addAll(
			titulo,
			new Label("Antonio William Almeida de Souza - RA: 1110482122041"),
			new Label("Felippe Ramos Marcial Dornellas - RA: 1110482122005"),
			new Label("Lucas Andrade Silva - RA: 1110482122046"),
			new Label("Wellen Ap. C. Matias Silva - RA: 1110482122009")
		);
		
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().forEach(e -> e.setStyle("-fx-font-size: 15"));
		vbox.setSpacing(10);
		titulo.setStyle("-fx-font-size: 25");

		principal.setAlignment(Pos.CENTER);
		principal.getChildren().add(vbox);
	}

	@Override
	public Pane render() {
		return principal;
	}

}
