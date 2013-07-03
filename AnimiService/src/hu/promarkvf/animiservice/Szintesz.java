package hu.promarkvf.animiservice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.Toast;
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

		MainActivity.szin_button = (Button) V.findViewById(R.id.szin_button);
		MainActivity.szin_button.setBackgroundColor(MainActivity.kek);
		MainActivity.matrix_table = (TableLayout) V.findViewById(R.id.Table00);

		value_fenyero = (TextView) V.findViewById(R.id.fenyero_ertek);
		value_fenyero.setText(" " + MainActivity.MAXFENYERO);
		seekbar_fenyero = (SeekBar) V.findViewById(R.id.fenyero);
		seekbar_fenyero.setMax(MainActivity.MAXFENYERO - 1);
		seekbar_fenyero.setProgress(MainActivity.MAXFENYERO - 1);
		seekbar_fenyero.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int f = progress + 1;
				MainActivity.fenyero = 1.0f / f;
				value_fenyero.setText(" " + f);
				MainActivity.szinez(MainActivity.szin_r, MainActivity.szin_g, MainActivity.szin_b, MainActivity.fenyero, 0);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		value_ido = (TextView) V.findViewById(R.id.ido_ertek);
		value_ido.setText(" " + 1);
		seekbar_ido = (SeekBar) V.findViewById(R.id.ido);
		seekbar_ido.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int f = progress + 1;
				MainActivity.ido = f;
				value_ido.setText(" " + f);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
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

		MainActivity.ed_r = (EditText) V.findViewById(R.id.editText_r);
		MainActivity.ed_g = (EditText) V.findViewById(R.id.editText_g);
		MainActivity.ed_b = (EditText) V.findViewById(R.id.editText_b);

		MainActivity.ed_r.setText(String.valueOf(MainActivity.szin_r));
		MainActivity.ed_r.addTextChangedListener(new tw(MainActivity.ed_r));
		MainActivity.ed_g.setText(String.valueOf(MainActivity.szin_g));
		MainActivity.ed_g.addTextChangedListener(new tw(MainActivity.ed_g));
		MainActivity.ed_b.setText(String.valueOf(MainActivity.szin_b));
		MainActivity.ed_b.addTextChangedListener(new tw(MainActivity.ed_b));

		return V;
	}

	OnClickListener jelelol_sor = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// System.out.println("Sor: " + v.getTag());
			int sor = Integer.valueOf((String) v.getTag()) + 1;
			for ( int i = 0; i < MainActivity.matrix_table.getChildCount(); i++ ) {
				View child1 = MainActivity.matrix_table.getChildAt(i);
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
			// System.out.println("Oszlop: " + v.getTag());
			int oszlop = Integer.valueOf((String) v.getTag());
			for ( int i = 0; i < MainActivity.matrix_table.getChildCount(); i++ ) {
				View child1 = MainActivity.matrix_table.getChildAt(i);
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
		return ( 0 );
	}

	private class tw implements TextWatcher {
		private EditText mEditText;

		public tw(EditText e) {
			mEditText = e;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			String value = s.toString();
			int intv;
			try {
				intv = Integer.parseInt(value);
				if ( intv > 255 ) {
					mEditText.setError(getString(R.string.rgb_err));
				}
				else {
					switch ( mEditText.getId() ) {
					case R.id.editText_r:
						MainActivity.szin_r = intv;
						break;
					case R.id.editText_g:
						MainActivity.szin_g = intv;
						break;
					case R.id.editText_b:
						MainActivity.szin_b = intv;
						break;

					default:
						break;
					}
					mEditText.setError(null);
					MainActivity.szinez(MainActivity.szin_r, MainActivity.szin_g, MainActivity.szin_b, MainActivity.fenyero, 1);
				}
			}
			catch ( Exception e ) {
				Toast.makeText(MainActivity.maincontext, getString(R.string.NotSer2net), Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	}
}
