package com.gradugation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class FindTheMacMinigame extends Activity {
	private static int ROW_COUNT = -1;
	private static int COL_COUNT = -1;
	private static int MAX_TRIES = 3;
	private static int CREDITS_EARNED = 3;
	private static Object lock = new Object();
	private Context context;
	private Drawable backImage;
	private int[][] cards;
	private List<Drawable> images;
	private Card firstCard, secondCard;
	private ButtonListener buttonListener;

	private TableLayout mainTable;
	private UpdateCardsHandler handler;
	private AlertDialog.Builder alertDialogBuilder;
	private AlertDialog alertDialog;

	static int tries;
	boolean win;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tries = 0;
		handler = new UpdateCardsHandler();
		loadImages();
		setContentView(R.layout.activity_find_the_mac);

		backImage = getResources().getDrawable(R.drawable.ic_launcher);

		buttonListener = new ButtonListener();

		mainTable = (TableLayout) findViewById(R.id.TableLayout);

		context = mainTable.getContext();
		//spinner is a pull-down menu to choose game difficulty, set it invisible until alertDialog is clicked
		final Spinner s = (Spinner) findViewById(R.id.Spinner);
		s.setVisibility(View.GONE);
		alertDialogBuilder = new AlertDialog.Builder(FindTheMacMinigame.this);

				//set message
				alertDialogBuilder
						.setMessage("Click on the pull-down menu above to choose the desired difficulty level. Look for the pair of Macs among the PCs. You will have less than a second to spot them. Click on the matching pair of Macs to win. You have three tries. Press Continue to play.");
				alertDialogBuilder.setCancelable(false);

				// create continue button
				alertDialogBuilder.setNeutralButton("Continue",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// dialog box
								dialog.cancel();
								dialog.dismiss();
								s.setVisibility(View.VISIBLE);
							}
						});

				// create alert dialog
				alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
		
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(android.widget.AdapterView<?> arg0,
					View arg1, int pos, long arg3) {

				View v = findViewById(R.id.background);
				
				((Spinner) findViewById(R.id.Spinner)).setSelection(0);

				int column, row;
				
				//level of difficulty - 4x4, 4x6, 6x6 number of cards
				switch (pos) {
				case 1: //easy
					COL_COUNT= 4;
					ROW_COUNT = 4;
					break;
				case 2: //medium
					COL_COUNT = 4;
					ROW_COUNT = 6;
					break;
				case 3: //hard
					COL_COUNT = 6;
					ROW_COUNT = 6;
					break;
				default:
					return;
				}
				v.setVisibility(View.GONE);
				newGame();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}// end of onCreate()

	private void newGame() {
		cards = new int[COL_COUNT][ROW_COUNT];

		TableRow tr = ((TableRow) findViewById(R.id.TableRow));
		tr.removeAllViews();

		mainTable = new TableLayout(context);
		tr.addView(mainTable);

		loadCards();
		// reveal cards
		for (int y = 0; y < ROW_COUNT; y++) {
			mainTable.addView(createRow(y));
		}

		// delay before turning the cards over
		final Handler delayHandler = new Handler();
		delayHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Flip cards back after .7s = 700ms
				for (int y = 0; y < ROW_COUNT; y++) {
					View view = mainTable.getChildAt(y);
					TableRow temp = (TableRow) view;
					for (int x = 0; x < COL_COUNT; x++) {
						View v = temp.getChildAt(x);
						Button button = (Button) v;
						button.setBackgroundDrawable(backImage);
					}
				}
			}
		}, 500);
		firstCard = null;
		//TextView to display number of tries
		((TextView) findViewById(R.id.tries)).setText("Tries: " + tries);
	}

	private void loadImages() {
		images = new ArrayList<Drawable>();

		images.add(getResources().getDrawable(R.drawable.mac2));

		for (int i = 0; i < 21; i++) {
			images.add(getResources().getDrawable(R.drawable.pc2));
		}
	}

	private void loadCards() {
		try {
			int size = ROW_COUNT * COL_COUNT;

			Log.i("loadCards()", "size=" + size);

			ArrayList<Integer> list = new ArrayList<Integer>();
			// list = {0,1,2,...,size-1}
			for (int i = 0; i < size; i++) {
				list.add(i);
			}

			Random r = new Random();

			for (int i = size - 1; i >= 0; i--) {
				int t = 0;

				// generate random number [0,i), i decreasing with each
				// iteration
				if (i > 0)
					t = r.nextInt(i);

				// remove and return the element at index t of list
				t = list.remove(t);
				cards[i % COL_COUNT][i / COL_COUNT] = t % (size / 2);

				Log.i("loadCards()", "card[" + (i % COL_COUNT) + "]["
						+ (i / COL_COUNT) + "]="
						+ cards[i % COL_COUNT][i / COL_COUNT]);
			}
		} catch (Exception e) {
			Log.e("loadCards()", e + "");
		}
	}

	private TableRow createRow(int y) {
		TableRow row = new TableRow(context);
		row.setHorizontalGravity(Gravity.CENTER);

		for (int x = 0; x < COL_COUNT; x++) {
			row.addView(createImageButton(x, y));
		}
		return row;
	}
	//buttons are created with the image face up
	private View createImageButton(int x, int y) {
		Button button = new Button(context);
		button.setBackgroundDrawable(images.get(cards[x][y]));
		button.setId(100 * x + y);
		button.setOnClickListener(buttonListener);
		return button;
	}

	class ButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			synchronized (lock) {
				if (firstCard != null && secondCard != null) {
					return;
				}
				int id = v.getId();
				int x = id / 100;
				int y = id % 100;
				turnCard((Button) v, x, y);
			}
		}

		// turn card reveals the image
		private void turnCard(Button button, int x, int y) {
			button.setBackgroundDrawable(images.get(cards[x][y]));

			if (firstCard == null) {
				firstCard = new Card(button, x, y);
			} else {

				if (firstCard.x == x && firstCard.y == y) {
					return; // player pressed the same card
				}

				secondCard = new Card(button, x, y);
				tries++;
				((TextView) findViewById(R.id.tries))
						.setText("Tries: " + tries);

				TimerTask tt = new TimerTask() {

					@Override
					public void run() {
						try {
							synchronized (lock) {
								handler.sendEmptyMessage(0);
							}
						} catch (Exception e) {
							Log.e("E1", e.getMessage());
						}
					}
				};

				Timer t = new Timer(false);
				t.schedule(tt, 1300);
			}
		}
	}

	class UpdateCardsHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			synchronized (lock) {
				checkCards();
			}
		}

		public void checkCards() {
			//win if both selected cards are the Macs
			if (cards[secondCard.x][secondCard.y] == 0
					&& cards[firstCard.x][firstCard.y] == 0) {
				firstCard.button.setVisibility(View.INVISIBLE);
				secondCard.button.setVisibility(View.INVISIBLE);
				win();
			} else { //3 total tries to match the two Macs before losing, start a new game after each try
				secondCard.button.setBackgroundDrawable(backImage);
				firstCard.button.setBackgroundDrawable(backImage);
				if(tries >= MAX_TRIES) 
					lose();
				else
					newGame();
			}
			firstCard = null;
			secondCard = null;
		}
	}
	
	public void win() {
		//update the player's credits
		Intent output = new Intent();
		output.putExtra(Event.FIND_THE_MAC_REQUEST_CODE+"", CREDITS_EARNED);
		setResult(RESULT_OK, output);
		alertDialogBuilder = new AlertDialog.Builder(this);

		// set title and message
		alertDialogBuilder.setTitle("Congradugation! You've found the Macs and earned 3 credits!");
		alertDialogBuilder.setMessage("Press Continue.");
		alertDialogBuilder.setCancelable(false);

		// create continue button
		alertDialogBuilder.setNeutralButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						FindTheMacMinigame.this.finish();
					}
				});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	public void lose() {
		Intent output = new Intent();
		output.putExtra(Event.FIND_THE_MAC_REQUEST_CODE+"", 0);
		setResult(RESULT_OK, output);
		alertDialogBuilder = new AlertDialog.Builder(this);

		// set title and message
		alertDialogBuilder.setTitle("Sorry, you lose.");
		alertDialogBuilder.setMessage("Press Continue.");
		alertDialogBuilder.setCancelable(false);

		// create continue button
		alertDialogBuilder.setNeutralButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						FindTheMacMinigame.this.finish();
					}
				});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
}