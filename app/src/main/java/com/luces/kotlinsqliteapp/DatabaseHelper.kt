package com.luces.kotlinsqliteapp

import android.annotation.SuppressLint
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

    @SuppressLint("Range")
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
        user.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_USER_Id)))
        user.setName(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_Name)))
        user.setUsername(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_Username)))
        user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_Password)))
        cursor.close()
        return user
    }

    @SuppressLint("Range")
    fun getListIdsUser():ArrayList<Int>{
        var list_ids:ArrayList<Int> = ArrayList()

        var selectQuery:String=""
        selectQuery = "SELECT * FROM ${User.TABLE_NAME_User}"
        var db:SQLiteDatabase = this.writableDatabase
         var cursor:Cursor=db.rawQuery(selectQuery,null)

        if(cursor.moveToFirst()){
            do {
                var int_id:Int=cursor.getInt(cursor.getColumnIndex(User.COLUMN_USER_Id))
                list_ids.add(int_id)
            }while (cursor.moveToNext())
        }
        db.close()
        return list_ids
    }

    @SuppressLint("Range")
    fun getAllUsers(sort:String):ArrayList<User>{
        var users:ArrayList<User> = ArrayList()

        var selectQuery:String=""
        if (sort.equals("nume")) {
            selectQuery = "SELECT  * FROM " + User.TABLE_NAME_User + " ORDER BY " +
                    User.COLUMN_USER_Name + " ASC";
        } else if (sort.equals("username")) {
            selectQuery = "SELECT  * FROM ${User.TABLE_NAME_User} ORDER BY ${User.COLUMN_USER_Username} ASC";
        }
        var db:SQLiteDatabase=this.writableDatabase
        var cursor:Cursor = db.rawQuery(selectQuery,null)

        if(cursor.moveToFirst()){
            do{
                var user:User= User()
                user.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_USER_Id)))
                user.setName(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_Name)))
                user.setUsername((cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_Username))))
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_Password)))

                users.add(user)
            } while (cursor.moveToNext())
        }
        db.close()
        return users
    }

    fun getUserCount():Int{
        var countQuery:String="SELECT * FROM ${User.TABLE_NAME_User}"
        var db:SQLiteDatabase=this.readableDatabase
        var cursor:Cursor=db.rawQuery(countQuery,null)

        var count:Int=cursor.count
        cursor.close()
        return count
    }

    fun updateUser(user:User): Int {
        var db:SQLiteDatabase=this.writableDatabase

        var values:ContentValues= ContentValues()
        values.put(User.COLUMN_USER_Name,user.getName())
        values.put(User.COLUMN_USER_Username,user.getUsername())
        values.put(User.COLUMN_USER_Password,user.getPassword())
        // id selected
        return db.update(User.TABLE_NAME_User,values,User.COLUMN_USER_Id+" = ?",arrayOf(valueOf(user.getId())))
    }

    fun deleteUser(user:User){
        var db:SQLiteDatabase=this.writableDatabase
        db.delete(User.TABLE_NAME_User,User.COLUMN_USER_Id+" = ?",arrayOf(valueOf(user.getId())))
        db.close()
    }


    companion object{
        private val DATABASE_VERSION:Int=1
        private val DATABASE_NAME:String="kotlin_sqlite_db"



    }
}