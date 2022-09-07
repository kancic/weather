package ancic.karim.weatherancic.feature.forecast

import ancic.karim.weatherancic.R
import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.databinding.FragmentForecastBinding
import ancic.karim.weatherancic.feature.main.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException

@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private lateinit var binding: FragmentForecastBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState).run {
            FragmentForecastBinding.inflate(inflater, container, false)
        }.also {
            binding = it
        }.run {
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.forecast.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) result.getOrNull()?.let { forecast ->
                setupForecast(forecast)
            } else when (result.exceptionOrNull()) {
                is UnknownHostException -> Snackbar.make(view, R.string.error_no_internet, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupForecast(forecast: List<Forecast>) {
        var adapter = binding.recyclerView.adapter as? ForecastAdapter
        if (adapter == null) {
            adapter = ForecastAdapter(forecast)
            binding.recyclerView.adapter = adapter
        } else {
            adapter.update(forecast)
        }
    }
}