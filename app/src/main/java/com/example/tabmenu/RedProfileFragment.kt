package com.example.tabmenu

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class RedProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val storageFirebase: StorageReference = FirebaseStorage.getInstance().reference
    var imageUri: Uri? = null

    override fun onStart()
    {
        super.onStart()

        val currentUser = mAuth.currentUser

        val nameEdit: EditText = requireActivity().findViewById(R.id.newNameEdit)
        val emailEdit: EditText = requireActivity().findViewById(R.id.newEmailEdit)
        val errorEmail: TextView = requireActivity().findViewById(R.id.errorEmail)
        val changeAvatarText: TextView = requireActivity().findViewById(R.id.changeAvatar)
        val saveText: TextView = requireActivity().findViewById(R.id.saveText)
        val avatar: ImageButton = requireActivity().findViewById(R.id.avatarButton)
        val avatarRef: StorageReference = storageFirebase.child("users/" + mAuth.currentUser?.uid + "profile.jpg")
        avatarRef.downloadUrl.addOnSuccessListener {
           Picasso.get().load(it).into(avatar)
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.grey) }?.let { it2 -> avatar.setBackgroundColor(it2) }
        }

        saveText.setOnClickListener {


            mAuth.fetchSignInMethodsForEmail(emailEdit.text.toString())
                    .addOnCompleteListener { task ->
                        val isNewUser = task.result?.signInMethods?.isEmpty()
                        if(currentUser?.email!=emailEdit.text.toString()) {
                            if (isNewUser == true) {
                                currentUser.updateEmail(emailEdit.text.toString())
                                errorEmail.visibility = View.INVISIBLE
                                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(nameEdit.text.toString()).build()
                                currentUser?.updateProfile(profileUpdates)
                                activity?.finish()
                            } else {
                                emailEdit.setTextColor(Color.RED)
                                errorEmail.visibility = View.VISIBLE
                            }
                        }
                        else
                        {
                            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(nameEdit.text.toString()).build()
                            currentUser.updateProfile(profileUpdates)
                            activity?.finish()
                        }
                    }

        }


        changeAvatarText.setOnClickListener {
            val openGallery = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(openGallery, 1000)
        }
        avatar.setOnClickListener{

            val openGallery = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(openGallery, 1000)

        }

        nameEdit.setText(currentUser?.displayName)
        emailEdit.setText(currentUser?.email)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val avatar: ImageButton = requireActivity().findViewById(R.id.avatarButton)
        val currentUser = mAuth.currentUser
        if(requestCode==1000)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                imageUri = data?.data
                //avatar.setImageURI(imageUri)
                imageUri?.let { uploadImageToFirebase(it) }


            }
        }

    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileReference: StorageReference = storageFirebase.child("users/" + mAuth.currentUser.uid + "profile.jpg")
        val avatar: ImageButton = requireActivity().findViewById(R.id.avatarButton)
        fileReference.putFile(imageUri).addOnSuccessListener {
            fileReference.downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(avatar)
            }
            
        }.addOnFailureListener{
            Toast.makeText(context, "Изображение не загружено", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        return inflater.inflate(R.layout.fragment_red_profile, container, false)
    }


}