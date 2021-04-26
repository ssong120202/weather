package com.shauna.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shauna.weather.R
import com.shauna.weather.service.model.Weather
import com.shauna.weather.service.repository.WeatherRepositoryImp
import com.shauna.weather.utils.observeOnce
import kotlinx.android.synthetic.main.overview_fragment.*


@Suppress("DEPRECATION")
class OverviewFragment : Fragment() {
    private lateinit var viewModel: OverviewViewModel
    private lateinit var repositoryImp: WeatherRepositoryImp
    private var weatherList: List<Weather> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(this, OverviewViewModel.Factory(it.application)).get(OverviewViewModel::class.java)
            repositoryImp = WeatherRepositoryImp.getInstance(it.application)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchService()
        observeViewModel()
        setUpUI()
    }

    private fun fetchService() {
        context?.let {
            viewModel.fetchService(it)
        }
    }

    private fun setUpUI() {
        search_info.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                context?.let { viewModel.fetchService(it, query ?: "") }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        //check box to control unit
        temp_check_box.setOnCheckedChangeListener { _, _ ->
            val mAdapter = WeatherAdapter(weatherList, temp_check_box.isChecked)
            weather_information_recycler_view.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun observeViewModel() {
        repositoryImp.weatherInfoList.observe(viewLifecycleOwner, {
            weatherList = it
            val mAdapter = WeatherAdapter(it, temp_check_box.isChecked)
            weather_information_recycler_view.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            city_name.text = it[0].city.name
        })
    }

}

