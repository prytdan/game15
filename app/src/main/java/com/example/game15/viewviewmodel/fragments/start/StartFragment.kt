package com.example.game15.viewviewmodel.fragments.start


import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.game15.databinding.FragmentStartBinding
import com.example.game15.viewviewmodel.fragments.BaseFragment

class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel>() {

    override val viewModel: StartViewModel by viewModel()

    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)


}