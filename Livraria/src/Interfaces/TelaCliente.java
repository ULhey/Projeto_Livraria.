package Interfaces;

import java.sql.SQLException;

import Controler.ControleCliente;
import Entidade.Cliente;
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

public class TelaCliente implements Tela {
	private FlowPane principal = new FlowPane();
	private TableView<Cliente> table = new TableView<>();

	private TextField txtCPF = new TextField();
	private TextField txtNome = new TextField();

	private ControleCliente cl = null;
	private Executor executor;

	public TelaCliente(Executor executor) {
		this.executor = executor;
	}

	public void tabela() {
		TableColumn<Cliente, String> col1 = new TableColumn<>("CPFs");
		col1.setCellValueFactory(new PropertyValueFactory<Cliente, String>("CPF"));

		TableColumn<Cliente, String> col2 = new TableColumn<>("Nomes");
		col2.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));

		TableColumn<Cliente, Void> col3 = new TableColumn<>("Ações");
		
		Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> acoes = new Callback<>() {
			@Override
			public TableCell<Cliente, Void> call(TableColumn<Cliente, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Cliente, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Cliente data = table.getItems().get(getIndex());
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

		col3.setCellFactory(acoes);
		try {
			table.getColumns().addAll(col1, col2, col3);
		} catch (Exception e) {
			System.out.println(e);
		}
		table.setItems(cl.getLista());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Cliente>() {
			@Override
			public void onChanged(Change<? extends Cliente> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {
		try {
			cl = new ControleCliente();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCPF.setPrefWidth(290);
		txtNome.setPrefWidth(290);

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.add(new Label("CPF: "), 0, 0);
		grid.add(txtCPF, 1, 0);
		grid.add(new Label("Nome: "), 0, 1);
		grid.add(txtNome, 1, 1);

		ligacoes();
		tabela();
		
		grid.setHgap(10);
		grid.setVgap(10);

		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (txtCPF.getText() == "" || txtNome.getText() == "") {
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
			if (txtCPF.getText() == "" || txtNome.getText() == "") {
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
			if (txtCPF.getText() == "") {
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

		Label titulo = new Label("Cliente\n\n");
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
		Bindings.bindBidirectional(txtCPF.textProperty(), cl.CPFProperty());
		Bindings.bindBidirectional(txtNome.textProperty(), cl.nomeProperty());
	}

	public void limparCampos() {
		txtCPF.setText("");
		txtNome.setText("");

	}

	@Override
	public Pane render() {
		return principal;
	}
}
