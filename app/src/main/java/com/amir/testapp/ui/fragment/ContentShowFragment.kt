package com.amir.testapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.navArgs
import com.amir.testapp.R
import com.amir.testapp.databinding.FragmentContentsListBinding
import com.amir.testapp.databinding.FragmentShowContentBinding
import com.amir.testapp.ui.adapter.ContentsAdapter
import com.amir.testapp.ui.viewModel.ContentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentShowFragment : Fragment() {

    private lateinit var binding: FragmentShowContentBinding
    private lateinit var viewModel: ContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_content, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        with(binding) {
            viewModel = this@ContentShowFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
        viewModel.fetchContent(arguments?.getInt("contentId", 0)!!)
    }

}