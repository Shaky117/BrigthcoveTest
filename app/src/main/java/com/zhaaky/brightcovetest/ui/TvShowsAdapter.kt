package com.zhaaky.brightcovetest.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zhaaky.brightcovetest.R
import com.zhaaky.brightcovetest.models.TvShow
import java.util.Locale

class TvShowsAdapter(
    private val context: Context,
    private val originalShows: List<TvShow>
) : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>(), Filterable {

    private var shows: MutableList<TvShow> = originalShows.toMutableList()
    private var copyShows: MutableList<TvShow> = originalShows.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tv_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = shows[position]
        holder.bind(show)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(show: TvShow) {
            itemView.findViewById<TextView>(R.id.tvTitle).text = show.show.name
            itemView.findViewById<TextView>(R.id.tvTime).text = show.airtime
            val ivMovieImg = itemView.findViewById<ImageFilterView>(R.id.ivMovieImg)

            val imgUrl = show.show.image?.original
            imgUrl?.let {
                Picasso.get().load(it).into(ivMovieImg)
            } ?: run {
                Picasso.get().load(R.drawable.tvlogo_placeholder).into(ivMovieImg)
            }

            itemView.setOnClickListener {
                listener?.onItemClick(show)
            }
        }
    }

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(show: TvShow)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setShows(shows: List<TvShow>) {
        this.shows = shows.toMutableList()
        this.copyShows = shows.toMutableList()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<TvShow>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(copyShows)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                    for (show in copyShows) {
                        if (show.show.name.lowercase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(show)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                shows = (results?.values as? List<TvShow> ?: listOf()).toMutableList()
                notifyDataSetChanged()
            }
        }
    }
}
