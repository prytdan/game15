package com.example.game15.viewviewmodel.fragments.menu

import androidx.activity.addCallback
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.Navigation
import com.example.game15.R
import com.example.game15.databinding.FragmentMenuBinding
import com.example.game15.viewviewmodel.fragments.BaseFragment

class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {

    override val viewModel: MenuViewModel by viewModel()

    override fun getViewBinding() = FragmentMenuBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        binding.easyLevelButton.setOnClickListener {
            onDifficultyButtonClick(1)
        }
        binding.mediumLevelButton.setOnClickListener {
            onDifficultyButtonClick(2)
        }
        binding.hardLevelButton.setOnClickListener {
            onDifficultyButtonClick(3)
        }
        binding.questionButton.setOnClickListener {
            onQuestionButtonClick()
        }
        overrideOnBackPressed()
    }

    private fun onDifficultyButtonClick(difficultyLevel: Int) {
        val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(difficultyLevel)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun onQuestionButtonClick() {
        Navigation.findNavController(requireView())
            .navigate(R.id.action_menuFragment_to_rulesFragment)
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // do nothing
        }
    }

}