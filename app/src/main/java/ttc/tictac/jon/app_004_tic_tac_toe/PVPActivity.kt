package ttc.tictac.jon.app_004_tic_tac_toe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ttc.tictac.jon.app_004_tic_tac_toe.databinding.ActivityPvpactivityBinding

class PVPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPvpactivityBinding
    private lateinit var name_player1: String
    private lateinit var name_player2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPvpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name_player1 = intent.getStringExtra("name").toString()

        // Configurar el clic del botón de confirmación
        binding.confirmButton.setOnClickListener {
            name_player2 = binding.player2Name.text.toString()

            if (name_player2.isNotEmpty()) {
                // Asegúrate de que el jugador 2 haya ingresado un nombre
                val intent = Intent(this, GamePVPActivity::class.java)
                intent.putExtra("name_player1", name_player1)
                intent.putExtra("name_player2", name_player2)
                startActivity(intent)
            } else {
                // Mostrar un mensaje o alerta si el nombre del jugador 2 está vacío
                binding.player2Name.error = "Por favor ingresa un nombre para Jugador 2"
            }
        }
    }
}
