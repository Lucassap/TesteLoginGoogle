package br.digitalhouse.projetologingoogle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    public static final String GOOGLE_ACCOUNT = "google_account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleSignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 101);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101:
                    try {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount conta = task.getResult(ApiException.class);
                        concluirLogin(conta);
                    } catch (ApiException e) {
                        Log.i("LOG", "Error:" + e.getMessage());
                        Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void concluirLogin(GoogleSignInAccount conta) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GOOGLE_ACCOUNT, conta);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Você já está logado", Toast.LENGTH_SHORT).show();
            concluirLogin(alreadyloggedAccount);
        } else {
            Toast.makeText(this, "Entre em alguma coisa", Toast.LENGTH_LONG).show();
        }
    }

}
