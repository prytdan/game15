package com.example.game15.viewviewmodel.fragments.game

import android.annotation.SuppressLint
import android.app.Dialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.game15.R
import com.example.game15.model.local.OnSwipeTouchListener
import com.example.game15.databinding.FragmentGameBinding
import com.example.game15.viewviewmodel.fragments.BaseFragment
import com.example.game15.model.local.Direction
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch


class GameFragment : BaseFragment<FragmentGameBinding, GameViewModel>() {

    override val viewModel: GameViewModel by viewModel()

    override fun getViewBinding() = FragmentGameBinding.inflate(layoutInflater)

    private lateinit var emptyCell: TextView

    private val args: GameFragmentArgs by navArgs()

    override fun initViews() {
        super.initViews()
        setupViews()
        subscribeToViewModel()
        startGame()
        setUpControls()
    }

    private fun setupViews() {
        emptyCell = binding.cell16
    }

    private fun setUpControls() {
        setOnTouchListenerToWholeScreen()
    }

    private fun subscribeToViewModel() {
        viewModel.liveDataForCellId.observe(this) {
            swapCells(it)
        }
        viewModel.liveDataForCounter.observe(this) {
            binding.numberOfTurnsCounter.text = it.toString()
        }
        viewModel.liveDataForToastMessage.observe(this) {
            showToast()
        }
    }

    private fun startGame() {
        val difficultyLevel = args.difficultyLevel
        lifecycleScope.launch {
            when (difficultyLevel) {
                1 -> viewModel.shuffle(50)
                2 -> viewModel.shuffle(150)
                3 -> viewModel.shuffle(500)
            }
            joinAll()
            checkForWin()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnTouchListenerToWholeScreen() {
        binding.gameField.setOnTouchListener(
            object : OnSwipeTouchListener(requireContext()) {
                override fun onSwipeRight() {
                    viewModel.tryToMakeMove(Direction.RIGHT)
                    checkForWin()
                }

                override fun onSwipeLeft() {
                    viewModel.tryToMakeMove(Direction.LEFT)
                    checkForWin()
                }

                override fun onSwipeTop() {
                    viewModel.tryToMakeMove(Direction.TOP)
                    checkForWin()
                }

                override fun onSwipeBottom() {
                    viewModel.tryToMakeMove(Direction.BOTTOM)
                    checkForWin()
                }

            })
    }

    private fun swapCells(cellId: Int) {
        val swappedCellId = resources.getIdentifier(
            "cell_$cellId",
            "id",
            requireContext().packageName
        )
        val swappedCell = requireView().findViewById<TextView>(swappedCellId)
        makeEmptyCellSwapped(swappedCell)
        makeSwappedCellEmpty(swappedCell)
        emptyCell = swappedCell
    }

    private fun makeSwappedCellEmpty(swappedCell: TextView) {
        swappedCell.text = ""
        swappedCell.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.transparent)
        )
    }

    private fun makeEmptyCellSwapped(swappedCell: TextView) {
        emptyCell.text = swappedCell.text
        emptyCell.background = swappedCell.background
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Impossible to move in this direction", Toast.LENGTH_SHORT)
            .show()
    }

    private fun checkForWin() {
        var win = true
        for (i in 1..16) {
            val cellId = resources.getIdentifier("cell_$i", "id", requireContext().packageName)
            val currentCell = requireView().findViewById<TextView>(cellId)
            if (currentCell.text.isEmpty()) {
                continue
            } else if (currentCell.text == i.toString()) {
                currentCell.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.ready_cell_form, null)
                currentCell.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.ready_cell)
                )
            } else {
                currentCell.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.unready_cell_form, null)
                currentCell.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                win = false
            }
        }
        if (win) {
            popUpWinGameWindow()
        }
    }

    private fun popUpWinGameWindow() {
        val dialogInflater = layoutInflater.inflate(R.layout.win_game_dialog, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogInflater)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dialog_background))
        dialog.setCancelable(false)
        dialog.show()
        dialog.findViewById<Button>(R.id.continue_button).setOnClickListener {
            dialog.dismiss()
            startGame()
        }
        dialog.findViewById<Button>(R.id.back_to_menu_button).setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(requireView())
                .navigate(R.id.action_gameFragment_to_menuFragment)
        }
    }
}