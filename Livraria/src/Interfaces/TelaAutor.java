package Interfaces;

import java.sql.SQLException;

import Controler.ControleAutor;
import Entidade.Autor;
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

public class TelaAutor implements Tela {
	private FlowPane principal = new FlowPane();

	private TableView<Autor> table = new TableView<>();
	private TextField txtCod = new TextField();
	private TextField txtNome = new TextField();

	private ControleAutor cl = null;
	private Executor executor;

	public TelaAutor(Executor executor) {
		this.executor = executor;
	}

	public void tabela() {
		TableColumn<Autor, Integer> col1 = new TableColumn<>("Códigos");
		col1.setCellValueFactory(new PropertyValueFactory<Autor, Integer>("cod"));

		TableColumn<Autor, String> col2 = new TableColumn<>("Nomes");
		col2.setCellValueFactory(new PropertyValueFactory<Autor, String>("nome"));

		TableColumn<Autor, Void> col4 = new TableColumn<>("Ações");
		Callback<TableColumn<Autor, Void>, TableCell<Autor, Void>> acoes = new Callback<>() {
			
			@Override
			public TableCell<Autor, Void> call(TableColumn<Autor, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Autor, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Autor data = table.getItems().get(getIndex());
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
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Autor>() {
			@Override
			public void onChanged(Change<? extends Autor> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {
		try {
			cl = new ControleAutor();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCod.setPrefWidth(290);
		txtNome.setPrefWidth(290);

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Nome: "), 0, 1);
		grid.add(txtNome, 1, 1);

		ligacoes();
		tabela();

		grid.setHgap(10);
		grid.setVgap(10);

		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtNome.getText() == "") {
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
			if (Integer.parseInt(txtCod.getText()) == 0 || txtNome.getText() == "") {
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
		
		Label titulo = new Label("Autor\n\n");
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
		Bindings.bindBidirectional(txtNome.textProperty(), cl.nomeProperty());
	}

	public void limparCampos() {
		txtCod.setText("");
		txtNome.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}
}
