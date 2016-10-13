package test.wjk.com.mylooklook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import test.wjk.com.mylooklook.Activity.AboutActivity;
import test.wjk.com.mylooklook.fragment.ZhihuFragment;
import test.wjk.com.mylooklook.util.SharePreferenceUtil;

public class MainActivity extends AppCompatActivity {

    @InjectView((R.id.fragment_container))
    FrameLayout fragmentContainer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.nav_view)
    NavigationView navView;
    @InjectView(R.id.drawer)
    DrawerLayout drawer;

    private SimpleArrayMap<Integer, String> mTitleArryMap = new SimpleArrayMap<>();
    private int nevigationId;
    private MenuItem currentMenuItem;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_open:
                        drawer.openDrawer(GravityCompat.END);
                        break;
                    case R.id.menu_about:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });


        mTitleArryMap.put(R.id.zhihuitem, getResources().getString(R.string.zhihu));
        mTitleArryMap.put(R.id.topnewsitem, getResources().getString(R.string.topnews));
        mTitleArryMap.put(R.id.meiziitem, getResources().getString(R.string.meizi));

//        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (currentMenuItem != item && currentMenuItem != null) {
                    currentMenuItem.setChecked(false);
                    int id = item.getItemId();
                    SharePreferenceUtil.putNevigationItem(MainActivity.this, id);
                    currentMenuItem = item;
                    currentMenuItem.setChecked(true);
                    switchFragment(getFragmentById(currentMenuItem.getItemId()), mTitleArryMap.get(currentMenuItem.getItemId()));
                }
                drawer.closeDrawer(GravityCompat.END, true);
                return true;
            }
        });


        if (savedInstanceState == null) {
            nevigationId = SharePreferenceUtil.getNevigationItem(this);
            if (nevigationId != -1) {
                currentMenuItem = navView.getMenu().findItem(nevigationId);
            }
            if (currentMenuItem == null) {
                currentMenuItem = navView.getMenu().findItem(R.id.zhihuitem);
            }
            if (currentMenuItem != null) {
                currentMenuItem.setChecked(true);
                Fragment fragment = getFragmentById(currentMenuItem.getItemId());
                String title = mTitleArryMap.get((Integer) currentMenuItem.getItemId());
                if (fragment != null) {
                    switchFragment(fragment, title);
                }
            }
        } else {
            if (currentMenuItem != null) {
                Fragment fragment = getFragmentById(currentMenuItem.getItemId());
                String title = mTitleArryMap.get((Integer) currentMenuItem.getItemId());
                if (fragment != null) {
                    switchFragment(fragment, title);
                }
            } else {
                switchFragment(new ZhihuFragment(), " ");
                currentMenuItem = navView.getMenu().findItem(R.id.zhihuitem);

            }
        }
    }

    private void switchFragment(Fragment fragment, String title) {
        if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
        currentFragment = fragment;
    }

    private Fragment getFragmentById(int id) {
        Fragment fragment = null;
        switch (id) {
//            case R.id.zhihuitem:
//                fragment = new ZhihuFragment();
//                break;
//            case R.id.topnewsitem:
//                fragment=new TopNewsFragment();
//                break;
//            case R.id.meiziitem:
//                fragment=new MeiziFragment();
//                break;

        }
        return fragment;
    }

    /**
     * 给toolbar添加布局
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
