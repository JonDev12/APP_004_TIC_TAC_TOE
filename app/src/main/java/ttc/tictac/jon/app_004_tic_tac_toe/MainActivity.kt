package ttc.tictac.jon.app_004_tic_tac_toe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ttc.tictac.jon.app_004_tic_tac_toe.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Variables para controlar el estado del juego
    private var gameState = IntArray(9) { -1 }  // -1 representa vacío, 0 para "O" y 1 para "X"
    private var activePlayer = 0  // 0 para el jugador "O", 1 para "X"
    private var gameActive = true

    // Variables para contar los movimientos
    private var playerMoves = 0
    private var iaMoves = 0

    // Combinaciones ganadoras
    private val winningPositions: Array<IntArray> = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),  // Horizontales
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),  // Verticales
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                        // Diagonales
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar clics de los botones
        setupButtonClicks()

        // Inicializar el contador de movimientos en los TextView
        updateMoveCount()

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
            // Actualizar el estado del juego para el jugador
            gameState[buttonIndex] = activePlayer
            clickedButton.text = "O"  // Jugador siempre es "O"
            playerMoves++

            // Actualizar el contador de movimientos en la interfaz
            updateMoveCount()

            // Comprobar si hay un ganador después del movimiento del jugador
            if (checkForWinner()) return

            // Cambiar a la IA
            activePlayer = 1

            // Retrasar el movimiento de la IA por 1000 milisegundos (1 segundo)
            Handler(Looper.getMainLooper()).postDelayed({
                iaMove()
            }, 1000)  // 1000 milisegundos de retraso
        }
    }

    private fun iaMove() {
        // Verificar si el juego sigue activo antes de que la IA haga su movimiento
        if (!gameActive) return

        // Encontrar una celda vacía aleatoriamente
        val emptyCells = gameState.withIndex().filter { it.value == -1 }.map { it.index }

        // Si hay celdas vacías, hacer un movimiento aleatorio
        if (emptyCells.isNotEmpty()) {
            val randomIndex = Random.nextInt(emptyCells.size)
            val iaMoveIndex = emptyCells[randomIndex]

            // Actualizar el estado del juego para la IA
            gameState[iaMoveIndex] = activePlayer

            // Actualizar el botón correspondiente en la interfaz
            val buttons = arrayOf(
                binding.button0, binding.button1, binding.button2,
                binding.button3, binding.button4, binding.button5,
                binding.button6, binding.button7, binding.button8
            )

            buttons[iaMoveIndex].text = "X"  // La IA siempre es "X"
            iaMoves++

            // Actualizar el contador de movimientos en la interfaz
            updateMoveCount()

            // Comprobar si la IA ha ganado
            if (checkForWinner()) return

            // Cambiar de nuevo al jugador
            activePlayer = 0
        }
    }

    private fun updateMoveCount() {
        // Actualizar el TextView del jugador y de la IA con los movimientos realizados
        binding.playerTextView.text = "Jugador\n$playerMoves"
        binding.iaTextView.text = "IA\n$iaMoves"
    }

    private fun checkForWinner(): Boolean {
        for (winningPosition in winningPositions) {
            val (a, b, c) = winningPosition

            // Si los tres valores son iguales y no son -1, tenemos un ganador
            if (gameState[a] == gameState[b] && gameState[b] == gameState[c] && gameState[a] != -1) {
                gameActive = false

                val winner = if (gameState[a] == 0) "O" else "X"
                Toast.makeText(this, "¡$winner ha ganado!", Toast.LENGTH_SHORT).show()

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

    // Método para reiniciar el juego
    private fun resetGame() {
        gameState.fill(-1)
        gameActive = true
        activePlayer = 0
        playerMoves = 0
        iaMoves = 0

        // Limpiar el texto de los botones
        binding.button0.text = ""
        binding.button1.text = ""
        binding.button2.text = ""
        binding.button3.text = ""
        binding.button4.text = ""
        binding.button5.text = ""
        binding.button6.text = ""
        binding.button7.text = ""
        binding.button8.text = ""

        // Reiniciar el contador de movimientos en los TextView
        updateMoveCount()
    }
}
