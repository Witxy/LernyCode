package com.example.tabmenu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginTabFragment extends Fragment {
    @Nullable

    EditText email, password;

    TextView forgetPass;
    Button login;
    float v = 0;
    FirebaseAuth fAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        email = root.findViewById(R.id.email);

        password = root.findViewById(R.id.password);
        login = root.findViewById(R.id.b_log);
        fAuth = FirebaseAuth.getInstance();
        forgetPass = root.findViewById(R.id.forgot_pass);

        email.setTranslationX(800);
        password .setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(300).start();
        password.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(500).start();
        forgetPass.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(700).start();
        login.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(900).start();


        login.setOnClickListener(new View.OnClickListener() {
    @Override
            public void onClick(View v)
             {
                 String sEmail = email.getText().toString();
                 String sPass = password.getText().toString();


                 if(TextUtils.isEmpty(sEmail))
                 {
                     email.setError("Почта обязательна");
                     return;
                 }
                 if(TextUtils.isEmpty(sPass))
                 {
                     password.setError("Пароль обязателен");
                     return;
                 }

                 //аутентификация
                 fAuth.signInWithEmailAndPassword(sEmail, sPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(getContext(), "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();
                             Intent myIntent = new Intent(getContext(), MainActivity.class);
                             startActivity(myIntent);
                         }
                         else
                         {
                             Toast.makeText(getContext(), "Ошибка!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });






            }
        });


        return root;
    }




}
