package com.luces.kotlinsqliteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luces.kotlinsqliteapp.model.User
import java.lang.String.valueOf

class DatabaseHelper(context: Context, factory:SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context,DATABASE_NAME,factory,
    DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        //Create SQLite tables
        db?.execSQL(User.CREATE_TABLE_User)

        db?.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop older table if exist
        db?.execSQL("DROP TABLE IF EXISTS ${User.TABLE_NAME_User}")

        db?.execSQL("PRAGMA foreign_keys=ON")

        // Create tables again
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys=ON")
        // Cada vez que me conecto a la base de datos, Foreign_keys está desactivado de forma predeterminada
    }

    fun insertUser(name:String,username:String,password:String):Long{
        var db:SQLiteDatabase=this.writableDatabase

        var values:ContentValues = ContentValues()
        // 'id' will be inserted automatically.
        values.put(User.COLUMN_USER_Name,name)
        values.put(User.COLUMN_USER_Username,username)
        values.put(User.COLUMN_USER_Password,password)

        // insert row
        var id:Long=db.insert(User.TABLE_NAME_User,null,values)

        // close db connection
        db.close()

        // return inserted id
        return id
    }

    fun getUser(id:Long):User{

        var db:SQLiteDatabase=this.readableDatabase

        var cursor:Cursor=db.query(User.TABLE_NAME_User,
            arrayOf(
                User.COLUMN_USER_Id,
                User.COLUMN_USER_Name,
                User.COLUMN_USER_Username,
                User.COLUMN_USER_Password),
            User.COLUMN_USER_Id+"=?",
            arrayOf<String>(valueOf(id)),
            null,
            null,
            null,
            null
        )
        if(cursor!=null)
            cursor.moveToFirst()
        var user:User=User()
        //var user:User=User(cursor.getInt(cursor.getColumnIndex(User.COLUMN_USER_Id)))
        return user
    }

    fun updateUser(db:SQLiteDatabase){
        var db:SQLiteDatabase=this.writableDatabase

        var values:ContentValues= ContentValues()
        // id selected

    }


    companion object{
        private val DATABASE_VERSION:Int=1
        private val DATABASE_NAME:String="kotlin_sqlite_db"



    }
}