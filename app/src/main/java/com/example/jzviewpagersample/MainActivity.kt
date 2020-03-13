package com.example.jzviewpagersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.example.jzviewpager.JzViewPager
import com.example.jzviewpager.callback
import com.example.jzviewpagersample.frag.f1
import com.example.jzviewpagersample.frag.f2
import com.example.jzviewpagersample.frag.f3

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a=JzViewPager(arrayListOf(f1(),
            f2(),f3())).setAnimable(true).setCallBack(object :callback{
            override fun onPageSelected(int: Int) {
                Log.e("onPageSelected","${int}")
            }

            override fun onPageScrollStateChanged(int: Int) {
                Log.e("onPageScrollStateChange","${int}")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.e("onPageScrolled","position${position}/positionOffset${positionOffset}/positionOffsetPixels${positionOffsetPixels}")
            }
        }).setTimer(2000).setSmooth(true)
        supportFragmentManager.beginTransaction().replace(R.id.frage,a).commit()
    }
}
