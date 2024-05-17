package com.zhaaky.brightcovetest.ui.tvschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhaaky.brightcovetest.MainActivity
import com.zhaaky.brightcovetest.databinding.FragmentTvScheduleBinding
import com.zhaaky.brightcovetest.models.TvShow
import com.zhaaky.brightcovetest.ui.TVShowViewModel
import com.zhaaky.brightcovetest.ui.TvShowsAdapter

class TvScheduleFragment : Fragment(), TvShowsAdapter.OnItemClickListener {

    private var _binding: FragmentTvScheduleBinding? = null
    private val binding: FragmentTvScheduleBinding get() = _binding!!

    lateinit var tvViewModel : TVShowViewModel
    lateinit var adapter : TvShowsAdapter

    private val mainActivity : MainActivity by lazy{ requireActivity() as MainActivity}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        setUpRV()
        addObservers()
    }

    private fun setUpRV() {
        adapter = TvShowsAdapter(requireContext(), listOf())
        binding.rvSchedule.adapter = adapter
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addListeners() {
        binding.svMovies.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        adapter.setOnItemClickListener(this)
    }

    private fun addObservers() {
        tvViewModel.shows.observe(viewLifecycleOwner, Observer {
            updateViews(it)
        })
    }

    private fun updateViews(showList: List<TvShow>) {

        adapter.setShows(showList)
        addListeners()
    }

    override fun onItemClick(show: TvShow) {
        val action = TvScheduleFragmentDirections.actionScheduleFragmentToDetailsFragment(show)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}