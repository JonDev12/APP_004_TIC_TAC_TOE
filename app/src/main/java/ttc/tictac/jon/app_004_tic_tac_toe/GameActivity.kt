package ttc.tictac.jon.app_004_tic_tac_toe

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ttc.tictac.jon.app_004_tic_tac_toe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var name: String
    private lateinit var txt_name: TextView
    private lateinit var ia_option: LinearLayout
    private lateinit var pvp_option: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("name").toString()
        txt_name = binding.name
        txt_name.text = "Hola $name, selecciona un modo de juego"

        ia_option = binding.iaOption
        pvp_option = binding.pvpOption

        ia_option.setOnClickListener {
            // Acción para IA
            val intent = Intent(this, IAActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        pvp_option.setOnClickListener {
            // Acción para PVP
            val intent = Intent(this, PVPActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }
}
