package com.sakayta.budgetapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sakayta.budgetapp.activity.main.HomeViewModel
import com.sakayta.budgetapp.activity.main.home.Home
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*


class MainActivity :
    BaseActivity(),
    Home.HomeInteractionListener{

    lateinit var viewModel: HomeViewModel


    override fun displayProgressBar(bool: Boolean) {
        if(bool){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.GONE
        }
    }

    override fun isLoading(flag: Boolean) {
       displayProgressBar(bool = flag)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set the toolbar as support action bar
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            // Set toolbar title/app title
            title = "Account List"

            // Display the app icon in action bar/toolbar
            setDisplayShowHomeEnabled(false)
        }
        viewModel =   ViewModelProvider(this).get(HomeViewModel::class.java)

    }

}
