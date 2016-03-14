package com.nnero.nnero.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nnero.nnero.R;
import com.nnero.nnero.base.NBaseAdapter;
import com.nnero.nnero.bean.Repo;
import com.nnero.nnero.callback.CallBack;
import com.nnero.nnero.manager.business.RepoManager;
import com.nnero.nnero.views.state.ContentLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements ContentLayout.OnNoNetworkRefreshListener {

    private ListView mListView;
    private ContentLayout mContentLayout;
    private View contentView;

    private RepoAdapter mRepoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData();
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

        RepoManager.requestRepos(callBack);
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
