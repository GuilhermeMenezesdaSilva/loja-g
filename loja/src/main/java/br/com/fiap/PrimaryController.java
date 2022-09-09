package br.com.fiap;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import br.com.fiap.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable{

    @FXML private TextField TextFieldNome;
    @FXML private TextField TextFieldEmail;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox<String> choiceBoxPerfil;

    public void salvar(){
        var usuario = new Usuario(
            TextFieldNome.getText(), 
            TextFieldEmail.getText(), 
            passwordField.getText(), 
            choiceBoxPerfil.getValue()
            );
        System.out.println(usuario);
        
        String servidor = "oracle.fiap.com.br";
        //String baseDeDados = "";
        String username = "RM89384";
        String senha = "310302";
        String url = "jdbc:oracle:thin:@" + servidor + ":1521:orcl";

        try{
            System.out.println("Conectando..");
            Connection conexao = DriverManager.getConnection(url, username, senha);
            Statement comando = conexao.createStatement();

            String sql = String.format(   
                "INSERT INTO T_DDD_LOJA_USUARIO (id, nome, email, senha, perfil) VALUES (SEQ_USUARIO.nextval, '%s', '%s', '%s', '%s')",
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getPerfil()
            );
            comando.execute(sql);
            conexao.close();
            System.out.println("Conectado");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choiceBoxPerfil.getItems().addAll("Vendedor");
        choiceBoxPerfil.getItems().addAll("Gerente");        
    }

    public void abrirListadeUsuarios() throws IOException{
        App.setRoot("secondary");
    }
}
