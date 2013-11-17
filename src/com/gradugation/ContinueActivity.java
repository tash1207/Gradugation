package com.gradugation;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContinueActivity extends Activity {

	private List<SavedGame> mySavedGames = new ArrayList<SavedGame>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_continue);

		populateSavedGameList();
		populateListView();
		registerClickCallback();
	}

	/*
	 * replace savedGame1 string with string variable whose value is set after
	 * saving game, get characterID in place of R.drawable.ic_launcher, get time
	 * at moment of save
	 */
	private void populateSavedGameList() {
		mySavedGames.add(new SavedGame("saved game1", R.drawable.ic_launcher,
				"Last saved mm/dd 00:00"));
		mySavedGames.add(new SavedGame("saved game2", R.drawable.ic_launcher,
				"Last saved mm/dd 00:00"));
		mySavedGames.add(new SavedGame("saved game3", R.drawable.ic_launcher,
				"Last saved mm/dd 00:00"));
	}

	private void populateListView() {
		ArrayAdapter<SavedGame> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.savedGamesListView);
		list.setAdapter(adapter);
	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.savedGamesListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				String message = "Unable to retrieve saved game. No saved games to retrieve at this time. ";
				Toast.makeText(ContinueActivity.this, message,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private class MyListAdapter extends ArrayAdapter<SavedGame> {
		public MyListAdapter() {
			super(ContinueActivity.this, R.layout.item_view, mySavedGames);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view,
						parent, false);
			}

			SavedGame currentSavedGame = mySavedGames.get(position);

			// Fill the view
			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.item_icon);
			imageView.setImageResource(currentSavedGame.getIconID());

			TextView makeText = (TextView) itemView
					.findViewById(R.id.item_txtSavedGame);
			makeText.setText(currentSavedGame.getGameNumber());

			TextView saveText = (TextView) itemView
					.findViewById(R.id.item_txtSaveData);
			saveText.setText(currentSavedGame.getTimeDate());

			return itemView;
		}
	}
}
