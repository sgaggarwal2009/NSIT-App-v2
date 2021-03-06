package nsit.app.com.nsitapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapters.MyFeedList;
import functions.Constant;
import functions.Utils;
import functions.Val;
import functions.dbAdapter;


/**
 * Created by Swati garg on 21-06-2015.
 */

public class FinalFeed extends Fragment implements Constant{
    Boolean Collegespace=false,Crosslinks=false,Junoon=false,Bullet=false,
            Rotaract=false,Quiz=false,Ieee=false,Csi=false,Ashwa=false,Deb=false,
            Enactus=false,Aagaz=false;
    boolean loadingMore=false;
    ProgressBar pb;
    SwipeRefreshLayout swipeLayout;
    String nextn;
    MyFeedList adapter;
    int first;
    String nextcollegespace,nextcrosslinks,nextjunoon,nextbullet,nextrotaract,nextquiz,nextieee,nextcsi,nextashwa,nextdeb, nextaagaz, nextecell, nextenactus;
    List<String> list = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<String> list5 = new ArrayList<String>();
    List<String> list6 = new ArrayList<String>();
    List<String> list7 = new ArrayList<String>();
    List<String> list8 = new ArrayList<String>();
    List<String> list9 = new ArrayList<String>();
    View footerView;
    SharedPreferences i;
    ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setHasOptionsMenu(true);
    }

    Activity activity;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedfinal, container, false);
        pb=(ProgressBar)rootView.findViewById(R.id.progressBar1);
        lv = (ListView) rootView.findViewById(R.id.list);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        footerView = ((LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);


        first=1;
        adapter = new MyFeedList(activity, list6, list, list2, list7, list1, list8,list9);

        if(activity!=null) {
            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            Boolean set = s.getBoolean(SOCIETY_SET, false);
            if (set == false) {
                Intent i = new Intent(getActivity(), ChooseFeedItems.class);
                startActivity(i);
            }
        }

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
            }
        });
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        load();
        return rootView;
    }



    private class DownloadWebPageTask2 extends AsyncTask<String, Void, String> {
        String id;

        public DownloadWebPageTask2(String id) {
            this.id = id;
        }

        @Override
        protected String doInBackground(String... urls) {


            String uri = "https://graph.facebook.com/" + id + "/posts?limit=10&fields=picture,shares,message,object_id," +
                    "link,comments.limit(0).summary(true),to,created_time,likes.limit(0).summary(true)&access_token=" + Val.common_access;

            java.net.URL url = null;
            String readStream = null;
            try {
                url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                readStream = Utils.readStream(con.getInputStream());
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return readStream;




        }

        @Override
        protected void onPostExecute(String result) {
            int j=0;
            JSONObject ob;
            JSONArray arr;
            try {
                ob = new JSONObject(result);
                arr = ob.getJSONArray("data");
                if(ob.has("paging")) {
                    ob = ob.getJSONObject("paging");

                    if (ob.has("next"))
                        nextn = ob.getString("next");
                    else
                        nextn = null;

                }
                else
                    nextn = null;

                switch (id) {
                    case Val.id_collegespace:nextcollegespace=nextn;
                        Collegespace = false;
                        break;
                    case Val.id_crosslinks:nextcrosslinks=nextn;
                        Crosslinks = false;
                        break;
                    case Val.id_bullet:nextbullet=nextn;
                        Bullet = false;
                        break;
                    case Val.id_junoon:nextjunoon=nextn;
                        Junoon = false;
                        break;
                    case Val.id_rotaract:nextrotaract=nextn;
                        Rotaract = false;
                        break;
                    case Val.id_csi:nextcsi=nextn;
                        Csi = false;
                        break;
                    case Val.id_ieee:nextieee=nextn;
                        Ieee = false;
                        break;
                    case Val.id_quiz:nextquiz=nextn;
                        Quiz = false;
                        break;
                    case Val.id_ashwa:nextashwa=nextn;
                        Ashwa = false;
                        break;
                    case Val.id_debsoc:nextdeb=nextn;
                        Deb = false;
                        break;
                    case Val.id_enactus:nextenactus=nextn;
                        Enactus = false;
                        break;
                    case Val.id_aagaz:nextaagaz=nextn;
                        Aagaz = false;
                        break;


                }

                dbAdapter db = new dbAdapter(getActivity());
                db.open();

                for(int i = 0; i < arr.length(); i++){

                    if(arr.getJSONObject(i).has("message"))
                        list.add(arr.getJSONObject(i).getString("message"));
                    else {
                        list.add(null);
                    }
                    if(!(arr.getJSONObject(i).has("object_id")))
                        list1.add(null);
                    else
                        list1.add(arr.getJSONObject(i).getString("object_id"));



                    if(arr.getJSONObject(i).has("picture")) {
                        list6.add(arr.getJSONObject(i).getString("picture"));
                    }
                    else
                        list6.add(null);
                    if(arr.getJSONObject(i).has("link")) {
                        list7.add(arr.getJSONObject(i).getString("link"));
                    }
                    else
                        list7.add(null);
                    if(arr.getJSONObject(i).has("likes")) {
                        String s = arr.getJSONObject(i).getString("likes");
                        JSONObject o = new JSONObject(s);
                        JSONArray a2 = o.getJSONArray("data");
                        String x = o.getString("summary");
                        JSONObject o2 = new JSONObject(x);

                        list2.add(o2.getString("total_count"));    //No of likes
                    }
                    else
                        list2.add("0");


                    if(arr.getJSONObject(i).has("created_time"))
                        list8.add(arr.getJSONObject(i).getString("created_time"));
                    else
                        list8.add(null);


                    if(arr.getJSONObject(i).has("to")){
                        JSONObject o = new JSONObject(arr.getJSONObject(i).getString("to"));
                        JSONArray a2 = o.getJSONArray("data");
                        String x = a2.getJSONObject(0).getString("name");
                        list9.add(x);
                    }else
                        list9.add(null);

                    db.insertRow(list.get(list.size()-1), list1.get(list1.size()-1), list2.get(list2.size()-1), list6.get(list6.size()-1),
                            list7.get(list7.size()-1), list8.get(list8.size()-1), list9.get(list9.size()-1), id);
                }

                db.close();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(first==1) {
                done();
            }

        }
    }

    private class DownloadWebPageTask3 extends AsyncTask<String, Void, String> {
        String id,token,next;

        public DownloadWebPageTask3(String id,String n) {
            this.id = id;
            next=n;
        }

        @Override
        protected String doInBackground(String... urls) {

            if(next!=null){



                String[] x = next.split("&__paging_token=");
                token=x[1];
                String uri = next;
                java.net.URL url = null;
                String readStream = null;
                try {
                    url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    readStream = Utils.readStream(con.getInputStream());
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                return readStream;



            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            int j=0;
            JSONObject ob;
            JSONArray arr;
            if(result!=null)
                try {
                    ob = new JSONObject(result);
                    arr = ob.getJSONArray("data");
                    if(ob.has("paging")) {
                        ob = ob.getJSONObject("paging");

                        if (ob.has("next"))
                            nextn = ob.getString("next");
                        else
                            nextn = null;

                    }
                    else
                        nextn = null;

                    dbAdapter db = new dbAdapter(getActivity());
                    db.open();

                    for(int i = 0; i < arr.length(); i++){

                        if(arr.getJSONObject(i).has("message")) {
                            list.add(arr.getJSONObject(i).getString("message"));
                        }
                        else {
                            continue;
                        }
                        if(!(arr.getJSONObject(i).has("object_id")))
                            list1.add(null);
                        else
                            list1.add(arr.getJSONObject(i).getString("object_id"));


                        if(arr.getJSONObject(i).has("to")){
                            JSONObject o = new JSONObject(arr.getJSONObject(i).getString("to"));
                            JSONArray a2 = o.getJSONArray("data");
                            String x = a2.getJSONObject(0).getString("name");
                            list9.add(x);
                        }else
                            list9.add(null);

                        if(arr.getJSONObject(i).has("picture")) {
                            list6.add(arr.getJSONObject(i).getString("picture"));
                        }
                        else
                            list6.add(null);
                        if(arr.getJSONObject(i).has("link")) {
                            list7.add(arr.getJSONObject(i).getString("link"));
                        }
                        else
                            list7.add(null);
                        if(arr.getJSONObject(i).has("likes")) {
                            String s = arr.getJSONObject(i).getString("likes");
                            JSONObject o = new JSONObject(s);
                            JSONArray a2 = o.getJSONArray("data");
                            String x = o.getString("summary");
                            JSONObject o2 = new JSONObject(x);

                            list2.add(o2.getString("total_count"));    //No of likes
                        }
                        else
                            list2.add("0");

                        if(arr.getJSONObject(i).has("created_time"))
                            list8.add(arr.getJSONObject(i).getString("created_time"));
                        else
                            list8.add(null);

                        db.insertRow(list.get(list.size()-1), list1.get(list1.size()-1), list2.get(list2.size()-1), list6.get(list6.size()-1),
                                list7.get(list7.size()-1), list8.get(list8.size()-1), list9.get(list9.size()-1), id);

                    }
                    db.close();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            switch (id) {
                case Val.id_collegespace: nextcollegespace=nextn;
                    break;
                case Val.id_crosslinks:nextcrosslinks=nextn;
                    Crosslinks = false;
                    break;
                case Val.id_bullet:nextbullet=nextn;
                    Bullet = false;
                    break;
                case Val.id_junoon:nextjunoon=nextn;
                    Junoon = false;
                    break;
                case Val.id_rotaract:nextrotaract=nextn;
                    Rotaract = false;
                    break;
                case Val.id_csi:nextcsi=nextn;
                    Csi = false;
                    break;
                case Val.id_ieee:nextieee=nextn;
                    Ieee = false;
                    break;
                case Val.id_quiz:nextquiz=nextn;
                    Quiz = false;
                    break;
                case Val.id_ashwa:nextashwa=nextn;
                    Ashwa = false;
                    break;
                case Val.id_debsoc:nextdeb=nextn;
                    Deb = false;
                    break;
                case Val.id_enactus:nextenactus=nextn;
                    Enactus = false;
                    break;
                case Val.id_aagaz:nextaagaz=nextn;
                    Aagaz = false;
                    break;
            }
            loadingMore=false;
            lv.removeFooterView(footerView);

        }
    }


    public void done()
    {
        if(!Csi && !Collegespace && !Crosslinks && !Bullet && !Junoon && !Ieee&& !Ashwa&& !Quiz&& !Deb &&!Rotaract && !Enactus && !Aagaz) {

            adapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
            lv.removeFooterView(footerView);

            if (activity != null)
                lv.setAdapter(adapter);
            first = 0;
        }
    }

    @Override
    public void onResume() {

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor e = s.edit();
        if(s.getBoolean("item_changed",false)) {
            list.clear();
            list1.clear();
            list2.clear();
            list6.clear();
            list7.clear();
            list8.clear();
            adapter.notifyDataSetChanged();
            e.putBoolean("item_changed", false);
            e.commit();
            load();
        }
        super.onResume();

    }

    public void load(){
        i = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Crosslinks = i.getBoolean(CROSSLINKS, false);
        Collegespace = i.getBoolean(COLLEGESPACE, false);
        Bullet = i.getBoolean(BULLET, false);
        Junoon = i.getBoolean(JUNOON, false);
        Rotaract = i.getBoolean(ROTARACT, false);
        Csi = i.getBoolean(CSI, false);
        Ieee = i.getBoolean(IEEE, false);
        Deb = i.getBoolean(DEB, false);
        Quiz = i.getBoolean(QUIZ, false);
        Ashwa = i.getBoolean(ASHWA, false);
        Enactus = i.getBoolean(ENACTUS, false);
        Aagaz = i.getBoolean(AAGAZ, false);


        dbAdapter db = new dbAdapter(getActivity());

        lv.addFooterView(footerView);

        if(!Csi && !Collegespace && !Crosslinks && !Bullet && !Junoon && !Ieee&& !Ashwa&& !Quiz&& !Deb &&!Rotaract && !Enactus && !Aagaz) {
            SnackbarManager.show(
                    Snackbar.with(activity.getApplicationContext())
                            .text("No item Selected")
                            .duration(Snackbar.SnackbarDuration.LENGTH_SHORT), activity);
        }else {

            if (Utils.isNetworkAvailable(activity)) {

                db.open();
                db.deleteAll();
                db.close();

                if (Crosslinks)
                    new DownloadWebPageTask2(Val.id_crosslinks).execute();
                if (Collegespace)
                    new DownloadWebPageTask2(Val.id_collegespace).execute();
                if (Bullet)
                    new DownloadWebPageTask2(Val.id_bullet).execute();
                if (Junoon)
                    new DownloadWebPageTask2(Val.id_junoon).execute();
                if (Rotaract)
                    new DownloadWebPageTask2(Val.id_rotaract).execute();
                if (Csi)
                    new DownloadWebPageTask2(Val.id_csi).execute();
                if (Ieee)
                    new DownloadWebPageTask2(Val.id_ieee).execute();
                if (Ashwa)
                    new DownloadWebPageTask2(Val.id_ashwa).execute();
                if (Quiz)
                    new DownloadWebPageTask2(Val.id_quiz).execute();
                if (Deb)
                    new DownloadWebPageTask2(Val.id_debsoc).execute();
                if(Enactus)
                    new DownloadWebPageTask2(Val.id_enactus).execute();
                if(Aagaz)
                    new DownloadWebPageTask2(Val.id_aagaz).execute();

            } else {
                SnackbarManager.show(
                        Snackbar.with(activity.getApplicationContext())
                                .text("No Internet Connection")
                                .duration(Snackbar.SnackbarDuration.LENGTH_SHORT), activity);
                db.open();
                Cursor c = db.getAllRows();
                if (c.moveToFirst()) {
                    do {
                        list.add(c.getString(1));
                        Log.v("DATABASE LOGS", c.getString(1)+" ");
                        list1.add(c.getString(2));
                        list2.add(c.getString(3));
                        list6.add(c.getString(4));
                        list7.add(c.getString(5));
                        list8.add(c.getString(6));
                        list9.add(c.getString(7));
                    } while (c.moveToNext());
                }
                db.close();
                pb.setVisibility(View.GONE);
            }
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;

                dbAdapter db = new dbAdapter(getActivity());

                if ((lastInScreen == totalItemCount) && !(loadingMore) && first!=1) {
                    loadingMore=true;
                    lv.addFooterView(footerView);
                    if(Utils.isNetworkAvailable(activity)){


                        Crosslinks = i.getBoolean(CROSSLINKS, false);
                        Collegespace = i.getBoolean(COLLEGESPACE, false);
                        Bullet = i.getBoolean(BULLET, false);
                        Junoon = i.getBoolean(JUNOON, false);
                        Rotaract = i.getBoolean(ROTARACT, false);
                        Csi = i.getBoolean(CSI, false);
                        Ieee = i.getBoolean(IEEE, false);
                        Deb = i.getBoolean(DEB, false);
                        Quiz = i.getBoolean(QUIZ, false);
                        Ashwa = i.getBoolean(ASHWA, false);
                        Enactus = i.getBoolean(ENACTUS, false);
                        Aagaz = i.getBoolean(AAGAZ, false);
                        if (Crosslinks)
                            new DownloadWebPageTask3(Val.id_crosslinks,nextcrosslinks).execute();
                        if (Collegespace)
                            new DownloadWebPageTask3(Val.id_collegespace,nextcollegespace).execute();
                        if (Bullet)
                            new DownloadWebPageTask3(Val.id_bullet,nextbullet).execute();
                        if (Junoon)
                            new DownloadWebPageTask3(Val.id_junoon,nextjunoon).execute();
                        if (Rotaract)
                            new DownloadWebPageTask3(Val.id_rotaract,nextrotaract).execute();
                        if (Csi)
                            new DownloadWebPageTask3(Val.id_csi,nextcsi).execute();
                        if (Ieee)
                            new DownloadWebPageTask3(Val.id_ieee,nextieee).execute();
                        if (Ashwa)
                            new DownloadWebPageTask3(Val.id_ashwa,nextashwa).execute();
                        if (Quiz)
                            new DownloadWebPageTask3(Val.id_quiz,nextquiz).execute();
                        if (Deb)
                            new DownloadWebPageTask3(Val.id_debsoc,nextdeb).execute();
                        if(Enactus)
                            new DownloadWebPageTask3(Val.id_enactus,nextenactus).execute();
                        if(Aagaz)
                            new DownloadWebPageTask3(Val.id_aagaz,nextaagaz).execute();

                    }
                    else {
                        SnackbarManager.show(
                                Snackbar.with(activity.getApplicationContext())
                                        .text("No Internet Connection")
                                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT), activity);

                        db.open();
                        Cursor c = db.getAllRows();
                        if(c.moveToFirst()) {
                            do {
                                list.add(c.getString(1));
                                Log.v("DATABASE LOGS", c.getString(1));
                                list1.add(c.getString(2));
                                list2.add(c.getString(3));
                                list6.add(c.getString(4));
                                list7.add(c.getString(5));
                                list8.add(c.getString(6));
                                list9.add(c.getString(7));
                            } while(c.moveToNext());
                        }
                        db.close();
                        pb.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();

                    }

                }

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_feed, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.items){
            Intent i = new Intent(getActivity(),ChooseFeedItems.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }



}