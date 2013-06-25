package hu.promarkvf.animiservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeviceFragment extends Fragment  {
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 // Inflate the layout for this fragment
		  View V = inflater.inflate(R.layout.device_view, container, false);

	     
	        return V;
	    }
}
