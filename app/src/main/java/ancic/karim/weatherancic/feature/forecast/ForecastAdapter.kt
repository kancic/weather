package ancic.karim.weatherancic.feature.forecast

import ancic.karim.weatherancic.R
import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.databinding.ItemForecastBinding
import ancic.karim.weatherancic.extensions.capitalize
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ForecastAdapter(private var items: List<Forecast>) : RecyclerView.Adapter<ForecastViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return items[position].date.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    fun update(items: List<Forecast>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class ForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private val datePattern = "dd.MM.yyyy."

    fun onBind(item: Forecast) {
        binding.labelDate.text = getFormattedDate(item.date)
        binding.labelCurrentTemperature.apply {
            text = context.getString(R.string.forecast_label_current_temperature, item.temperatureNow)
            isVisible = !item.temperatureNow.isNullOrEmpty()
        }
        binding.labelMinTemperature.text = context.getString(R.string.forecast_label_min_temperature, item.temperatureMin)
        binding.labelMaxTemperature.text = context.getString(R.string.forecast_label_max_temperature, item.temperatureMax)
    }

    private fun getFormattedDate(localDate: LocalDate) = if (localDate.isEqual(LocalDate.now())) {
        context.getString(R.string.forecast_label_today, localDate.format(DateTimeFormatter.ofPattern(datePattern)))
    } else {
        localDate.format(DateTimeFormatter.ofPattern("EEEE, $datePattern").withLocale(Locale("hr")))
    }.capitalize()
}
