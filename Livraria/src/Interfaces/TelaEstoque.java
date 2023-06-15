package Interfaces;

import java.sql.SQLException;

import Controler.ControleEstoque;
import Entidade.Estoque;
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

public class TelaEstoque implements Tela {

	private FlowPane principal = new FlowPane();
	
	private TableView<Estoque> table = new TableView<>();
	private TextField txtCod = new TextField();
	private TextField txtCodEdicao = new TextField();
	private TextField txtQuantidade = new TextField();
	
	private ControleEstoque cl = null;
	private Executor executor;

	public TelaEstoque(Executor executor) {
		this.executor = executor;
	}

	public void tabela() {
		TableColumn<Estoque, Integer> col1 = new TableColumn<>("Códigos");
		col1.setCellValueFactory(new PropertyValueFactory<Estoque, Integer>("cod"));
		
		TableColumn<Estoque, Integer> col2 = new TableColumn<>("Cód. Livro");
		col2.setCellValueFactory(new PropertyValueFactory<Estoque, Integer>("codEdicao"));
		
		TableColumn<Estoque, Integer> col3 = new TableColumn<>("Quantidades");
		col3.setCellValueFactory(new PropertyValueFactory<Estoque, Integer>("quantidade"));
		

		TableColumn<Estoque, Void> col4 = new TableColumn<>("Ações");
		Callback<TableColumn<Estoque, Void>, TableCell<Estoque, Void>> acoes = new Callback<>() {
			
			@Override
			public TableCell<Estoque, Void> call(TableColumn<Estoque, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Estoque, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Estoque data = table.getItems().get(getIndex());
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
		
		col4.setCellFactory(acoes);
		try {
			table.getColumns().addAll(col1, col2, col4);
		} catch (Exception e) {
			System.out.println(e);
		}
		table.setItems(cl.getLista());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Estoque>() {
			@Override
			public void onChanged(Change<? extends Estoque> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}
	
	@Override
	public void start() {
		try {	
			cl = new ControleEstoque();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCod.setPrefWidth(290);
		txtCodEdicao.setPrefWidth(290);
		txtQuantidade.setPrefWidth(290);

		VBox vbox = new VBox();
		
		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Codigo Edição: "), 0, 1);
		grid.add(txtCodEdicao, 1, 1);
		grid.add(new Label("Quantidade: "), 0, 2);
		grid.add(txtQuantidade, 1, 2);
		
		ligacoes();
		tabela();
		
		grid.setHgap(10);
		grid.setVgap(10);
		
		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtCodEdicao.getText() == "") {
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
			if (Integer.parseInt(txtCod.getText()) == 0 || txtCodEdicao.getText() == "") {
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

		Label titulo = new Label("Estoque\n\n");
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
		Bindings.bindBidirectional(txtCodEdicao.textProperty(), cl.codLivroProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtQuantidade.textProperty(), cl.quantidadeProperty(), new NumberStringConverter());
	}
	
	public void limparCampos() {
		txtCod.setText("");
		txtCodEdicao.setText("");
		txtQuantidade.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}

}
