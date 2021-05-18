     package com.example.tabmenu

    import android.content.Intent
    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import android.util.Log
    import android.widget.ImageButton
    import androidx.appcompat.app.AppCompatActivity
    import androidx.viewpager.widget.ViewPager
    import com.google.android.gms.auth.api.signin.GoogleSignIn
    import com.google.android.gms.auth.api.signin.GoogleSignInClient
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions
    import com.google.android.gms.common.api.ApiException
    import com.google.android.material.floatingactionbutton.FloatingActionButton
    import com.google.android.material.tabs.TabLayout
    import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.GoogleAuthProvider

     class LoginActivity : AppCompatActivity() {

         private lateinit var mAuth: FirebaseAuth
         private lateinit var googleSignInClient: GoogleSignInClient

         companion object{
             private const val RC_SIGN_IN = 120
         }

         override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
             val tabLayout: TabLayout = findViewById(R.id.tab_layout)
             val viewPager: ViewPager = findViewById(R.id.view_pager)
             val vk: ImageButton = findViewById(R.id.fab_vk)
             val google: ImageButton = findViewById(R.id.fab_google)
             val twitter: ImageButton = findViewById(R.id.fab_twitter)

             mAuth = FirebaseAuth.getInstance()
             val user  = mAuth.currentUser

             Handler(Looper.getMainLooper()).postDelayed({
                 if (user != null) {
                     val myIntent = Intent(this, MainActivity::class.java)
                     startActivity(myIntent)
                     finish()
                 }

             }, 2000)


             // Configure Google Sign In
             val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build()

             googleSignInClient = GoogleSignIn.getClient(this, gso)





            tabLayout.addTab(tabLayout.newTab().setText("Вход"))
             tabLayout.addTab(tabLayout.newTab().setText("Регистрация"))
             tabLayout.tabGravity = TabLayout.GRAVITY_FILL

            val logAdapter = LoginAdapter(supportFragmentManager, this, tabLayout.tabCount)

                viewPager.adapter= logAdapter

             viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))


             tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                 override fun onTabSelected(tab: TabLayout.Tab) {
                     viewPager.currentItem = tab.position
                 }

                 override fun onTabUnselected(tab: TabLayout.Tab) {}
                 override fun onTabReselected(tab: TabLayout.Tab) {}
             })


             /*
             * Устанавливаем кнопке гугла обработчик события на вход*/
             google.setOnClickListener{
                 signIn()
             }


             vk.translationX = 300F
             google.translationX= 300F
             twitter.translationX= 300F
             tabLayout.translationX= 300F

             val v: Float = 0F
             vk.alpha= v
             google.alpha= v
             twitter.alpha= v
             tabLayout.alpha= v


             vk.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(200).start()
             google.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
             twitter.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(600).start()
             tabLayout.animate().translationX(0F).alpha(1F).setDuration(1000).setStartDelay(100).start()


         }
         private fun signIn() {
             val signInIntent = googleSignInClient.signInIntent
             startActivityForResult(signInIntent, RC_SIGN_IN)
         }

         override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
             super.onActivityResult(requestCode, resultCode, data)

             // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
             if (requestCode == RC_SIGN_IN) {
                 val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                 val exception = task.exception
                 if(task.isSuccessful) {
                     try {
                         // Google Sign In was successful, authenticate with Firebase
                         val account = task.getResult(ApiException::class.java)!!
                         Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
                         firebaseAuthWithGoogle(account.idToken!!)
                     } catch (e: ApiException) {
                         // Google Sign In failed, update UI appropriately
                         Log.w("LoginActivity", "Google sign in failed", e)

                     }
                 }
                 else
                 {
                     Log.w("LoginActivity", exception.toString())
                 }
             }
         }
         private fun firebaseAuthWithGoogle(idToken: String) {
             val credential = GoogleAuthProvider.getCredential(idToken, null)
             mAuth.signInWithCredential(credential)
                 .addOnCompleteListener(this) { task ->
                     if (task.isSuccessful) {
                         Log.d("LoginActivity", "signInWithCredential:success")
                         val myIntent = Intent(this, MainActivity::class.java)
                         startActivity(myIntent)
                         finish()
                     } else {
                         // If sign in fails, display a message to the user.
                         Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                     }
                 }
         }

    }