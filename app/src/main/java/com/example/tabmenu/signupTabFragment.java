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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.ktx.Firebase;

import java.util.Objects;

public class signupTabFragment extends Fragment {

    @Nullable

    EditText email, name, password, passwordAgain;
    FirebaseAuth fAuth;
    Button login;
    float v = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        name = root.findViewById(R.id.name);
        password = root.findViewById(R.id.password);
        passwordAgain = root.findViewById(R.id.password_again);

        login = root.findViewById(R.id.b_log);
        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser()!=null)
        {
            Intent myIntent = new Intent(getContext(), MainActivity.class);
            startActivity(myIntent);
            requireActivity().finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sPass = password.getText().toString();
                String sPassAgain = passwordAgain.getText().toString();
                String sName = name.getText().toString();

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
                if(sPass.length()<6)
                {
                    password.setError("Пароль должен быть длинее 6 символов");
                    return;
                }
                if(!sPass.equals(sPassAgain))
                {
                    password.setError("Пароли не совпадают");
                    return;
                }

                //регистрация пользователя

                fAuth.createUserWithEmailAndPassword(sEmail, sPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(sName.toString()).build();
                                user.updateProfile(profileUpdates);
                                Toast.makeText(getContext(), "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
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





       email.setTranslationX(800);
        name.setTranslationX(800);
       password .setTranslationX(800);
       passwordAgain.setTranslationX(800);

        login.setTranslationX(800);




        email.setAlpha(v);
        name.setAlpha(v);
        password.setAlpha(v);
        passwordAgain.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(300).start();
        name.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(500).start();
        password.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(700).start();
        passwordAgain.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(700).start();
        login.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(900).start();






        return root;
    }

}
