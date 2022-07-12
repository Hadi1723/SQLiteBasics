package com.example.sqlbasics

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "students.db"
        private val DATABASE_VERSION = 3
        private val TBL_STUDENT = "tbl_student"
        private val ID = "id"
        private val NAME = "name"
        private val EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        // this statement is used to create the table within the database
            // Format: CREATE TABLE table_name (
                            //   column_1 data_type,
                            //   column_2 data_type,
                            //   column_3 data_type
                        //);
            // Parts:
                    // CREATE TABLE is a clause.
                    //table_name refers to the name of the table that the command is applied to.
                    //(column_1 data_type, column_2 data_type, column_3 data_type) is a parameter.


        // Constraints
        /*

        1. PRIMARY KEY columns can be used to uniquely identify the row. Attempts to insert a row with an identical value to a row already in the table will result in a constraint violation which will not allow you to insert the new row.

        2. UNIQUE columns have a different value for every row. This is similar to PRIMARY KEY except a table can have many different UNIQUE columns.

        3. NOT NULL columns must have a value. Attempts to insert a row without a value for a NOT NULL column will result in a constraint violation and the new row will not be inserted.

        4. DEFAULT columns take an additional argument that will be the assumed value for an inserted row if the new row does not specify a value for that column.

        Example:
         CREATE TABLE celebs (
            id INTEGER PRIMARY KEY,
            name TEXT UNIQUE,
            date_of_birth TEXT NOT NULL,
            date_of_death TEXT DEFAULT 'Not Applicable'
         );

         */

        val createTBlStudent = ("CREATE TABLE " + TBL_STUDENT
                + "(" + ID + " INTEGER PRIMARY KEY," + NAME
                + " TEXT," + EMAIL + " TEXT" + ")")
        db?.execSQL(createTBlStudent)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun insertStudent(std: StudentModel): Long {
        val dp = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)

        // The INSERT statement inserts a new row into a table.
        //parts of insert statement
            // clause: INSERT INTO ____ VALUES
            // table: table name
            // parameters: (the column names) and (the specfic values into each column)
        val success = dp.insert(TBL_STUDENT, null, contentValues)
        dp.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllStudent(): ArrayList<StudentModel> {
        val stdList: ArrayList<StudentModel> = arrayListOf()

        //types of select statements

        // 1. selecting everything from a relational database
            // Format: "SELECT * FROM ${table_name} "
        val selectQuery = "SELECT * FROM $TBL_STUDENT"

        // 2. selecting specific columns from a table (output will be table that only consist of specified columns [a subset of the actual table])
            // Format: "SELECT ${column_name} FROM ${table_name}"
        //val selectQuery2 = "SELECT $NAME FROM $TBL_STUDENT"


        // 3. selecting multiple columns from a table (output will be table that only consist of specified columns [a subset of the actual table])
        // Format: "SELECT ${column_name1, column_name2, ...} FROM ${table_name}"
        //val selectQuery2 = "SELECT $NAME, $ID FROM $TBL_STUDENT"


        //parts of select statement:
            //SELECT and FROM are the clauses here.
            //We are applying the command to the celebs table.
            // the column names are the parametrs


        //4. extra clauses for selecting statements
        // the "AS" clause renames a column of a table
        // the "DISTINCT" caluse returns all unique rows (optional)
        // the "WHERE" is used to filter rows
        // when using BETWEEN clause, the ending element is exclusive for text columns, date columns, or integer columns
        // when using the AND clause, we can use this keyword to act like a proper && comparisson operator inside a WHERE clause
        // when using the OR clause, we can use this keyword to act like a proper || comparisson operator inside a WHERE clause
        // the ORDER BY clause orders the tables based on given column name (adding the clause DESC will sort in descending order)
        // the LIMIT clause will set maximum limit for number of rows outputted using SELECT statement
            //Format: SELECT DISTINCT ${column_name} AS '${new_name}' FROM ${table_name} WHERE ${conditional_column_name} < 5;
            //Format: SELECT DISTINCT ${column_name} AS '${new_name}' FROM ${table_name} WHERE ${conditional_column_name} IS NULL;
            //Format: SELECT DISTINCT ${column_name} AS '${new_name}' FROM ${table_name} WHERE ${conditional_column_name} IS NOT NULL;
            //Format: SELECT DISTINCT ${column_name} AS '${new_name}' FROM ${table_name} WHERE ${conditional_column_name} BETWEEN 'D 'AND 'G';
            //Format: SELECT DISTINCT ${column_name} AS '${new_name}' FROM ${table_name} WHERE ${conditional_column_name} BETWEEN 1970 AND 1979;
            //Format: SELECT ${column_names} FROM ${table_name} ORDER BY ${order_column_name} DESC;
            //Format: SELECT ${column_names} FROM ${table_name} ORDER BY ${order_column_name};
            //Format: SELECT ${column_names} FROM ${table_name} ORDER BY ${column_names} DESC LIMIT 3


        // 5. selecting statemnet with the LIKE clause
            // Format:  SELECT * FROM ${table_name} WHERE ${column_name} LIKE 'Se_en';
                // Here, the all rows are selected where the column entry begins with "Se" and ends with "en"
            // Format: SELECT * FROM ${table_name} WHERE ${column_name} LIKE '%man%';
                // Here, the pattern states that the column entry must contain the string "man" anywhere
            // Format: SELECT * FROM ${table_name} WHERE ${column_name} LIKE '%man';
                // Here, the pattern states that the column entry must contain the string "man" only at the end of entry
            // Format: SELECT * FROM ${table_name} WHERE ${column_name} LIKE 'man%';
                // Here, the pattern states that the column entry must contain the string "man" only at the beginning of entry


        // 6. case selecting statement will create extra column
        /*
        Example:

            SELECT name,
                CASE
                    WHEN genre = 'romance' THEN 'Chill'
                    WHEN genre = 'comedy'  THEN 'Chill'
                ELSE 'Intense'
                END AS 'Mood'
            FROM movies;
         */

        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch(e: Exception) {
            db.execSQL(selectQuery)
            e.printStackTrace()
            return arrayListOf<StudentModel>()
        }

        var id: Int
        var name: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val std = StudentModel(id, name, email)
                stdList.add(std)

            } while(cursor.moveToNext())
        }

        return stdList
    }

    fun updateStudent(std: StudentModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)


        //Format: "UPDATE ${table_name} SET ${content_values} WHERE ${condition}"

        //Parts:
            // Clauses:
                // 1. UPDATE
                // 2. SET
                // 3. WHERE
            // Table: table_name
            // Parameters:
                // 1. content_values = new values for specified rows
                // 2. condition = what condition must be met for the row to be updated
                        // this parameter is optional and if not specified (null), all rows will be updated
        val success = db.update(TBL_STUDENT, contentValues, "name= '" + "${std.name}" + "'", null)

        val value = "name= '" + "${std.name}" + "'"
        Log.e(TAG, value)

        db.close()

        return success

    }

    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase

        //need these otherwise no output
        val contentValues = ContentValues()
        contentValues.put(ID, id)


        // Format: DELETE FROM ${table_name} WHERE ${condition}
        /*
        Parts:
              1. DELETE FROM is a clause that lets you delete rows from a table.
              2. table_name is the name of the table we want to delete rows from.
              3. WHERE is a clause that lets you select which rows you want to delete. Here we want to delete all of the rows where the twitter_handle column IS NULL.
              4. condition is the parameter and if value is null, all rows will be deleted

         */
        val success = db.delete(TBL_STUDENT,"id= $id", null)

        //val value = "name= '" + "${std.name}" + "'"
        //Log.e(TAG, value)

        db.close()

        return success

    }
}