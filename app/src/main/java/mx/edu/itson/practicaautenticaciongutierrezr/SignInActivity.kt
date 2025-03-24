package mx.edu.itson.practicaautenticaciongutierrezr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.Firebase
import kotlin.math.sign

class SignInActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth

        val email:EditText = findViewById(R.id.etrEmail)
        val password:EditText = findViewById(R.id.etrPassword)
        val confirmPassword:EditText = findViewById(R.id.etrConfirmPassword)
        val tvError:TextView = findViewById(R.id.tvrError)

        val button:Button = findViewById(R.id.btnRegister)

        tvError.visibility = View.INVISIBLE

        button.setOnClickListener {
            if (email.text.isBlank() || password.text.isBlank()  || confirmPassword.text.isBlank()) {
                tvError.text = "Asegúrese de llenar todos los campos."
                tvError.visibility = View.VISIBLE
            } else if (!password.text.toString().equals(confirmPassword.text.toString())) {
                tvError.text = "Asegúrese de que ambas contraseñas coincidan."
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.INVISIBLE
                signIn(email.text.toString(), password.text.toString())
            }
        }
    }

    fun signIn(email:String, password:String) {
        Log.d("INFO", "email: ${email}, password: ${password}")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d("INFO", "signInWithEmail:success")
                val user = auth.currentUser
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                Log.w("ERROR", "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "El registro falló", Toast.LENGTH_SHORT).show()
            }
        }
    }
}