package ttc.tictac.jon.app_004_tic_tac_toe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ttc.tictac.jon.app_004_tic_tac_toe.databinding.ActivityGamePvpactivityBinding

class GamePVPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamePvpactivityBinding
    private lateinit var player1NameTextView: TextView
    private lateinit var player2NameTextView: TextView

    // Variables para controlar el estado del juego
    private var gameState = IntArray(9) { -1 }  // -1 representa vacío, 0 para jugador1 y 1 para jugador2
    private var activePlayer = 0  // 0 para jugador1, 1 para jugador2
    private var gameActive = true

    // Variables para contar los movimientos
    private var player1Moves = 0
    private var player2Moves = 0

    // Combinaciones ganadoras
    private val winningPositions: Array<IntArray> = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),  // Horizontales
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),  // Verticales
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                        // Diagonales
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePvpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los nombres de los jugadores del intent
        val player1Name = intent.getStringExtra("name_player1") ?: "Jugador 1"
        val player2Name = intent.getStringExtra("name_player2") ?: "Jugador 2"

        // Configurar los TextViews para mostrar los nombres de los jugadores
        player1NameTextView = binding.jugadorTextView
        player2NameTextView = binding.jugador2TextView

        player1NameTextView.text = "$player1Name\n$player1Moves"
        player2NameTextView.text = "$player2Name\n$player2Moves"

        // Configurar clics de los botones
        setupButtonClicks()

        // Configurar el clic del botón de reinicio
        binding.resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun setupButtonClicks() {
        // Asignar un listener a cada botón del tablero
        val buttons = arrayOf(
            binding.button0, binding.button1, binding.button2,
            binding.button3, binding.button4, binding.button5,
            binding.button6, binding.button7, binding.button8
        )

        for ((index, button) in buttons.withIndex()) {
            button.setOnClickListener {
                onButtonClick(it, index)
            }
        }
    }

    private fun onButtonClick(view: View, buttonIndex: Int) {
        val clickedButton = view as Button

        // Verificar si el botón ya fue presionado o si el juego sigue activo
        if (gameState[buttonIndex] == -1 && gameActive) {
            // Actualizar el estado del juego para el jugador actual
            gameState[buttonIndex] = activePlayer

            // Actualizar el texto del botón según el jugador
            clickedButton.text = if (activePlayer == 0) "X" else "O"

            // Incrementar el contador del jugador actual
            if (activePlayer == 0) player1Moves++ else player2Moves++

            // Actualizar el contador de movimientos en la interfaz
            updateMoveCount()

            // Comprobar si hay un ganador
            if (checkForWinner()) return

            // Cambiar al siguiente jugador
            activePlayer = 1 - activePlayer  // Alterna entre 0 y 1
        }
    }

    private fun updateMoveCount() {
        // Obtener los nombres de los jugadores
        val player1Name = intent.getStringExtra("name_player1") ?: "Jugador 1"
        val player2Name = intent.getStringExtra("name_player2") ?: "Jugador 2"

        // Actualizar los TextView con los nombres y movimientos de los jugadores
        player1NameTextView.text = "$player1Name\n$player1Moves"
        player2NameTextView.text = "$player2Name\n$player2Moves"
    }

    private fun checkForWinner(): Boolean {
        for (winningPosition in winningPositions) {
            val (a, b, c) = winningPosition

            // Si los tres valores son iguales y no son -1, tenemos un ganador
            if (gameState[a] == gameState[b] && gameState[b] == gameState[c] && gameState[a] != -1) {
                gameActive = false

                // Obtener el nombre del jugador ganador
                val winnerName = if (gameState[a] == 0) {
                    intent.getStringExtra("name_player1") ?: "Jugador 1"
                } else {
                    intent.getStringExtra("name_player2") ?: "Jugador 2"
                }

                Toast.makeText(this, "¡$winnerName ha ganado!", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        // Comprobar si hay empate
        if (!gameState.contains(-1)) {
            gameActive = false
            Toast.makeText(this, "¡Empate!", Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }

    private fun resetGame() {
        gameState.fill(-1)
        gameActive = true
        activePlayer = 0
        player1Moves = 0
        player2Moves = 0

        // Limpiar el texto de los botones
        val buttons = arrayOf(
            binding.button0, binding.button1, binding.button2,
            binding.button3, binding.button4, binding.button5,
            binding.button6, binding.button7, binding.button8
        )

        buttons.forEach { it.text = "" }

        // Reiniciar el contador de movimientos en los TextView
        updateMoveCount()
    }
}