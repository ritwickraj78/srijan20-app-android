package in.srijanju.androidapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import in.srijanju.androidapp.R;

public class About extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
	private static final String srijanAbt = "SRIJAN has always dedicated itself to the idea of promoting, showcasing, and encouraging concepts and research beyond the known periphery. SRIJAN is not just a fest for us. It is a Religion.";
	private static final String API_KEY = "AIzaSyBYBVz-xSdiiIE2bueRgccFKcQ7odLosVg";
	private static final String VIDEO_ID= "7oAc0d_W8-k";
	String[] values = new String[] { "Rishav Kumar$IT 3rd Year","Gaurav Damani$IT 3rd Year"};
	YouTubePlayerView playerView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		playerView= (YouTubePlayerView)findViewById(R.id.srijanVid);
		playerView.initialize(API_KEY,this);
		TextView srijanText = (TextView)findViewById(R.id.srijanText);
		srijanText.setText(srijanAbt);
		ListView creatorListView = (ListView)findViewById(R.id.creatorsList);
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final  MyArrayAdapter adapter = new MyArrayAdapter(this,list);
		creatorListView.setAdapter(adapter);
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
		youTubePlayer.loadVideo(VIDEO_ID);
	}

	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

	}
	public class MyArrayAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final ArrayList<String> values;

		public MyArrayAdapter(Context context, ArrayList<String> values) {
			super(context, -1, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.creator_item, parent, false);
			TextView nametextView = (TextView) rowView.findViewById(R.id.name);
			TextView depttextView = (TextView) rowView.findViewById(R.id.dept);
			String name= values.get(position).substring(0, values.get(position).indexOf('$'));
			String dept= values.get(position).substring(values.get(position).indexOf('$')+1);
			nametextView.setText(name);
			depttextView.setText(dept);
			return rowView;
		}
	}

}
