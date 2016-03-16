package com.nnero.nnero.buisness;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.nnero.nnero.R;
import com.nnero.nnero.base.BaseFragmentActivity;
import com.nnero.nnero.base.NBaseAdapter;
import com.nnero.nnero.bean.Repo;
import com.nnero.nnero.callback.CallBack;
import com.nnero.nnero.buisness.main.RepoManager;
import com.nnero.nnero.util.NLog;
import com.nnero.nnero.views.state.ContentLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseFragmentActivity implements ContentLayout.OnNoNetworkRefreshListener {

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private ContentLayout mContentLayout;
    private View contentView;

    private RepoAdapter mRepoAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        initToolbar();
        initLeftMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mContentLayout = (ContentLayout) findViewById(R.id.content_layout);
        mListView = (ListView) findViewById(R.id.list);
        contentView = findViewById(R.id.content_view);
        mContentLayout.setContentView(contentView);
        //bussiness
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("NNERO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initLeftMenu(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void initListView(List<Repo> repos){
        if(mRepoAdapter == null){
            mRepoAdapter = new RepoAdapter(repos,MainActivity.this);
            mListView.setAdapter(mRepoAdapter);
        } else {
            mRepoAdapter.setData(repos);
            mRepoAdapter.notifyDataSetChanged();
        }
    }

    private CallBack<List<Repo>> callBack;

    public void getData(){
        callBack = new CallBack<List<Repo>>() {

            @Override
            public void preDoInBackground() {
                MainActivity.this.mContentLayout.setLayoutLevel(ContentLayout.LAYOUT_LOADING);
            }

            @Override
            public void process(List<Repo> repos) {
                if(repos != null && repos.size() > 0){
                    MainActivity.this.mContentLayout.setLayoutLevel(ContentLayout.LAYOUT_CONTENT);
                    initListView(repos);
                } else {
                    MainActivity.this.mContentLayout.setLayoutLevel(ContentLayout.LAYOUT_NO_DATA);
                }
            }
        };

//        RepoManager.requestRepos(callBack);
        MainActivity.this.mContentLayout.setLayoutLevel(ContentLayout.LAYOUT_LOADING);
        RepoManager.requestRepos();//测试eventBus
    }

    @Subscribe
    public void proc(List<Repo> repos){
        NLog.dList("proc",repos);
    }

    @Subscribe
    public void processData(List<Repo> repos){
        if(repos != null && repos.size() > 0){
            MainActivity.this.mContentLayout.setLayoutLevel(ContentLayout.LAYOUT_CONTENT);
            initListView(repos);
        } else {
            MainActivity.this.mContentLayout.setLayoutLevel(ContentLayout.LAYOUT_NO_DATA);
        }
    }

    static class RepoAdapter extends NBaseAdapter<Repo,RepoAdapter.ViewHolder> {

        static class ViewHolder extends NBaseAdapter.ViewHolder{
            @InjectView(R.id.repo_id) TextView mIdView;
            @InjectView(R.id.repo_name) TextView mNameView;
            @InjectView(R.id.repo_full_name) TextView mFullNameView;
            @InjectView(R.id.repo_is_private) TextView mPrivateView;

            public ViewHolder(View v){
                super(v);
                ButterKnife.inject(this,v);
            }
        }

        public RepoAdapter(List<Repo> repos,Activity activity){
            super(repos,activity);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_repo_list;
        }

        @Override
        public ViewHolder getViewHolder(View v) {
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,int pos) {
            viewHolder.mIdView.setText(list.get(pos).getId()+"");
            viewHolder.mNameView.setText(list.get(pos).getName());
            viewHolder.mFullNameView.setText(list.get(pos).getFull_name());
            viewHolder.mPrivateView.setText(list.get(pos).isPrivate()?"TRUE":"FALSE");
        }
    }
}
