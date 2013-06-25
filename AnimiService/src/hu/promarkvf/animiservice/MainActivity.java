package hu.promarkvf.animiservice;

import java.util.Timer;
import java.util.TimerTask;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;
	public Context maincontext;
	static int szurke = Color.DKGRAY;
	public static int kek = Color.BLUE;
	SeekBar seekbar_fenyero = null;
	TextView value_fenyero = null;
	Socket_io gep_1 = null;
	int szin_r = 0;
	int szin_g = 0;
	int szin_b = 0;
	public static float fenyero = 0.0f;
	public static int ido = 0;
	String pxtomb = "0000000000000000000000000000000000000000000000000";
	private int aktido = 0;
	TimerTask idoTask_fo;
	public static Button mutasd_button = null;
	String animiserverIP;
	int animiserverPort;
	int animiserverTimeout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		maincontext = MainActivity.this;
		setContentView(R.layout.activity_main);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("Szinteszt").setIndicator("Szinteszt", getResources().getDrawable(R.drawable.ic_battery_tab)), Szintesz.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Sebesség teszt").setIndicator("Sebesség teszt", getResources().getDrawable(R.drawable.ic_network_tab)), NetworkFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Status").setIndicator("Status", getResources().getDrawable(R.drawable.ic_device_tab)), DeviceFragment.class, null);

		szin_r = ( 0x00FF0000 & Color.DKGRAY ) / ( 256 * 256 );
		szin_g = ( 0x0000FF00 & Color.DKGRAY ) / 256;
		szin_b = 0x000000FF & Color.DKGRAY;
		fenyero = 1f / 5f;
		loadPref();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		intent.setClass(maincontext , SetPreferenceActivity.class);
		startActivityForResult(intent, 0);

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		loadPref();
	}

	public void gep_elem_click(View v) {
		// Toast.makeText(MainActivity.this, "STB: " +
		// String.valueOf(v.getId()), Toast.LENGTH_LONG).show();
		if ( v instanceof Button ) {
			ColorDrawable buttonColor = (ColorDrawable) ( (Button) v ).getBackground();
			if ( buttonColor.getColor() != szurke ) {
				v.setBackgroundColor(szurke);
			}
			else {
				v.setBackgroundColor(kek);
			}
		}
	}

	public void torol_click(final View v) {
		TableLayout table = (TableLayout) findViewById(R.id.Table00);
		for ( int i = 0; i < table.getChildCount(); i++ ) {
			View child1 = table.getChildAt(i);
			if ( child1 instanceof TableRow ) {
				TableRow tablerow = (TableRow) child1;
				for ( int j = 0; j < tablerow.getChildCount(); j++ ) {
					View child = tablerow.getChildAt(j);
					if ( child instanceof Button ) {
						child.setBackgroundColor(szurke);
					}
				}
			}
		}
	}

	public void mindszinez_click(final View v) {
		TableLayout table = (TableLayout) findViewById(R.id.Table00);
		for ( int i = 0; i < table.getChildCount(); i++ ) {
			View child1 = table.getChildAt(i);
			if ( child1 instanceof TableRow ) {
				TableRow tablerow = (TableRow) child1;
				for ( int j = 0; j < tablerow.getChildCount(); j++ ) {
					View child = tablerow.getChildAt(j);
					if ( child instanceof Button ) {
						child.setBackgroundColor(kek);
					}
				}
			}
		}
	}

	public void szinez_click(final View v) {
		int currtentColor = kek;
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(MainActivity.this, currtentColor, new OnAmbilWarnaListener() {
			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
			}

			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				TableLayout table = (TableLayout) findViewById(R.id.Table00);
				kek = color;
				for ( int i = 0; i < table.getChildCount(); i++ ) {
					View child1 = table.getChildAt(i);
					if ( child1 instanceof TableRow ) {
						TableRow tablerow = (TableRow) child1;
						for ( int j = 0; j < tablerow.getChildCount(); j++ ) {
							View child = tablerow.getChildAt(j);
							if ( child instanceof Button ) {
								ColorDrawable buttonColor = (ColorDrawable) ( (Button) child ).getBackground();
								if ( buttonColor.getColor() != szurke ) {
									child.setBackgroundColor(color);
								}
							}
						}
					}
				}
				szin_r = ( 0x00FF0000 & color ) / ( 256 * 256 );
				szin_g = ( 0x0000FF00 & color ) / 256;
				szin_b = 0x000000FF & color;
				( (Button) v ).setBackgroundColor(color);
			}
		});
		dialog.show();
	}

	public void mutat_click(final View v) {
		pxtomb = "";
		TableLayout table = (TableLayout) findViewById(R.id.Table00);
		for ( int i = 0; i < table.getChildCount(); i++ ) {
			View child1 = table.getChildAt(i);
			if ( child1 instanceof TableRow ) {
				TableRow tablerow = (TableRow) child1;
				for ( int j = 0; j < tablerow.getChildCount(); j++ ) {
					View child = tablerow.getChildAt(j);
					if ( child instanceof Button ) {
						ColorDrawable buttonColor = (ColorDrawable) ( (Button) child ).getBackground();
						if ( buttonColor.getColor() != szurke ) {
							pxtomb += "1";
						}
						else {
							pxtomb += "0";
						}
					}
				}
			}
		}

		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		gep_1 = new Socket_io("188.6.164.115", 8001, 5000);
		// gep_1 = new Socket_io(animiserverIP, animiserverPort,
		// animiserverTimeout);
		if ( gep_1.isConnect() ) {
			aktido = 0;
			idoTask_fo = idofut_socket();
		}
		else {
			Toast.makeText(MainActivity.this, getString(R.string.NotSer2net), Toast.LENGTH_LONG).show();
		}
	}

	private TimerTask idofut_socket() {
		Timer t = new Timer();
		// Set the schedule function and rate
		TimerTask idoTask2 = ( new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if ( aktido == 0 ) {
							mutasd_button.setEnabled(false);
							String ret = gep_1.socket_write(fenyero, szin_r, szin_g, szin_b, pxtomb);
							System.out.println("TT: " + ret);
						}
						if ( ido < aktido ) {
							// Kikapcsolás
							gep_1.socket_clear();
							gep_1.socket_stop();
							gep_1.socket_close();
							idoTask_fo.cancel();
							aktido = 0;
							mutasd_button.setEnabled(true);
							mutasd_button.setText(getString(R.string.str_mutat));
						}
						if ( aktido > 0 ) {
							mutasd_button.setText(getString(R.string.str_mutat) + "  (" + String.valueOf(aktido) + ")");
						}
						aktido++;
					};// --
				});// --
			};
		} );
		t.scheduleAtFixedRate(idoTask2, 0, 1000);
		return idoTask2;
	}

	/*
	 * Beállítások betöltése
	 */
	private void loadPref() {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		animiserverIP = mySharedPreferences.getString("@string/animiserverIP", "188.6.164.115");
		animiserverPort = Integer.parseInt(mySharedPreferences.getString("@string/animiserverPort", "8001"));
		animiserverTimeout = Integer.parseInt(mySharedPreferences.getString("@string/animiserverTimeout", "5000"));
	}

}
