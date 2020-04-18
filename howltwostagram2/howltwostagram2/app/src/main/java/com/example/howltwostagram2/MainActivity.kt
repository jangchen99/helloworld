package com.example.howltwostagram2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.howltwostagram2.navigation.AlarmFragment
import com.example.howltwostagram2.navigation.DetailViewFragment
import com.example.howltwostagram2.navigation.GridFragment
import com.example.howltwostagram2.navigation.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_home ->{
                var detailViewFrament = DetailViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFrament).commit()
                return true
            }
            R.id.action_search ->{
                var gridFrament = GridFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,gridFrament).commit()
                return true
            }
            R.id.action_add_photo ->{

                 return true
            }
            R.id.action_favorite_alarm ->{
                var alarmFragment = AlarmFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,alarmFragment).commit()
                return true
            }
            R.id.action_account ->{
                var userFragment = UserFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,userFragment).commit()
                return true
            }

        }   //리스너를 연결할 꺼임

        return false // 어떠한 조건도 만족하지 않으면 false를 리턴
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)//activity는 this
    }

}