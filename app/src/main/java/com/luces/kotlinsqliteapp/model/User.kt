package com.luces.kotlinsqliteapp.model

public class User {

    private var id:Int = 0
    private var name:String=""
    private var username:String=""
    private var password:String=""

    fun User(id:Int,name:String,username:String,password:String){
        this.id=id
        this.name=name
        this.username=username
        this.password=password
    }

    fun getId():Int{return this.id}
    fun setId(id:Int){this.id=id}

    fun getName():String{return this.name}
    fun setName(name:String){this.name=name}

    fun getUsername():String{return this.username}
    fun setUsername(username:String){this.username=username}

    fun getPassword():String{return this.password}
    fun setPassword(password:String){this.password=password}

    companion object{
        var TABLE_NAME_User:String="users"

        var COLUMN_USER_Id:String="id"
        var COLUMN_USER_Name:String="name"
        var COLUMN_USER_Username:String="username"
        var COLUMN_USER_Password:String="password"

        var CREATE_TABLE_User:String="CREATE TABLE IF NOT EXISTS $TABLE_NAME_User(" +
                "$COLUMN_USER_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USER_Name TEXT," +
                "$COLUMN_USER_Username TEXT," +
                "$COLUMN_USER_Password TEXT"



    }
}