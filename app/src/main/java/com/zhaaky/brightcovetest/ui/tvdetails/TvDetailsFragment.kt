package com.zhaaky.brightcovetest.ui.tvdetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.zhaaky.brightcovetest.R
import com.zhaaky.brightcovetest.databinding.FragmentTvDetailsBinding
import com.zhaaky.brightcovetest.models.TvShow


class TvDetailsFragment : Fragment() {

    private var _binding: FragmentTvDetailsBinding? = null
    private val binding:  FragmentTvDetailsBinding get() = _binding!!

    lateinit var show : TvShow

    private val args : TvDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvDetailsBinding.inflate(inflater, container, false)

        show = args.showDetails

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        setUpViews()
    }

    private fun setUpViews() {

        binding.tvTitle.text = show.show.name
        binding.tvSummary.text = Html.fromHtml(show.show.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvLanguage.text = show.show.language
        binding.tvStatus.text = show.show.status
        binding.tvPremiered.text = show.show.premiered
        binding.tvTime.text = show.show.schedule.time

        var dayString = show.show.schedule.days.joinToString(separator = ", ", postfix = "")

        if (dayString.isEmpty()){
            dayString = "N/A"
        }

        binding.tvDays.text = dayString

        val imgUrl = show.show.image?.medium

        imgUrl?.let {
            Picasso.get().load(it).into(binding.ivPoster)
        } ?: run {
            Picasso.get().load(R.drawable.tvlogo_placeholder).into(binding.ivPoster)
        }
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}