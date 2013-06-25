package hu.promarkvf.animiservice;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableRow;
import android.widget.TextView;

public class Szintesz extends Fragment {
	SeekBar seekbar_fenyero = null;
	TextView value_fenyero = null;
	SeekBar seekbar_ido = null;
	TextView value_ido = null;
	View V;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		V = inflater.inflate(R.layout.szinteszt_view, container, false);

		MainActivity.mutasd_button = (Button) V.findViewById(R.id.mutasd_button);

		Button szin_button = (Button) V.findViewById(R.id.szin_button);
		szin_button.setBackgroundColor(MainActivity.kek);

		value_fenyero = (TextView) V.findViewById(R.id.fenyero_ertek);
		value_fenyero.setText(" " + 1);
		seekbar_fenyero = (SeekBar) V.findViewById(R.id.fenyero);
		seekbar_fenyero.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int f = progress + 1;
				MainActivity.fenyero = 1.0f / f;
				value_fenyero.setText(" " + f);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		value_ido = (TextView) V.findViewById(R.id.ido_ertek);
		value_ido.setText(" " + 1);
		seekbar_ido = (SeekBar) V.findViewById(R.id.ido);
		seekbar_ido.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int f = progress + 1;
				MainActivity.ido = f;
				value_ido.setText(" " + f);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		TableLayout table = (TableLayout) V.findViewById(R.id.jelol_sor);
		for ( int i = 0; i < table.getChildCount(); i++ ) {
			View child1 = table.getChildAt(i);
			if ( child1 instanceof TextView ) {
				TextView textview = (TextView) child1;
				textview.setOnClickListener(this.jelelol_sor);
			}
		}

		TableRow tablr = (TableRow) V.findViewById(R.id.jelol_oszlop);
		for ( int i = 0; i < tablr.getChildCount(); i++ ) {
			View child1 = tablr.getChildAt(i);
			if ( child1 instanceof TextView ) {
				TextView textview = (TextView) child1;
				textview.setOnClickListener(this.jelelol_oszlop);
			}
		}

		return V;
	}

	OnClickListener jelelol_sor = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			System.out.println("Sor: " + v.getTag());
			int sor = Integer.valueOf((String) v.getTag()) + 1;
			TableLayout table = (TableLayout) V.findViewById(R.id.Table00);
			for ( int i = 0; i < table.getChildCount(); i++ ) {
				View child1 = table.getChildAt(i);
				if ( child1 instanceof TableRow ) {
					TableRow tablerow = (TableRow) child1;
					for ( int j = 0; j < tablerow.getChildCount(); j++ ) {
						View child = tablerow.getChildAt(j);
						if ( sor == i ) {
							if ( child instanceof Button ) {
								ColorDrawable buttonColor = (ColorDrawable) ( (Button) child ).getBackground();
								switch ( radio_olv() ) {
								case 1:
									if ( buttonColor.getColor() != MainActivity.szurke ) {
										child.setBackgroundColor(MainActivity.szurke);
									}
									else {
										child.setBackgroundColor(MainActivity.kek);
									}
									break;

								case 2:
									child.setBackgroundColor(MainActivity.kek);
									break;
								case 3:
									child.setBackgroundColor(MainActivity.szurke);
									break;
								default:
									break;
								}
							}
						}
					}
				}
			}
		}
	};

	OnClickListener jelelol_oszlop = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			System.out.println("Oszlop: " + v.getTag());
			int oszlop = Integer.valueOf((String) v.getTag());
			TableLayout table = (TableLayout) V.findViewById(R.id.Table00);
			for ( int i = 0; i < table.getChildCount(); i++ ) {
				View child1 = table.getChildAt(i);
				if ( child1 instanceof TableRow ) {
					TableRow tablerow = (TableRow) child1;
					for ( int j = 0; j < tablerow.getChildCount(); j++ ) {
						View child = tablerow.getChildAt(j);
						if ( oszlop == j ) {
							if ( child instanceof Button ) {
								ColorDrawable buttonColor = (ColorDrawable) ( (Button) child ).getBackground();
								switch ( radio_olv() ) {
								case 1:
									if ( buttonColor.getColor() != MainActivity.szurke ) {
										child.setBackgroundColor(MainActivity.szurke);
									}
									else {
										child.setBackgroundColor(MainActivity.kek);
									}
									break;

								case 2:
									child.setBackgroundColor(MainActivity.kek);
									break;
								case 3:
									child.setBackgroundColor(MainActivity.szurke);
									break;
								default:
									break;
								}
							}
						}
					}
				}
			}
		}
	};

	int radio_olv() {
		RadioGroup radioGroup = (RadioGroup) V.findViewById(R.id.radioGroup1);
		int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
		switch ( checkedRadioButton ) {
		case R.id.radio_i:
			return ( 1 );
		case R.id.radio_sz:
			return ( 2 );
		case R.id.radio_t:
			return ( 3 );
		}
		return(0);
	}
}
