package ancic.karim.weatherancic.feature.main

import ancic.karim.weatherancic.R
import ancic.karim.weatherancic.databinding.ActivityMainBinding
import ancic.karim.weatherancic.extensions.nonNull
import ancic.karim.weatherancic.utils.LocationUtil
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
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
            viewModel.location.value = it
        }

        observeCity()
    }

    private fun observeCity() {
        viewModel.city.nonNull().observe(this) {
            binding.searchCity.setQuery(it, false)
        }
    }
}