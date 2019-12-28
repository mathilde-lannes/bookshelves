package com.maty.android.bookshelves.ui.addBook.suggestions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.intmainreturn00.grapi.SearchResult
import com.intmainreturn00.grapi.grapi
import kotlinx.android.synthetic.main.item_suggestion.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SuggestionAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val allSuggestions: List<SearchResult>):
        ArrayAdapter<SearchResult>(context, layoutResource, allSuggestions),
        Filterable {
    private var suggestions: List<SearchResult> = allSuggestions

    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getItem(p0: Int): SearchResult? {
        return suggestions[p0]

    }
    override fun getItemId(p0: Int): Long {
        // Or just return p0
        return suggestions[p0].workId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        val suggestion = suggestions[position]
        view.author.text = suggestion.authorName
        view.title.text = suggestion.bookTitle
        Glide.with(context).load(suggestion.imageUrl).into(view.cover)

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                suggestions = if (filterResults.values != null) filterResults.values as List<SearchResult> else listOf()
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()
                val filterResults = FilterResults()

                if (queryString.isNullOrEmpty()) {
                    return filterResults
                }

                runBlocking {
                    val sugg = async { grapi.getSearchResults(queryString).results }
                    filterResults.values = sugg.await()
                }
                return filterResults
            }

        }
    }
}