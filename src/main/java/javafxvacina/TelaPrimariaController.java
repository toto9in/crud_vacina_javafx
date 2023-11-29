package javafxvacina;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafxvacina.models.Aplicacao;
import javafxvacina.models.Pessoa;
import javafxvacina.models.Vacina;
import javafxvacina.models.dao.AplicacaoDAO;
import javafxvacina.models.dao.DAOFactory;
import javafxvacina.models.dao.PessoaDAO;
import javafxvacina.models.dao.VacinaDAO;

public class TelaPrimariaController implements Initializable {

    @FXML
    private TableColumn<Vacina, Long> codigoColuna;

    @FXML
    private DatePicker APartirDeDatePicker;

    @FXML
    private DatePicker AteDatePicker;

    @FXML
    private TextField CPFTextField;

    @FXML
    private TextField codigoPessoaTextField;

    @FXML
    private TableColumn<Pessoa, Long> codigoPessoaColuna;

    @FXML
    private TableColumn<Pessoa, String> cpfColuna;

    @FXML
    private TableColumn<Pessoa, String> nascimentoColuna;

    @FXML
    private TableColumn<Pessoa, String> nomePessoaColuna;

    @FXML
    private TextField nomePessoaTextField;

    @FXML
    private TableView<Pessoa> pessoaTableView;

    @FXML
    private Button pesquisarPessoaButton;

    @FXML
    private Button criarAplicacaoButton;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TableColumn<Vacina, String> descricaoColuna;

    @FXML
    private TextField descricaoTextField;

    @FXML
    private Button editarButton;

    @FXML
    private TableColumn<Vacina, String> nomeColuna;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Button novaButton;

    @FXML
    private Button pesquisarButton;

    @FXML
    private Button removerButton;

    @FXML
    private TableView<Vacina> vacinaTableView;

    private Stage stageTelaSecundaria;
    private TelaSecundariaController controllerTelaSecundaria;
    private Vacina vacina;
    private Pessoa pessoa;

    List<Vacina> vacinas = null;
    List<Pessoa> pessoas = null;

