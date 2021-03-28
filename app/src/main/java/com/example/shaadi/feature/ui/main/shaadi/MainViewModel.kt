package com.example.shaadi.feature.ui.main.shaadi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shaadi.feature.contract.UserModel
import com.example.shaadi.feature.db.DBHelper
import com.example.shaadi.feature.network.ShaadiApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val shaadiApi: ShaadiApi,private val dbHelper: DBHelper) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val users = MutableLiveData<List<UserModel>>()
    fun getUsers(): LiveData<List<UserModel>> {return users}

    fun fetchData(noOfUsers:Int){
        val users = dbHelper.getAllUser()
        if(users.isNullOrEmpty()) {
            val subscription = shaadiApi.getProfiles(noOfUsers).subscribeOn(Schedulers.io()).subscribe({
                updateDB(it.results)
                handleUsers(it.results)
            }, {
            })
            compositeDisposable.add(subscription)
        }else{
            handleUsers(users)
        }
    }

    private fun handleUsers(response:List<UserModel>){
        this.users.postValue(response)
    }

    private fun updateDB(users:List<UserModel>){
        for (user in users){
            dbHelper.insertUser(user)
        }
    }

    fun updateUser(user:UserModel,status:String){
       dbHelper.updateUser(user,status)
    }

    private fun updateUserDeclined(users:List<UserModel>){
        for (user in users){
            dbHelper.insertUser(user)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}