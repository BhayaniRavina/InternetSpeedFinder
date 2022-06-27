package com.example.internetspeedfinder

import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.internetspeedfinder.Database.Local.InternetDB
import com.example.internetspeedfinder.Database.Local.InternetTable
import com.example.internetspeedfinder.Database.Local.Repositry.InternetSpeedRepository
import com.example.internetspeedfinder.Services.FrquentlyCheckNetworkServices
import com.example.internetspeedfinder.customViewModels.MainActivityViewModel
import com.example.internetspeedfinder.customViewModels.ViewModelFactory
import com.example.internetspeedfinder.databinding.ActivityMainBinding
import com.example.internetspeedfinder.databinding.ActivityMainBindingImpl

class MainActivity : AppCompatActivity() {
    var frequentlyCheckNetworkServices: FrquentlyCheckNetworkServices? = null
    private lateinit var connection : ServiceConnection
    var bounded = false
    val TAG = MainActivity::class.java.simpleName

    private lateinit var tvCurrentSpeedValue : TextView
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_main)

        connection = object : ServiceConnection{
            override fun onServiceConnected(componentName : ComponentName?, service: IBinder?) {
                Log.d(TAG, "Service connected.")
                frequentlyCheckNetworkServices = (service as FrquentlyCheckNetworkServices.RandomNumberGeneratorServiceBinder).service
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d(TAG,"Service disconnected.")
                frequentlyCheckNetworkServices = null
            }
        }

        val btnCurrentSpeedPrint : Button = findViewById(R.id.btnCurrentSpeedPrint);
        tvCurrentSpeedValue = findViewById(R.id.tvCurrentSpeedValue);

        val dao = InternetDB.getInstance(application).internetDAOQueries
        val repository = InternetSpeedRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(MainActivityViewModel::class.java)
        binding.mainActivityViewModel = viewModel
        binding.lifecycleOwner = this

        RetriveInternetSpeed()

        callbindSerive()
        getRandomNumber();
        //callunbindSerive()

    }

    private fun getRandomNumber() {
        if(!bounded){
            Log.d(TAG,getString(R.string.service_not_bound))
        }else{
            Log.d(TAG,frequentlyCheckNetworkServices?.randomNumber.toString())
        }
    }

    override fun onDestroy() {
        unbindSafely()
        super.onDestroy()
    }
    private fun unbindSafely() {
        if (bounded) {
            unbindService(connection)
            bounded = false
        }
    }
    private fun callunbindSerive() {
        unbindSafely()
    }

    private fun callbindSerive() {
        if(!bounded){
            bindService(Intent(this, FrquentlyCheckNetworkServices::class.java),connection,Context.BIND_AUTO_CREATE)
        var test = frequentlyCheckNetworkServices?.randomNumber.toString()
        Toast.makeText(applicationContext,"test random value : "+ test,Toast.LENGTH_LONG).show()
            bounded = true
        }
    }
    private fun insertData(currentSpeed: String) {
        val internetTable : InternetTable = InternetTable(0,currentSpeed.toDouble())
        viewModel.insert(internetTable)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun RetriveInternetSpeed() {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val downloadSpeed = (networkCapabilities?.linkDownstreamBandwidthKbps)?.div(1000)

        //tvCurrentSpeedValue.text = downloadSpeed.toString()
        Toast.makeText(applicationContext,"Speed : " + downloadSpeed.toString(),Toast.LENGTH_LONG).show()

        insertData(downloadSpeed.toString())
        displayData()
    }


    private fun displayData() {

        viewModel.getAllData.observe(this,{
            if(it.size > 0){
                binding.tvCurrentSpeedValue.text = it[0].speed.toString()
                Toast.makeText(application,"Current Speed : " + it[0].speed.toString(),Toast.LENGTH_LONG).show()
            }
        })
        viewModel.getMaximumSpeed.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            if(it.size > 0){
                binding.tvMaximumSpeedValue.text = it[0].speed.toString()
                Toast.makeText(application,"Max Speed : " + it[0].speed.toString(),Toast.LENGTH_LONG).show()
            }

        })
        viewModel.getMinSpeed.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            if(it.size > 0){
                binding.tvMinimumSpeedValue.text = it[0].speed.toString()
                Toast.makeText(application,"Min Speed : " + it[0].speed.toString(),Toast.LENGTH_LONG).show()
            }
        })
        viewModel.getMeanSpeed.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            if(it.size > 0){
                binding.tvMeanSpeedValue.text = it[0].speed.toString()
                Toast.makeText(application,"Mean Speed : " + it[0].speed.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }
}