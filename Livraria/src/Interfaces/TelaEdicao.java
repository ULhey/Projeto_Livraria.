package Interfaces;

import java.sql.SQLException;

import Controler.ControleEdicao;
import Entidade.Edicao;
import Entidade.Editora;
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

public class TelaEdicao implements Tela{

private FlowPane principal = new FlowPane();
	
	private TableView<Edicao> table = new TableView<>();
	private TextField txtCod = new TextField();
	private TextField txtTipoEd = new TextField();
	private TextField txtValor = new TextField();
	private TextField txtNumPag = new TextField();
	private TextField txtCodEditora = new TextField();
	private TextField txtCodLivro = new TextField();
	
	private ControleEdicao cl = null;
	private Executor executor;

	public TelaEdicao(Executor executor) {
		this.executor = executor;
	}
	
	public void tabela() {
		TableColumn<Edicao, Integer> col1 = new TableColumn<>("Códigos");
		col1.setCellValueFactory(new PropertyValueFactory<Edicao, Integer>("cod"));

		TableColumn<Edicao, String> col2 = new TableColumn<>("Tipo Ed.");
		col2.setCellValueFactory(new PropertyValueFactory<Edicao, String>("tipoed"));
		
		TableColumn<Edicao, Double> col3 = new TableColumn<>("Valores");
		col3.setCellValueFactory(new PropertyValueFactory<Edicao, Double>("valor"));
		
		TableColumn<Edicao, Integer> col4 = new TableColumn<>("Num. Páginas");
		col4.setCellValueFactory(new PropertyValueFactory<Edicao, Integer>("numpag"));
		
		TableColumn<Edicao, Integer> col5 = new TableColumn<>("Cod. Editora");
		col5.setCellValueFactory(new PropertyValueFactory<Edicao, Integer>("codEditora"));
		
		TableColumn<Edicao, Integer> col6 = new TableColumn<>("Cod. Livro");
		col6.setCellValueFactory(new PropertyValueFactory<Edicao, Integer>("codLivro"));

		TableColumn<Edicao, Void> col7= new TableColumn<>("Ações");
		Callback<TableColumn<Edicao, Void>, TableCell<Edicao, Void>> acoes = new Callback<>() {
			
			@Override
			public TableCell<Edicao, Void> call(TableColumn<Edicao, Void> param) {
				final Button btExcluir = new Button("Excluir");

				TableCell<Edicao, Void> cell = new TableCell<>() {
					{
						btExcluir.setOnAction(event -> {
							Edicao data = table.getItems().get(getIndex());
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
		
		col7.setCellFactory(acoes);
		try {
			table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
		} catch (Exception e) {
			System.out.println(e);
		}
		table.setItems(cl.getLista());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Edicao>() {
			@Override
			public void onChanged(Change<? extends Edicao> d) {
				if (!d.getList().isEmpty()) {
					cl.daEntidade(d.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {
		try {	
			cl = new ControleEdicao();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(90);
		
		txtCod.setPrefWidth(290);
		txtTipoEd.setPrefWidth(290);
		txtValor.setPrefWidth(290);
		txtNumPag.setPrefWidth(290);
		txtCodEditora.setPrefWidth(290);
		txtCodLivro.setPrefWidth(290);
		
		VBox vbox = new VBox();
		
		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Codigo livro: "), 0, 1);
		grid.add(txtTipoEd, 1, 1);
		grid.add(new Label("Valor: "), 0, 2);
		grid.add(txtValor, 1, 2);
		grid.add(new Label("Num. páginas: "), 0, 3);
		grid.add(txtNumPag, 1, 3);
		grid.add(new Label("Cod. editora: "), 0, 4);
		grid.add(txtCodEditora, 1, 4);
		grid.add(new Label("Cod. livro: "), 0, 5);
		grid.add(txtCodLivro, 1, 5);
		
		
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
		hbox.setPrefWidth(300);
		
		Label titulo = new Label("Edição\n\n");
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
		Bindings.bindBidirectional(txtTipoEd.textProperty(), cl.tipoEdProperty());
		Bindings.bindBidirectional(txtValor.textProperty(), cl.valorProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtNumPag.textProperty(), cl.numPagsProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtCodEditora.textProperty(), cl.codEditoraProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtCodLivro.textProperty(), cl.codLivroProperty(), new NumberStringConverter());
	}
	
	public void limparCampos() {
		txtCod.setText("");
		txtTipoEd.setText("");
		txtValor.setText("");
		txtNumPag.setText("");
		txtCodEditora.setText("");
		txtCodLivro.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}

}
