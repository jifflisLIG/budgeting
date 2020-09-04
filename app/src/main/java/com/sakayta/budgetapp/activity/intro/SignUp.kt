package com.sakayta.budgetapp.activity.intro

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.sakayta.budgetapp.R
import com.sakayta.budgetapp.model.User
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUp : Fragment() {

    lateinit var viewModel: IntroViewModel;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(IntroViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }


    fun save(){
        val _username = username.text.toString()
        val _password = password.text.toString()
        val _email = email.text.toString()
        when {
            TextUtils.isEmpty(_username) -> {
                Toast.makeText(context,"Username is required!",Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(_password) -> {
                Toast.makeText(context,"Password is required!",Toast.LENGTH_LONG).show()
            }
            else -> {
                var user = User(
                    username = _username,
                    password = _password,
                    email =  _email
                )
                viewModel.saveUser(user)
                requireActivity().finish()
            }
        }
    }

}
