package com.example.game15.viewviewmodel.fragments.rules

import com.example.game15.databinding.FragmentRulesBinding
import com.example.game15.viewviewmodel.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RulesFragment : BaseFragment<FragmentRulesBinding, RulesViewModel>() {

    override val viewModel: RulesViewModel by viewModel()

    override fun getViewBinding() = FragmentRulesBinding.inflate(layoutInflater)

}