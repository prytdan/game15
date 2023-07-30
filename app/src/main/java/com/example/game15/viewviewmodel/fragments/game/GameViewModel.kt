package com.example.game15.viewviewmodel.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.game15.viewviewmodel.fragments.BaseViewModel
import com.example.game15.model.local.Direction
import com.example.game15.model.local.MoveLimitOfEmptyCell
import kotlinx.coroutines.delay
import java.util.Random

class GameViewModel(private val random: Random) : BaseViewModel() {

    private val mutableLiveDataForCellId = MutableLiveData<Int>()

    val liveDataForCellId: LiveData<Int> = mutableLiveDataForCellId

    private val mutableLiveDataForCounter = MutableLiveData<Int>()

    val liveDataForCounter: LiveData<Int> = mutableLiveDataForCounter

    private val mutableLiveDataForToastMessage = MutableLiveData<Unit>()

    val liveDataForToastMessage = mutableLiveDataForToastMessage

    private var counter = 0

    private var emptyCellCurrentIndex = 16

    suspend fun shuffle(n: Int) {
        counter = 0
        mutableLiveDataForCounter.value = counter
        val directions = arrayOf(Direction.RIGHT, Direction.LEFT, Direction.TOP, Direction.BOTTOM)
        var moves = 0
        while (moves < n) {
            val randomDirection = directions[random.nextInt(4)]
            if (isPossibleToMakeMove(randomDirection)) {
                delay(1)
                makeMove(randomDirection)
                moves++
            }
        }
    }

    fun tryToMakeMove(direction: Direction) {
        if (isPossibleToMakeMove(direction)) {
            makeMove(direction)
            increaseMovesCounter()
        } else {
            liveDataForToastMessage.value = Unit
        }
    }

    private fun isPossibleToMakeMove(direction: Direction): Boolean {
        val possibleToMove = when (direction) {
            Direction.RIGHT -> (emptyCellCurrentIndex - 1) % 4 != MoveLimitOfEmptyCell.RIGHT.value
            Direction.LEFT -> emptyCellCurrentIndex % 4 != MoveLimitOfEmptyCell.LEFT.value
            Direction.TOP -> emptyCellCurrentIndex < MoveLimitOfEmptyCell.TOP.value
            Direction.BOTTOM -> emptyCellCurrentIndex > MoveLimitOfEmptyCell.BOTTOM.value
        }
        return possibleToMove
    }

    private fun makeMove(direction: Direction) {
        val valueOfIndexChange = getValueOfIndexChange(direction)
        mutableLiveDataForCellId.value = emptyCellCurrentIndex - valueOfIndexChange
        emptyCellCurrentIndex -= valueOfIndexChange
    }

    private fun increaseMovesCounter() {
        counter++
        mutableLiveDataForCounter.value = counter
    }


    private fun getValueOfIndexChange(direction: Direction): Int {
        return when (direction) {
            Direction.RIGHT -> 1
            Direction.LEFT -> -1
            Direction.BOTTOM -> 4
            Direction.TOP -> -4
        }
    }
}
