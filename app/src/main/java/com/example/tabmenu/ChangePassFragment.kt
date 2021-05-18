package com.example.tabmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class  ChangePassFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        val oldPassEdit: EditText = requireActivity().findViewById(R.id.oldPasswordEdit)
        val newPassEdit: EditText = requireActivity().findViewById(R.id.newPasswordEdit)
        val newPassAgainEdit: EditText = requireActivity().findViewById(R.id.newPasswordAgainEdit)
        val oldPassError: TextView = requireActivity().findViewById(R.id.oldPasswordError)
        val newPassError: TextView = requireActivity().findViewById(R.id.newPasswordError)
        val saveText: TextView = requireActivity().findViewById(R.id.savePassText)

        saveText.setOnClickListener {
            oldPassError.visibility = View.INVISIBLE
            if(oldPassEdit.text.isNotEmpty()) {
                val credential = EmailAuthProvider
                    .getCredential(currentUser.email, oldPassEdit.text.toString())

                currentUser.reauthenticate(credential).addOnCompleteListener { task ->
                    if(task.isSuccessful)
                    {
                        if(passwordCheck()) {
                            currentUser!!.updatePassword(newPassEdit.text.toString())
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Пароль успешно обновлен",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        activity?.finish()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Ошибка!" + it.exception!!.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }
                    else
                    {
                        oldPassError.visibility = View.VISIBLE
                    }
                }
            }
            else
            {
                oldPassError.visibility = View.VISIBLE
                oldPassError.text = "Ошибка в вводе пароля"
            }

        }





    }

    private fun passwordCheck(): Boolean
    {
        val newPassEdit: EditText = requireActivity().findViewById(R.id.newPasswordEdit)
        val newPassAgainEdit: EditText = requireActivity().findViewById(R.id.newPasswordAgainEdit)
        val newPassError: TextView = requireActivity().findViewById(R.id.newPasswordError)


        if(newPassEdit.text.isEmpty() || newPassAgainEdit.text.isEmpty()) {
            newPassError.text = "Поле пароля не может быть пустым!"
            newPassError.visibility = View.VISIBLE
            return false
        }
        else {
            newPassError.visibility = View.INVISIBLE
        }
        if(newPassEdit.text.length<6){
            newPassError.text ="Пароль меньше 6 символов!"
            newPassError.visibility = View.VISIBLE
            return false
        }
        else {
            newPassError.visibility = View.INVISIBLE
        }
        if(newPassEdit.text.toString()!= newPassAgainEdit.text.toString()) {
            newPassError.text ="Пароли не совпадают"
            newPassError.visibility = View.VISIBLE
            return false
        }
        else{
            newPassError.visibility = View.INVISIBLE
        }

        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {

        return inflater.inflate(R.layout.fragment_change_pass, container, false)
    }


}