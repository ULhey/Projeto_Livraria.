package Interfaces;

import java.sql.SQLException;

import Controler.ControleCompra;
import Entidade.Autor;
import Entidade.Compra;
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

public class TelaCompra implements Tela {

	private FlowPane principal = new FlowPane();
	
	private TableView<Compra> table = new TableView<>();
	private TextField txtCod = new TextField();
	private TextField txtCodLivro = new TextField();
	private TextField txtQuantidade = new TextField();
	private TextField txtValor = new TextField();
	private TextField txtCodCliente = new TextField();
	
	private ControleCompra cl = null;
	private Executor executor;

	public TelaCompra(Executor executor) {
		this.executor = executor;
	}

	public void tabela() {
		TableColumn<Compra, Integer> col1 = new TableColumn<>("Códigos");
		col1.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("cod"));
		
		TableColumn<Compra, Integer> col2 = new TableColumn<>("Cod. Edição");
		col2.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("codEdicao"));
		
		TableColumn<Compra, String> col3 = new TableColumn<>("Quantidade");
		col3.setCellValueFactory(new PropertyValueFactory<Compra, String>("quantidade"));
		
		TableColumn<Compra, Double> col4 = new TableColumn<>("Valor");
		col4.setCellValueFactory(new PropertyValueFactory<Compra, Double>("valorTotal"));
		
		TableColumn<Compra, Integer> col5 = new TableColumn<>("Cod. Cliente");
		col5.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("codCliente"));

		TableColumn<Compra, Void> col6 = new TableColumn<>("Ações");
		Callback<TableColumn<Compra, Void>, TableCell<Compra, Void>> acoes = new Callback<>() {
			
			@Override
			public TableCell<Compra, Void> call(TableColumn<Compra, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Compra, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Compra data = table.getItems().get(getIndex());
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
		
		col6.setCellFactory(acoes);
		try {
			table.getColumns().addAll(col1, col2, col3, col4, col5, col6);
		} catch (Exception e) {
			System.out.println(e);
		}
		table.setItems(cl.getLista());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Compra>() {
			@Override
			public void onChanged(Change<? extends Compra> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {
		try {	
			cl = new ControleCompra();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCod.setPrefWidth(290);
		txtCodLivro.setPrefWidth(290);
		txtQuantidade.setPrefWidth(290);
		txtValor.setPrefWidth(290);
		txtValor.disabledProperty();
		txtCodCliente.setPrefWidth(290);

		VBox vbox = new VBox();
		
		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Cod. livro: "), 0, 1);
		grid.add(txtCodLivro, 1, 1);
		grid.add(new Label("Quantidade: "), 0, 2);
		grid.add(txtQuantidade, 1, 2);
		grid.add(new Label("Valor: "), 0, 3);
		grid.add(txtValor, 1, 3);
		grid.add(new Label("CPF Cliente: "), 0, 4);
		grid.add(txtCodCliente, 1, 4);
		
		ligacoes();
		tabela();
		
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setAlignment(Pos.CENTER);
		
		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtCodLivro.getText() == "") {
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
			if (Integer.parseInt(txtCod.getText()) == 0 || txtCodLivro.getText() == "") {
				Alert m = new Alert(AlertType.ERROR, "Campos em branco ou codigo zerado", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.atualizar();
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
		
		Label titulo = new Label("Compra\n\n");
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
		Bindings.bindBidirectional(txtCodLivro.textProperty(), cl.codEdicaoProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtQuantidade.textProperty(), cl.quantidadeProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtValor.textProperty(), cl.valorTotalProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtCodCliente.textProperty(), cl.cpfClienteProperty());
	}
	
	public void limparCampos() {
		txtCod.setText("");
		txtCodLivro.setText("");
		txtQuantidade.setText("");
		txtValor.setText("");
		txtCodCliente.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}

}
