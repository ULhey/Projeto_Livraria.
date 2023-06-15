package Interfaces;

import java.sql.SQLException;

import Controler.ControleLivro;
import Entidade.Autor;
import Entidade.Livro;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class TelaLivro implements Tela {
	private FlowPane principal = new FlowPane();

	private TableView<Livro> table = new TableView<>();
	private TextField txtCod = new TextField();
	private TextField txtTitulo = new TextField();
	private TextField txtCodGenero = new TextField();
	private TextField txtCodAutor = new TextField();

	private ControleLivro cl = null;
	private Executor executor;

	public TelaLivro(Executor executor) {
		this.executor = executor;
	}

	public void tabela() {
		TableColumn<Livro, Integer> col1 = new TableColumn<>("Códigos");
		col1.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("cod"));

		TableColumn<Livro, String> col2 = new TableColumn<>("Titulo");
		col2.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));
		
		TableColumn<Livro, Integer> col3 = new TableColumn<>("Cod. Genero");
		col3.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("codGenero"));
		
		TableColumn<Livro, Integer> col4 = new TableColumn<>("Cod. Autor");
		col4.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("codAutor"));

		TableColumn<Livro, Void> col5 = new TableColumn<>("Ações");
		Callback<TableColumn<Livro, Void>, TableCell<Livro, Void>> acoes = new Callback<>() {
			
			@Override
			public TableCell<Livro, Void> call(TableColumn<Livro, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Livro, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Livro data = table.getItems().get(getIndex());
							try {
								cl.remove(data);
							} catch (ClassNotFoundException | SQLException e) {
								e.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btExcluir);
						}
					}
				};

				return cell;
			}
		};
		
		col5.setCellFactory(acoes);
		try {
			table.getColumns().addAll(col1, col2, col3, col4, col5);
		} catch (Exception e) {
			System.out.println(e);
		}
		table.setItems(cl.getLista());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Livro>() {
			@Override
			public void onChanged(Change<? extends Livro> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}
	
	public void start() {
		try {
			cl = new ControleLivro();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCod.setPrefWidth(290);
		txtTitulo.setPrefWidth(290);
		txtCodGenero.setPrefWidth(290);
		txtCodAutor.setPrefWidth(290);

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Titulo: "), 0, 1);
		grid.add(txtTitulo, 1, 1);
		grid.add(new Label("Cod. Gereno: "), 0, 2);
		grid.add(txtCodGenero, 1, 2);
		grid.add(new Label("Cod. Autor: "), 0, 3);
		grid.add(txtCodAutor, 1, 3);

		ligacoes();
		tabela();
		
		grid.setHgap(10);
		grid.setVgap(10);

		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtTitulo.getText() == "" || Integer.parseInt(txtCodGenero.getText()) == 0 || Integer.parseInt(txtCodAutor.getText()) == 0) {
				Alert m = new Alert(AlertType.ERROR, "Campos em branco ou codigo zerado", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.criar();
					limparCampos();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		Button btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtTitulo.getText() == "" || Integer.parseInt(txtCodGenero.getText()) == 0 || Integer.parseInt(txtCodAutor.getText()) == 0) {
				Alert m = new Alert(AlertType.ERROR, "Campos em branco ou codigo zerado", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.atualizar();
					table.setItems(cl.getLista());
					limparCampos();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0) {
				Alert m = new Alert(AlertType.ERROR, "Codigo não pode ser zero", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.leitura();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		hbox.getChildren().addAll(btnCriar, btnAtualizar, btnPesquisar);
		hbox.getChildren().forEach(bt -> bt.setStyle("-fx-pref-width: 100"));
		hbox.setSpacing(10);
		
		Label titulo = new Label("Livro\n\n");
		titulo.setStyle("-fx-font-size: 25");

		vbox.getChildren().addAll(titulo, grid, hbox, table);
		vbox.setSpacing(10);
		
		hbox.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		principal.getChildren().addAll(vbox);
		principal.setAlignment(Pos.CENTER);
		principal.setMargin(grid, new Insets(10));

	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtCod.textProperty(), cl.codProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtTitulo.textProperty(), cl.tituloProperty());
		Bindings.bindBidirectional(txtCodGenero.textProperty(), cl.codGeneroProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtCodAutor.textProperty(), cl.codAutorProperty(), new NumberStringConverter());
	}

	public void limparCampos() {
		txtCod.setText("");
		txtTitulo.setText("");
		txtCodGenero.setText("");
		txtCodAutor.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}
}
