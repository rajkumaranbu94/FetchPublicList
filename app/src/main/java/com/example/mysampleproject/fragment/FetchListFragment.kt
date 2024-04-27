package com.example.mysampleproject.fragment

import ListViewResponseItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysampleproject.R
import com.example.mysampleproject.adapter.ListViewRecyclerAdapter
import com.example.mysampleproject.base.ScreenState
import com.example.mysampleproject.viewmodel.FetchResultFragmentEvent
import com.example.mysampleproject.viewmodel.FetchResultFragmentState
import com.example.mysampleproject.viewmodel.FetchResultViewModel
import com.example.mysampleproject.viewmodel.FetchResultViewModelFactory


class FetchListFragment : Fragment() {

    private val viewModel by viewModels<FetchResultViewModel> {
        FetchResultViewModelFactory()
    }

    private lateinit var recyclerview: RecyclerView;
    private lateinit var noDataFound: TextView
    private lateinit var progressBar: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.dispatchEvent(FetchResultFragmentEvent.GetResult())
        viewModel.state.observe(this, ::updateUi)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fetch_list,container, false)
        recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        noDataFound = view.findViewById<TextView>(R.id.tv_no_search_data)
        progressBar = view.findViewById<FrameLayout>(R.id.progressBar)
        return view
    }

    private fun updateUi(screenState: ScreenState<FetchResultFragmentState>?) {
        when (screenState) {
            is ScreenState.Render -> loadScreenState(screenState.renderState)
            is ScreenState.Loading -> setLoadingViewState(true)
            else -> {}
        }
    }

    private fun loadScreenState(renderState: FetchResultFragmentState) {
        setLoadingViewState(false)
        when (renderState) {
            is FetchResultFragmentState.FetchSearchResponseSuccess -> {
                setAdapter(renderState.fetchList)
            }
            else -> {}
        }
    }

    private fun showListOnUi(list: List<ListViewResponseItem>) {
        if (list.isEmpty()) {
            showNoResultUi()
        } else {
            recyclerview.visibility = View.VISIBLE
            noDataFound.visibility = View.GONE
            setAdapter(list)
        }
    }

    private fun showNoResultUi() {
        recyclerview.visibility = View.GONE
        noDataFound.visibility = View.VISIBLE
    }

    fun setLoadingViewState(showing: Boolean) {
        if (showing) {
            //progressBar.hideKeyboard()
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun setAdapter(fetchList: List<ListViewResponseItem>) {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.HORIZONTAL
            )
        )
        val adapter = ListViewRecyclerAdapter(fetchList) {
            val bundle = Bundle()
            bundle.putParcelable("fetchdata", it)
            findNavController().navigate(R.id.action_fetchResultFragment_to_fetchListDetailFragment)
        }
        recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}