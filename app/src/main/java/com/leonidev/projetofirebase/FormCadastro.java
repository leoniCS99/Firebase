package com.leonidev.projetofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class FormCadastro extends AppCompatActivity {


    private EditText edit_nome, edit_email, edit_senha; // CRIADO OS OBJETOS
    private Button bt_cadastrar;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);


        getSupportActionBar().hide();
        IniciarComponentes();

        bt_cadastrar.setOnClickListener(new View.OnClickListener() { // RECEBENDO EVENTO DE CLIQUE
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();


                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) { // VERIFICA SE O USUÁRIO DIGITOU ALGO NOS CAMPOS
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT); // Mostra a mensagem armazedad no array caso nenhum dos campos esteja Preenchidos
                    snackbar.show(); // EXECUTA A MENSAGEM
                } else {

                    CadastrarUsuario(v);
                }


            }
        });

    }


    private void CadastrarUsuario(View v) {  //METODO QUE REALIZA O CADASTRO DO USUARIO

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();


        // VALIDA EMAIL E SENHA
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                if (task.isSuccessful()) { //TASK TRAZ O RESULTADO DO CADASTRO

                    //salvarDadosUsuario();



                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else {
                    String erro;

                    try {

                        throw Objects.requireNonNull(task.getException());

                    } catch (FirebaseAuthWeakPasswordException e) { //Trata acessão usuário digitar acima de 4 caracteres para senha

                        erro = "Digite uma senha com no minimo 6 caracteres";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esta conta ja foi Cadastrada";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail invalido";

                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }

                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
            }
        });

    }


    private void SalvarDadosUsuarios(){
        String nome = edit_nome.getText().toString();

        FirebaseFirestore dataBase = FirebaseFirestore.getInstance(); // Conexão com banco de dados
    }



    private void IniciarComponentes() {

        edit_nome = findViewById(R.id.edit_nome);  // RECUPERAR OS ID
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
    }


}