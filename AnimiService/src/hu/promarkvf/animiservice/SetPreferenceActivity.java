package hu.promarkvf.animiservice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class SetPreferenceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content,
                new Fragment()).commit();
	}

}

