package com.amir.testapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amir.testapp.R
import com.amir.testapp.data.model.Content
import com.amir.testapp.databinding.FragmentContentsListBinding
import com.amir.testapp.ui.adapter.ContentsAdapter
import com.amir.testapp.ui.viewModel.ContentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentsListFragment : Fragment() {

    private val RECYCLER_STATE = "recyclerState"
    private var favoriteList: List<Content> = listOf()
    private lateinit var binding: FragmentContentsListBinding
    private lateinit var viewModel: ContentViewModel
    private lateinit var contentsAdapter: ContentsAdapter
    private val saveBundle by lazy { Bundle() }

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

        if (saveBundle != null && saveBundle.containsKey(RECYCLER_STATE)){
            setupAdapter()
            setupRecycler()
            setupObserver()
        }else{
            setupAdapter()
            setupRecycler()
            setupViewModel()
            setupObserver()
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (saveBundle != null && saveBundle.containsKey(RECYCLER_STATE)){
            binding.contentsRecycler.layoutManager?.onRestoreInstanceState(saveBundle.getParcelable(RECYCLER_STATE))
        }
    }

    private fun setupAdapter() {
        contentsAdapter = ContentsAdapter().also {
            it.onItemClick = {
                saveBundle.putParcelable(
                    RECYCLER_STATE,
                    binding.contentsRecycler.layoutManager?.onSaveInstanceState()
                )
                val bundle = Bundle()
                bundle.putInt("contentId", it)
                findNavController().navigate(R.id.contentShowFragment, bundle)
            }

            it.onFavoriteItemClick = { position, content ->

                if (content.isFavorite) {
                    viewModel.deleteFavorite(content)
                    Toast.makeText(context, "un favorite", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insertFavorite(content.apply {
                        this.isFavorite = true
                    })
                    Toast.makeText(context, "favorite", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun setupRecycler() {
        binding.contentsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            adapter = contentsAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
        viewModel.fetchContents()
    }

    private fun setupObserver() {
        viewModel.getFavoriteContents().observe(viewLifecycleOwner, Observer {
            favoriteList = it
        })

        viewModel.getContents().observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                binding.progressBar.visibility = View.GONE
                contentsAdapter.submitData(it)
            }
        })
    }

}