    // Configurar um textFormatter que vi no stackoverflow pra permitir so numeros
    // nos inputs de codigo

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("telasecundaria.fxml"));
        Parent root;
        try {

            root = loader.load();
            Scene scene = new Scene(root);
            stageTelaSecundaria = new Stage();
            stageTelaSecundaria.setScene(scene);
            stageTelaSecundaria.setTitle("tela 2");
            controllerTelaSecundaria = loader.getController();

            // aceitando input de data so no formato brasileiro (stackoverflow)
            APartirDeDatePicker.setConverter(new StringConverter<LocalDate>() {
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    APartirDeDatePicker.setPromptText(pattern.toLowerCase());
                }

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            });

            // aceitando input de data so no formato brasileiro (stackoverflow)
            AteDatePicker.setConverter(new StringConverter<LocalDate>() {
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    AteDatePicker.setPromptText(pattern.toLowerCase());
                }

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            });

            // Configurar um textFormatter que vi no stackoverflow pra permitir so numeros
            // nos inputs de codigo
            codigoPessoaTextField.setTextFormatter(
                    new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]*") ? change : null));
            codigoPessoaTextField.setTextFormatter(
                    new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]*") ? change : null));
            // Chama a função buscarTodasVacinas e preenche a tabela com os valores das
            // Chama a função buscarTodasVacinas e preenche a tabela com os valores das
            // vacinas dessa lista
            // chama a funcao para pegar todas as pessoas
            vacinas = buscarTodasVacinas();
            pessoas = buscarTodasPessoas();

            // configura as celulas da tabela
            codigoPessoaColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigoPessoa"));
            nomePessoaColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nomePessoa"));
            cpfColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
            nascimentoColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("dataNascimento"));
            //JEITO que encontrei pra mostrar em formato brasileiro
            nascimentoColuna.setCellFactory(column -> {
                return new TableCell<Pessoa, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            // Parse date string from yyyy-MM-dd to dd/MM/yyyy
                            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate date = LocalDate.parse(item, inputFormat);
                            String formattedDate = outputFormat.format(date);

                            setText(formattedDate);
                        }
                    }
                };
            });

            pessoaTableView.getItems().addAll(pessoas);
            pessoaTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!pessoaTableView.getSelectionModel().isEmpty())) {
                    Pessoa selectedPessoa = pessoaTableView.getSelectionModel().getSelectedItem();
                    pessoa = selectedPessoa;
                    System.out.println(pessoa.toString());

                }
            });

            codigoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
            nomeColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
            descricaoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
            vacinaTableView.getItems().addAll(vacinas);
            vacinaTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!vacinaTableView.getSelectionModel().isEmpty())) {
                    Vacina selectedVacina = vacinaTableView.getSelectionModel().getSelectedItem();
                    controllerTelaSecundaria.setVacina(selectedVacina);
                    vacina = selectedVacina;
                    System.out.println(vacina);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaCriacao(ActionEvent event) {
        controllerTelaSecundaria.setEditar(false);
        stageTelaSecundaria.showAndWait();
        pesquisarVacina(event);
    }

    @FXML
    void abrirTelaEdicao(ActionEvent event) {
        if(vacina == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione uma vacina para editar!");
            alert.showAndWait();
            return;
        }
        controllerTelaSecundaria.setEditar(true);
        stageTelaSecundaria.showAndWait();
        pesquisarVacina(event);
    }

    @FXML
    void pesquisarVacina(ActionEvent event) {

        DAOFactory daoFactory = new DAOFactory();

        try {

            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();

            if (codigoTextField.getText().isEmpty()
                    && nomeTextField.getText().isEmpty()
                    && descricaoTextField.getText().isEmpty()) {
                vacinaTableView.getItems().clear();
                List<Vacina> vacinas = buscarTodasVacinas();
                codigoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
                nomeColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
                descricaoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
                vacinaTableView.getItems().addAll(vacinas);
            } else {
                String codigo = "";
                String nome = "";
                String descricao = "";

                if (!codigoTextField.getText().isBlank()) {
                    codigo = codigoTextField.getText();
                }
                if (!nomeTextField.getText().isBlank()) {
                    nome = nomeTextField.getText();
                }
                if (!descricaoTextField.getText().isBlank()) {
                    descricao = descricaoTextField.getText();
                }
                List<Vacina> vacinas = vacinaDAO.buscar(codigo, nome, descricao);

                vacinaTableView.getItems().clear();
                codigoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
                nomeColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
                descricaoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
                vacinas.add(vacina);
                vacinaTableView.getItems().addAll(vacinas);

            }

        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }

    }

    @FXML
    void removerVacina(ActionEvent event) {

        if(vacina == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione uma vacina para remover!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Vacina");
        alert.setHeaderText("Remover Vacina");
        alert.setContentText("Tem certeza que deseja remover a vacina?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                vacinaDAO.remover(vacina);
                vacinaTableView.getItems().remove(vacina);
            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();

            }
        }

    }

    @FXML
    void criarAplicacao(ActionEvent event) {

        Aplicacao aplicacao = new Aplicacao(LocalDate.now(), pessoa, vacina);
        if (vacina == null || pessoa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione uma vacina e uma pessoa!");
            alert.showAndWait();
            return;
        }
        
        DAOFactory daoFactory = new DAOFactory();

        try {
            daoFactory.abrirConexao();
            AplicacaoDAO aplicacaoDAO = daoFactory.criarAplicacaoDAO();

            if (aplicacaoDAO.gravar(aplicacao)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Aplicação cadastrada com sucesso!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao cadastrar aplicação!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }

    }

    protected void verificarDatas() {
        // data pode ser nula e ai envia uma string vazia pra pesquisa no banco
        if (APartirDeDatePicker == null) {
            APartirDeDatePicker.getEditor().clear();
        }
        if (AteDatePicker == null) {
            AteDatePicker.getEditor().clear();
        }
    }

    @FXML
    void pesquisarPessoa(ActionEvent event) {

        DAOFactory daoFactory = new DAOFactory();

        try {

            daoFactory.abrirConexao();
            PessoaDAO pessoaDAO = daoFactory.criarPessoaDAO();

            if (codigoPessoaTextField.getText().isEmpty()
                    && nomePessoaTextField.getText().isEmpty()
                    && CPFTextField.getText().isEmpty()
                    && APartirDeDatePicker.getValue() == null
                    && AteDatePicker.getValue() == null) {
                pessoaTableView.getItems().clear();
                List<Pessoa> pessoas = buscarTodasPessoas();
                codigoPessoaColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigoPessoa"));
                nomePessoaColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nomePessoa"));
                cpfColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
                nascimentoColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("dataNascimento"));
                pessoaTableView.getItems().addAll(pessoas);
            } else {
                String codigo = "";
                String nome = "";
                String cpf = "";
                String dataNascimento = "";
                String ateData = "";
                if (!codigoPessoaTextField.getText().isBlank()) {
                    codigo = codigoPessoaTextField.getText();
                }
                if (!nomePessoaTextField.getText().isBlank()) {
                    nome = nomePessoaTextField.getText();
                }
                if (!CPFTextField.getText().isBlank()) {
                    cpf = CPFTextField.getText();
                }
                if (APartirDeDatePicker.getValue() != null) {
                    dataNascimento = APartirDeDatePicker.getValue().toString();
                }
                if (AteDatePicker.getValue() != null) {
                    ateData = AteDatePicker.getValue().toString();
                }
                List<Pessoa> pessoas = pessoaDAO.buscar(codigo, nome, cpf, dataNascimento, ateData);
                pessoaTableView.getItems().clear();
                codigoPessoaColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigoPessoa"));
                nomePessoaColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nomePessoa"));
                cpfColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
                nascimentoColuna.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("dataNascimento"));
                pessoaTableView.getItems().addAll(pessoas);
            }

        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }

    }

    protected static List<Vacina> buscarTodasVacinas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
            List<Vacina> vacinas = vacinaDAO.buscarTodas();

            for (Vacina vacina : vacinas) {
                System.out.println(vacina);
            }
            return vacinas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        return null;

    }

    protected static List<Pessoa> buscarTodasPessoas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            PessoaDAO pessoaDAO = daoFactory.criarPessoaDAO();
            List<Pessoa> pessoas = pessoaDAO.buscarTodas();

            for (Pessoa pessoa : pessoas) {
                System.out.println(pessoa);
            }
            return pessoas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        return null;

    }

}
