package ttc.tictac.jon.app_004_tic_tac_toe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ttc.tictac.jon.app_004_tic_tac_toe.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var button : Button
    private lateinit var playerName : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = binding.btnStart
        playerName = binding.etName

        button.setOnClickListener {
            val name = playerName.text.toString()
            if (name.isNotEmpty()){
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Introduce tu nombre para continuar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

