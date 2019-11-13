package br.digitalhouse.projetologingoogle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import static br.digitalhouse.projetologingoogle.LoginActivity.GOOGLE_ACCOUNT;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    TextView textView2;
    ImageView imageView;
    Button button;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView1= findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);



        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        pegaOsDados();

        button.setOnClickListener(v->
                googleSignInClient.signOut().addOnCompleteListener(task -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }));




    }

    private void pegaOsDados(){
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(imageView);
        textView.setText(googleSignInAccount.getDisplayName());
        textView1.setText(googleSignInAccount.getEmail());
        textView2.setText(googleSignInAccount.getId());
        button.setText("LOGOUT");
    }


}
