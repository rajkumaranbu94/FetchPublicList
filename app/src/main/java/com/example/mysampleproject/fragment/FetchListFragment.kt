package com.example.mysampleproject

import ListViewResponseItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    lateinit var recyclerview: RecyclerView;
    lateinit var noDataFound: TextView
    lateinit var progressBar: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchResultListLive.observe(::getLifecycle, ::updateList)
        viewModel.dispatchEvent(FetchResultFragmentEvent.GetResult())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fetch_list, null)
        recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        noDataFound = view.findViewById<TextView>(R.id.tv_no_search_data)
        progressBar = view.findViewById<FrameLayout>(R.id.progressBar)
        return view
    }

    private fun updateList(list: MutableList<ListViewResponseItem>) {
        setLoadingViewState(false)
        showListOnUi(list)
    }

    private fun showListOnUi(list: MutableList<ListViewResponseItem>) {
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

    private fun updateUi(screenState: ScreenState<FetchResultFragmentState>?) {
        when (screenState) {
            is ScreenState.Render -> loadScreenState(screenState.renderState)
            is ScreenState.Loading -> setLoadingViewState(true)
            null -> TODO()
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

    fun setLoadingViewState(showing: Boolean) {
        if (showing) {
            //progressBar.hideKeyboard()
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun setAdapter(fetchList: MutableList<ListViewResponseItem>) {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.HORIZONTAL
            )
        )
        val adapter = ListViewRecyclerAdapter(fetchList) {
            Toast.makeText(context, it.userId, Toast.LENGTH_SHORT).show()
        }
        recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}