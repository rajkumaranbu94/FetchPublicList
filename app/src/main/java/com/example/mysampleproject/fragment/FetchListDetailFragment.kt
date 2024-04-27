package com.example.mysampleproject.fragment

import ListViewResponseItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mysampleproject.R
/**
 * A simple [Fragment] subclass.
 * Use the [FetchListDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FetchListDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fetch_list_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fetchData: ListViewResponseItem? = this.arguments?.getParcelable("fetchData")
        view.findViewById<TextView>(R.id.txt_detail_title).text = fetchData?.title
        view.findViewById<TextView>(R.id.txt_detail_desc).text = fetchData?.body
    }
}