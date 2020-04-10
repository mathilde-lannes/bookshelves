package com.maty.android.bookshelves.ui.books.add.suggestions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.intmainreturn00.grapi.SearchResult
import com.intmainreturn00.grapi.grapi
import kotlinx.android.synthetic.main.item_suggestion.view.*
import kotlinx.coroutines.runBlocking

class SuggestionAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val allSuggestions: List<SearchResult>):
        ArrayAdapter<SearchResult>(context, layoutResource, allSuggestions),
        Filterable {
    private var suggestions: List<SearchResult> = allSuggestions
    private lateinit var autocomplete: EditText
    private lateinit var mLoadingIndicator: ProgressBar

    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getItem(p0: Int): SearchResult? {
        return suggestions[p0]

    }
    override fun getItemId(p0: Int): Long {
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

    fun setViews(view : EditText, progressBar : ProgressBar) {
        autocomplete = view
        mLoadingIndicator = progressBar
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                suggestions = if (filterResults.values != null) filterResults.values as List<SearchResult> else listOf()
                notifyDataSetChanged()
                mLoadingIndicator.visibility = View.GONE
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()
                val filterResults = FilterResults()

                if (queryString.isNullOrEmpty()) {
                    return filterResults
                }

                runBlocking {
                    val suggs = grapi.getSearchResults(queryString).results

                    if (autocomplete.text.toString() == charSequence) {
                        filterResults.values = suggs.take(5)
                    }
                }
                return filterResults
            }

        }
    }
}