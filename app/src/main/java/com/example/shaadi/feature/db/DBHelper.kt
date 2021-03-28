package com.example.shaadi.feature.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shaadi.feature.MatchStatus
import com.example.shaadi.feature.contract.*
import kotlin.math.PI

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.name?.first )
        values.put(LAST_NAME, user.name?.last )
        values.put(USER_EMAIL, user.email)
        values.put(GENDER, user.gender )
        values.put(PICTURE, user.picture?.medium)
        values.put(USER_NAME,user.login?.username)
        values.put(AGE,user.dob?.age)
        values.put(STREET_NO,user.location?.street?.number)
        values.put(STREET_NAME,user.location?.street?.name)
        values.put(CITY,user.location?.city)
        values.put(STATE,user.location?.state)
        values.put(COUNTRY,user.location?.country)
        values.put(STATUS,MatchStatus.NotResponded.toString())

        db.insert(TABLE_NAME, null, values)
        db.close()
        return true
    }

    fun updateUser(user: UserModel,status:String): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.name?.first )
        values.put(LAST_NAME, user.name?.last )
        values.put(USER_EMAIL, user.email)
        values.put(GENDER, user.gender )
        values.put(PICTURE, user.picture?.medium)
        values.put(USER_NAME,user.login?.username)
        values.put(AGE,user.dob?.age)
        values.put(STREET_NO,user.location?.street?.number)
        values.put(STREET_NAME,user.location?.street?.name)
        values.put(CITY,user.location?.city)
        values.put(STATE,user.location?.state)
        values.put(COUNTRY,user.location?.country)
        values.put(STATUS,status)
        val _success = db.update(TABLE_NAME, values, USER_NAME + "=?", arrayOf(user.login?.username))
        db.close()
        return Integer.parseInt("$_success") != -1
        return false
    }


    fun getAllUser(): List<UserModel> {

        val columns = arrayOf(USER_ID,FIRST_NAME, LAST_NAME, USER_EMAIL, GENDER, PICTURE, USER_NAME,AGE, STREET_NO, STREET_NAME,CITY, STATE, COUNTRY, STATUS)
        var userList = emptyList<UserModel>()

        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            null
        )         //The sort order
        if(cursor.moveToFirst())
        {
            while (cursor.moveToNext()) {
                val user = UserModel(
                    name = Name(first = cursor.getString(cursor.getColumnIndex(FIRST_NAME)),last = cursor.getString(cursor.getColumnIndex(
                        LAST_NAME)),title = ""),
                    email = cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                    gender = cursor.getString(cursor.getColumnIndex(GENDER)),
                    picture = Picture(medium = cursor.getString(cursor.getColumnIndex(PICTURE)),large = cursor.getString(cursor.getColumnIndex(
                        PICTURE)),thumbnail = cursor.getString(cursor.getColumnIndex(PICTURE))),
                        login = Login(username = cursor.getString(cursor.getColumnIndex(USER_NAME))),
                        location = Location(city = cursor.getString(cursor.getColumnIndex(CITY)),
                        state = cursor.getString(cursor.getColumnIndex(STATE)),
                        country = cursor.getString(cursor.getColumnIndex(COUNTRY)),
                                street = Street(name = cursor.getString(cursor.getColumnIndex(STREET_NAME)),
                                number = cursor.getInt(cursor.getColumnIndex(STREET_NO)))
                        ),
                        dob = DOB(age = cursor.getInt(cursor.getColumnIndex(AGE))),
                        status = cursor.getString(cursor.getColumnIndex(STATUS))

                )
                if(!userList.contains(user)){
                    userList = userList.plus(user)
                }
            }
        }
        cursor.close()
        db.close()
        return userList
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "User.db"
        val TABLE_NAME = "USER"
        val USER_ID = "USER_ID"
        val FIRST_NAME = "FIRST"
        val LAST_NAME = "LAST"
        val USER_EMAIL = "EMAIL"
        val GENDER = "GENDER"
        val PICTURE = "PICTURE"
        val USER_NAME = "USER_NAME"
        val AGE = "AGE"
        val STREET_NO = "STREET_NO"
        val STREET_NAME = "STREET_NAME"
        val CITY = "CITY"
        val STATE = "STATE"
        val COUNTRY = "COUNTRY"
        val STATUS = "STATUS";

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FIRST_NAME + " TEXT NOT NULL," +
                    LAST_NAME + " TEXT NOT NULL," +
                    USER_EMAIL + " TEXT NOT NULL," +
                    GENDER + " TEXT NOT NULL," +
                    PICTURE + " TEXT NOT NULL," +
                    USER_NAME + " TEXT NOT NULL," +
                    AGE + " INTEGER NULL," +
                    STREET_NO + " INTEGER NULL," +
                    STREET_NAME + " TEXT NOT NULL," +
                    CITY + " TEXT NOT NULL," +
                    STATE + " TEXT NOT NULL," +
                    COUNTRY + " TEXT NOT NULL," +
                    STATUS + " TEXT NOT NULL)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME
    }
}