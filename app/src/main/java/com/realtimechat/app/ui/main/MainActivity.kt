package com.realtimechat.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.database.*
import com.realtimechat.app.R
import com.realtimechat.app.data.prefs.AppPreferences
import com.realtimechat.app.ui.base.BaseActivity
import com.realtimechat.app.ui.users.login.LoginActivity
import com.realtimechat.app.utils.ImageUtils
import kotlinx.android.synthetic.main.main_activity.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity(), MainView {
    private var mPresenter : MainPresenter<MainView> = MainPresenter()

    var room_name = "ROOM1"
    var root: DatabaseReference = FirebaseDatabase.getInstance().getReference().getRoot()

    lateinit var root2: DatabaseReference

    lateinit var arrayAdapter : ArrayAdapter<String>
    var list_of_rooms = arrayListOf<String>()

    lateinit var temp_key: String
    lateinit var chat_msg: String
    lateinit var user_name: String
    lateinit var chat_user_name: String
    lateinit var chat_time: String

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

        lateinit var instance: MainActivity
        fun getInstace(): MainActivity {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mPresenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        setSupportActionBar(toolbar)

        instance = this

        initChatRoom()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initChatRoom(){
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list_of_rooms)
        //listView.setAdapter(arrayAdapter)

        val map: MutableMap<String, Any> = HashMap()
        map[room_name] = ""
        root.updateChildren(map)

        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val set: MutableSet<String> = HashSet()
                val i: Iterator<*> = dataSnapshot.children.iterator()
                while (i.hasNext()) {
                    set.add((i.next() as DataSnapshot).key)
                }
                list_of_rooms.clear()
                list_of_rooms.addAll(set)
                arrayAdapter.notifyDataSetChanged()

                initStartChat()

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun initStartChat(){
        var foto = AppPreferences.getAccount1().avatar
        if(AppPreferences.loginAt() == 1) {
            user_name = AppPreferences.getAccount1().name
        }else if(AppPreferences.loginAt() == 2){
            user_name = AppPreferences.getAccount2().name
            foto = AppPreferences.getAccount2().avatar
        }else{
            return
        }

        tv_user.text = user_name
        Glide.with(this)
            .load(foto).transition(DrawableTransitionOptions.withCrossFade())
            .apply(ImageUtils.RequestOption())
            .into(img_profile)

        Log.i("Login at", user_name)

        root2 = FirebaseDatabase.getInstance().getReference().child(room_name)

        btn_send.setOnClickListener(View.OnClickListener {

            val map: Map<String?, Any> = HashMap()
            temp_key = root2.push().key
            root2.updateChildren(map)

            Log.i("temp key", temp_key)

            val message_root = root2.child(temp_key)
            val map2: MutableMap<String?, Any> = HashMap()
            map2["name"] = user_name
            map2["msg"] = ed_message.getText().toString()
            map2["time"] = getTimeNow()

            message_root.updateChildren(map2)

            ed_message.setText("")
        })

        root2.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded( dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversatin(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversatin(dataSnapshot)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?){}

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun append_chat_conversatin(dataSnapshot: DataSnapshot) {
        val i: Iterator<*> = dataSnapshot.children.iterator()
        while (i.hasNext()) {
            chat_msg = (i.next() as DataSnapshot).value as String
            chat_user_name = (i.next() as DataSnapshot).value as String
            chat_time = (i.next() as DataSnapshot).value as String

            tv_message.append("")
            tv_message.append(chat_user_name +"    "+ chat_time + " \n " + chat_msg + "\n\n")
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_logout -> {
                mPresenter.doLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun logoutSuccess() {
        val intent = LoginActivity.getStartIntent(this)
        startActivity(intent)
        finish()
    }

    fun getTimeNow(): String{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateandTime: String = sdf.format(Date())
        return currentDateandTime
    }
}
