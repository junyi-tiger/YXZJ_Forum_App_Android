package com.example.easylearnhome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnhome.activities.MyPostsActivity;
import com.example.easylearnhome.activities.NewPostActivity;
import com.example.easylearnhome.activities.StartActivity;
import com.example.easylearnhome.activities.UserInfoActivity;
import com.example.easylearnhome.fragments.CollectionFragment;
import com.example.easylearnhome.fragments.PostFragment;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.UheadUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        CollectionFragment.OnFragmentInteractionListener,
        PostFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * 右下角浮动的小图标
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
            startActivity(intent);
        });

        /**
         * 拖拽Activity的拖出菜单布局
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout nav_header_container = (LinearLayout)navigationView.getHeaderView(0);
        user_avater = nav_header_container.findViewById(R.id.user_avater);
        user_name = nav_header_container.findViewById(R.id.user_name);
        user_email = nav_header_container.findViewById(R.id.user_email);
        //设置头像
        String uhead = DataBaseUtil.getUhead(new DatabaseHelper(getApplicationContext()).getReadableDatabase());
        if (uhead!=null&&uhead.compareTo("")!=0)
            user_avater.setBackground(getResources().getDrawable(UheadUtils.getUhead_id(uhead)));
        //设置用户名
        user_name.setText(DataBaseUtil.getUname(new DatabaseHelper(getApplicationContext()).getReadableDatabase()));
        //设置邮箱
        user_email.setText(DataBaseUtil.getUemail(new DatabaseHelper(getApplicationContext()).getReadableDatabase()));

        initViews();//初始化控件
        initEvents();//初始化Tab的点击事件
        initDatas();//初始化数据

    }

    //声明ViewPager
    private ViewPager viewPager;
    //适配器
    private FragmentPagerAdapter fragmentPagerAdapter;
    //装载Fragment的集合
    private List<Fragment> fragments;
    //两个Tab对应的布局
    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout header_container;
    private NavigationView navigationView;

    private ImageView user_avater;//用户头像
    private TextView user_name;//用户名
    private TextView user_email;//邮箱


    private void initViews(){
        viewPager = findViewById(R.id.viewpager);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        header_container = (LinearLayout) navigationView.getHeaderView(0);
    }

    private void initEvents(){
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        header_container.setOnClickListener(this);
    }

    private void initDatas(){
        //添加到fragments集合中
        fragments = new ArrayList<>();
        fragments.add(new PostFragment());
        fragments.add(new CollectionFragment());

        //初始化适配器
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);//设置viewpager的适配器
        //设置ViewPager的切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            //页面滚动事件
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tab1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tab2.setBackgroundColor(getResources().getColor(R.color.colorBackGround));
                        tab1.getChildAt(0).setBackgroundResource(R.drawable.ic_home_selected);
                        tab2.getChildAt(0).setBackgroundResource(R.drawable.ic_heart_unselected);
                        break;
                    case 1:
                        tab1.setBackgroundColor(getResources().getColor(R.color.colorBackGround));
                        tab2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tab1.getChildAt(0).setBackgroundResource(R.drawable.ic_home_unselected);
                        tab2.getChildAt(0).setBackgroundResource(R.drawable.ic_heart_selected);
                        break;
                        default:
                            break;
                }
            }
            //页面滚动状态改变事件
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    /**
     * 设置按下返回按钮时的动作
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    /**
     * 创建选项菜单时调用
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 当点击某一菜单项时的操作
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //删除数据
            DataBaseUtil.clear(new DatabaseHelper(getApplicationContext()).getWritableDatabase());
            //开启登录活动
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 拖出菜单导航栏的监听器
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_collection) {
//            Toast.makeText(getApplicationContext(),"查看我的收藏",Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_my_post) {
//            Toast.makeText(getApplicationContext(),"查看我的帖子",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MyPostsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(),"分享此APP",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 点击事件处理
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.nav_header_container:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
//                Toast.makeText(getApplicationContext(),"查看个人信息",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("uid",DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase()));
                startActivity(intent);
                break;
        }
    }

    /**
     * Fragment与Activity通信
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this,"onFragmentInteraction() executed!", Toast.LENGTH_LONG).show();
    }

}