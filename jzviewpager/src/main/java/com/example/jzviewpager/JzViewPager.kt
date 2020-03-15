package com.example.jzviewpager


import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_jz_view_pager.view.*
import java.util.*
import kotlin.concurrent.schedule

/**
 * A simple [Fragment] subclass.
 */
interface callback{
    fun onPageSelected(int: Int)
    fun onPageScrollStateChanged(int: Int)
    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
}
class JzViewPager(var  fragments:ArrayList<Fragment>) : Fragment() {
    var timer=Timer()
    var handler=Handler()
    var scrollState=0
lateinit var rootview:View
    lateinit var callback: callback
    lateinit var adapter:MyPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_jz_view_pager, container, false)

        adapter=MyPagerAdapter(activity!!.supportFragmentManager)
        rootview.pager.adapter=adapter

            rootview.pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                    if(::callback.isInitialized){  callback.onPageScrollStateChanged(state)}
                    scrollState=state
                }
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if(::callback.isInitialized){   callback.onPageScrolled(position,positionOffset,positionOffsetPixels)}
                }
                override fun onPageSelected(position: Int) {
                    if(::callback.isInitialized){   callback.onPageSelected(position)}
                }
            })

        return rootview
    }

    public inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(i: Int): Fragment {
            return fragments[i]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
    var index=0
    var time=1000L
    var scollAble=false
    var smooth=true

    fun setCallBack(a:callback):JzViewPager{
        callback=a
        return this
    }
fun setTimer(a:Long):JzViewPager{
    time=a
    return this
}
    fun scrollToPosition(a:Int){
        rootview.pager.setCurrentItem(a)
    }

    fun setSmooth(a:Boolean):JzViewPager{
        smooth=a
        return this
    }

    fun setAnimable(a:Boolean):JzViewPager{
        scollAble=a
        return this
    }

    override fun onResume() {
        super.onResume()

        if(!scollAble){return}
        timer=Timer()
        timer.schedule(0,time){
            handler.post {
//                Log.e("focus",""+rootview.pager.action)
                if(::rootview.isInitialized&&scrollState==0){
                    index+=1
                    if(fragments.size<=index){index=0}
                    rootview.pager.setCurrentItem(index,smooth)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
fun isInitialized():Boolean{
    return ::rootview.isInitialized
}


}
