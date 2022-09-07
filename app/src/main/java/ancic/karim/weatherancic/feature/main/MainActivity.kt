package ancic.karim.weatherancic.feature.main

import ancic.karim.weatherancic.R
import ancic.karim.weatherancic.databinding.ActivityMainBinding
import ancic.karim.weatherancic.extensions.nonNull
import ancic.karim.weatherancic.utils.LocationUtil
import android.app.SearchManager
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.CursorAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    @Inject lateinit var locationUtil: LocationUtil

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        setupActionBarWithNavController(navController)

        locationUtil.getLocation().nonNull().observe(this) {
            if (viewModel.location.value == null) {
                viewModel.location.value = it
            }
        }

        setupSearchCityInput()

        observeCity()
        observeSuggestionCities()
    }

    private fun setupSearchCityInput() {
        binding.searchCity.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchCity(query.trim())
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.searchCity(query.trim())
                    return true
                }

            })
            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    viewModel.selectSuggestion(position)
                    clearFocus()
                    return true
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    viewModel.selectSuggestion(position)
                    clearFocus()
                    return true
                }

            })
        }
    }

    private fun observeCity() {
        viewModel.city.nonNull().observe(this) {
            binding.searchCity.setQuery(it, false)
        }
    }

    private fun observeSuggestionCities() {
        viewModel.citySuggestions.observe(this) {
            setCitySuggestions(it)
        }
    }

    private fun setCitySuggestions(suggestions: List<String>) {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(android.R.id.text1)
        val cursorAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        binding.searchCity.suggestionsAdapter = cursorAdapter

        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
        suggestions.forEachIndexed { index, suggestion ->
            cursor.addRow(arrayOf(index, suggestion))
            cursorAdapter.changeCursor(cursor)
        }
    }
}