package Interfaces;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application implements Executor {

	private Tela Editora, Autor, Livro, Estoque, Compra, Genero, Cliente, Edicao, Sobre, Home;
	private BorderPane principal = new BorderPane();
	
	@Override
	public void start(Stage stage) throws Exception {
		Editora = new TelaEditora(this);
		Editora.start();
		Autor = new TelaAutor(this);
        Autor.start();
        Livro = new TelaLivro(this);
        Livro.start();
        Estoque = new TelaEstoque(this);
        Estoque.start();
        Compra = new TelaCompra(this);
        Compra.start();
        Genero = new TelaGenero(this);
        Genero.start();
        Cliente = new TelaCliente(this);
        Cliente.start();
        Edicao = new TelaEdicao(this);
        Edicao.start();
        Sobre = new TelaSobre(this);
        Sobre.start();
        Home = new TelaHome(this);
        Home.start();
        
		MenuBar mnuBar = new MenuBar();
		Menu mnuCadastro = new Menu("Cadastro");
		Menu mnuHome = new Menu("Home");
		Menu mnuSobre = new Menu("Sobre");
		mnuSobre.setOnAction(e-> {
			abrir("Sobre"); });
		
		mnuBar.getMenus().addAll(mnuHome, mnuCadastro, mnuSobre);
		      
		MenuItem mnuEditora = new MenuItem("Editora");
		mnuEditora.setOnAction( e-> {
			abrir("Editora"); });
		
        MenuItem mnuAutor = new MenuItem("Autor");
        mnuAutor.setOnAction( e -> {
            abrir("Autor"); });
        
        MenuItem mnuLivro = new MenuItem("Livro");
        mnuLivro.setOnAction( e -> {
            abrir("Livro"); });
        
        MenuItem mnuEstoque = new MenuItem("Estoque");
        mnuEstoque.setOnAction( e -> {
            abrir("Estoque"); });
        
        MenuItem mnuCompra = new MenuItem("Compra");
        mnuCompra.setOnAction( e -> {
            abrir("Compra"); });
        
        MenuItem mnuGenero = new MenuItem("Genero");
        mnuGenero.setOnAction( e -> {
            abrir("Genero"); });
        
        MenuItem mnuCliente = new MenuItem("Cliente");
        mnuCliente.setOnAction( e -> {
            abrir("Cliente"); });
        
        MenuItem mnuEdicao = new MenuItem("Edição");
        mnuEdicao.setOnAction( e -> {
            abrir("Edicao"); });
        
		mnuCadastro.getItems().addAll(mnuAutor, mnuCliente, mnuCompra, mnuEdicao, mnuEditora, mnuEstoque, mnuGenero, mnuLivro);
		
		MenuItem mnuInfo = new MenuItem("Integrantes");
		mnuInfo.setOnAction( e -> {
            abrir("Sobre"); });
		
		mnuSobre.getItems().addAll(mnuInfo);
		
		MenuItem mnuLogo = new MenuItem("Logo");
		mnuLogo.setOnAction( e -> {
			abrir("Home"); });
		
		mnuHome.getItems().addAll(mnuLogo);
		
		principal.setTop(mnuBar);
	
		Scene scn = new Scene(principal, 600, 500);
		
		stage.setScene(scn);
		stage.setResizable(false);
		stage.setTitle("Sistema de Livraria");
		stage.show();
		
		abrir("Home");
	}

	@Override	
	public void abrir(String cmd) {
        if ("Editora".equals(cmd)) { 
            principal.setCenter(Editora.render());
        } else if ("Autor".equals(cmd)) {
            principal.setCenter(Autor.render());
        } else if ("Livro".equals(cmd)) {
        	principal.setCenter(Livro.render());
        } else if ("Estoque".equals(cmd)) {
        	principal.setCenter(Estoque.render());
        } else if ("Compra".equals(cmd)) {
        	principal.setCenter(Compra.render());
        } else if ("Genero".equals(cmd)) {
        	principal.setCenter(Genero.render());
        } else if ("Cliente".equals(cmd)) {
        	principal.setCenter(Cliente.render());
        } else if ("Edicao".equals(cmd)) {
        	principal.setCenter(Edicao.render());
        } else if ("Sobre".equals(cmd)) {
        	principal.setCenter(Sobre.render());
        } else if ("Home".equals(cmd)) {
        	principal.setCenter(Home.render());
        }
    }
	public static void main(String[] args) {
		Application.launch();
	}
}