package com.hikvision.mykotlintest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hikvision.mykotlintest.database.dao.UserDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.database.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataBaseActivity : AppCompatActivity() {
        private lateinit var createDatabase: Button
        private lateinit var delete: Button
        private lateinit var add: Button
        private lateinit var update: Button
        private lateinit var queryOne: Button
        private lateinit var queryAll: Button

        private lateinit var appDatabase: AppDatabase
        private lateinit var userDao: UserDao

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.database_practice)

                //userDao= AppDatabase.getDatabase(this).userDao()
                // Toast.makeText(this,"创建数据库成功",Toast.LENGTH_SHORT)



                //创建数据库，创建userDao
                createDatabase = findViewById(R.id.createDatabase)
                createDatabase.setOnClickListener {
                        //加入携程
                        // lifecycleScope
                        GlobalScope.launch(Dispatchers.Main) {
                                createDatabase()
                        }
                }

                val user1 = User("zhangsan", "hik12345")
                val user2 = User("lisi", "12345")
                //增加新用户
                add = findViewById(R.id.add)
                add.setOnClickListener {
                        lifecycleScope.launch(Dispatchers.Main) {
                                user1.id = userDao.insertUser(user1)
                                user2.id = userDao.insertUser(user2)
                                Log.d("2", "添加成功")
                        }
                }

                //根据用户名删除用户
                delete = findViewById(R.id.delete)
                delete.setOnClickListener {
                        lifecycleScope.launch {
                                //要删除用户，需要先从界面中获取到要注销的用户名和密码
                                zhuxiao()
                        }

                }
                //改用户名和密码
                update = findViewById(R.id.update)
                update.setOnClickListener {

                }
                //查询一个人
                queryOne = findViewById(R.id.selectOne)
                queryOne.setOnClickListener {

                }

                queryAll = findViewById(R.id.selectAll)
                queryAll.setOnClickListener {
                        lifecycleScope.launch(Dispatchers.Main) {
                                var users: ArrayList<User>
                                users = userDao.queryAllUser() as ArrayList<User>
                                for (user in users) {
                                        Log.d("3", user.toString())
                                }
                        }

                }
                //val userDao=AppDatabase.getDatabase(this).userDao()

                var loginbtn = findViewById<Button>(R.id.loginDatabase)
                loginbtn.setOnClickListener {
                        lifecycleScope.launch {
                                login()
                        }
                }

                var registerBtn = findViewById<Button>(R.id.btnRegisterDatabase)
                registerBtn.setOnClickListener {
                        lifecycleScope.launch {
                                register()
                        }
                }
        }

        //创建数据库
        suspend fun createDatabase() {
                appDatabase = AppDatabase.getDatabaseSingleton2(this)
                Log.d("1", "创建数据库成功")
                userDao = appDatabase.userDao()
        }

        suspend fun login() {
                var nameInput = findViewById<EditText>(R.id.textOfNameDatabase)
                var username = nameInput.text.toString()
                var passwordInout = findViewById<EditText>(R.id.textOfPassWordDatabase)
                var password = passwordInout.text.toString()

                var user = userDao.queryUserByName(username, password)
                if (user == null) {
                        Toast.makeText(this, "密码或用户名错误，登录失败", Toast.LENGTH_SHORT).show()
                } else {
                        Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show()
                }
        }

        suspend fun register() {
                var nameInput = findViewById<EditText>(R.id.textOfNameDatabase)
                var username = nameInput.text.toString()
                var passwordInout = findViewById<EditText>(R.id.textOfPassWordDatabase)
                var password = passwordInout.text.toString()

                if (userDao.queryUserByName(username) != null) {
                        Toast.makeText(this, "用户名已存在！", Toast.LENGTH_LONG).show()
                } else {
                        var user = User(username, password)
                        if (userDao.insertUser(user) > 0) {
                                Toast.makeText(this, "用户注册成功！", Toast.LENGTH_LONG).show()
                        } else {
                                Toast.makeText(this, "用户注册失败！", Toast.LENGTH_LONG).show()
                        }
                }
        }

        suspend fun zhuxiao() {
                //先查到
                var nameInput = findViewById<EditText>(R.id.textOfNameDatabase)
                var username = nameInput.text.toString()
                var passwordInout = findViewById<EditText>(R.id.textOfPassWordDatabase)
                var password = passwordInout.text.toString()
                var user = userDao.queryUserByName(username, password)
                if (user != null) {
                        if (userDao.deleteUser(user) > 0) {
                                Toast.makeText(this, "用户删除成功！", Toast.LENGTH_LONG).show()
                        }
                } else {
                        Toast.makeText(this, "此用户不存在！删除失败", Toast.LENGTH_LONG).show()
                }
        }


}