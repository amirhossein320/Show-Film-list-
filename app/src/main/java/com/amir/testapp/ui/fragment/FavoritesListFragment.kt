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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amir.testapp.R
import com.amir.testapp.databinding.FragmentContentsListBinding
import com.amir.testapp.ui.adapter.ContentsAdapter
import com.amir.testapp.ui.adapter.FavoritesAdapter
import com.amir.testapp.ui.viewModel.ContentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesListFragment : Fragment() {

    private lateinit var binding: FragmentContentsListBinding
    private lateinit var viewModel: ContentViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contents_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecycler()
        setupViewModel()
        setupObserver()

    }

    private fun setupAdapter() {
        favoritesAdapter = FavoritesAdapter().also {
            it.onItemClick = {
                val bundle = Bundle()
                bundle.putInt("contentId", it)
                findNavController().navigate(R.id.contentShowFragment, bundle)
            }

            it.onFavoriteItemClick = {
                if (it.isFavorite) viewModel.deleteFavorite(it) else viewModel.insertFavorite(it.apply {
                    this.isFavorite = true
                })
            }
        }
    }

    private fun setupRecycler() {
        binding.contentsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            adapter = favoritesAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
        viewModel.fetchContents()
    }

    private fun setupObserver() {
        viewModel.getFavoriteContents().observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            favoritesAdapter.submitList(it)
        })
    }

}