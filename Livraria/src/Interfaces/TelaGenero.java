package Interfaces;

import java.sql.SQLException;

import Controler.ControleGenero;
import Entidade.Genero;
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

public class TelaGenero implements Tela {
	private FlowPane principal = new FlowPane();
	
	private TableView<Genero> table = new TableView<>();
	private TextField txtCod = new TextField();
	private TextField txtDescricao = new TextField();

	private ControleGenero cl = null;
	private Executor executor;

	public TelaGenero(Executor executor) {
		this.executor = executor;
	}

	public void tabela() {
		TableColumn<Genero, Integer> col1 = new TableColumn<>("Códigos");
		col1.setCellValueFactory(new PropertyValueFactory<Genero, Integer>("cod"));

		TableColumn<Genero, String> col2 = new TableColumn<>("Descrição");
		col2.setCellValueFactory(new PropertyValueFactory<Genero, String>("descricao"));

		TableColumn<Genero, Void> col4 = new TableColumn<>("Ações");
		Callback<TableColumn<Genero, Void>, TableCell<Genero, Void>> acoes = new Callback<>() {
			@Override
			public TableCell<Genero, Void> call(TableColumn<Genero, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Genero, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Genero data = table.getItems().get(getIndex());
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
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Genero>() {
			@Override
			public void onChanged(Change<? extends Genero> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}
	
	@Override
	public void start() {
		try {
			cl = new ControleGenero();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCod.setPrefWidth(290);
		txtDescricao.setPrefWidth(290);

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Descrição: "), 0, 1);
		grid.add(txtDescricao, 1, 1);

		ligacoes();
		tabela();
		
		grid.setHgap(10);
		grid.setVgap(10);

		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtDescricao.getText() == "") {
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
			if (Integer.parseInt(txtCod.getText()) == 0 || txtDescricao.getText() == "") {
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

		Label titulo = new Label("Genero\n\n");
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
		Bindings.bindBidirectional(txtDescricao.textProperty(), cl.descricaoProperty());
	}

	public void limparCampos() {
		txtCod.setText("");
		txtDescricao.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}
}
