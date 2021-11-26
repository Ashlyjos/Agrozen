package mca.project.agrozen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class TipsTricks extends AppCompatActivity {


    String api_key = "AIzaSyBNWkd-lzhKi8EvXvu1xxlbYrEOzt6EVQY";
    RecyclerView recyclerView;
    ArrayList<DataSetList> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_tricks);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<DataSetList>();


        DataSetList dataSetList = new DataSetList("https://www.youtube.com/watch?v=FyOzeO6fQwI");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/watch?v=hiSRDBY_0i0");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/watch?v=YDcakKDJWv0");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/watch?v=DoVGbPa0jHw");
        arrayList.add(dataSetList);

        YoutubeAdapter youtubeAdapter = new YoutubeAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(youtubeAdapter);



        //AIzaSyBNWkd-lzhKi8EvXvu1xxlbYrEOzt6EVQY


    }
}