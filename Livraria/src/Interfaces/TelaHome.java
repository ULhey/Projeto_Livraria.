package Interfaces;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class TelaHome implements Tela {
	private FlowPane principal = new FlowPane();
	private Executor executor;
	private final String imgUrl = "file:///H:/Programinha%20JAVA/Livraria/src/Image/Logo.png";
	
	public TelaHome(Executor executor) {
		this.executor = executor;
	}
	
	@Override
	public void start() {
		Image imagem = new Image(imgUrl);
		ImageView visualizadorImagem = new ImageView(imagem);
        
        principal.getChildren().add(visualizadorImagem);
        principal.setAlignment(Pos.CENTER);
	}

	@Override
	public Pane render() {
		return principal;
	}	
}