package com.example.mvvmsample.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsample.R
import com.example.mvvmsample.data.db.entities.Quote
import com.example.mvvmsample.util.Coroutines
import com.example.mvvmsample.util.hide
import com.example.mvvmsample.util.show
import com.xwray.groupie.GroupAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*

class QuotesFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(QuotesViewModel::class.java)
        bindUI()


    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        val quotes= viewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(quoteItem)
        }

        recycler_view.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=mAdapter
         }
    }

    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }

}