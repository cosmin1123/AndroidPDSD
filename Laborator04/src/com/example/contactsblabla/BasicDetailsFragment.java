package com.example.contactsblabla;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BasicDetailsFragment extends Fragment{
	  @Override
	  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
		 
		  return layoutInflater.inflate(R.layout.fragment_basic_detail, container, false);
	  }
	  

	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  Button show = (Button) getActivity().findViewById(R.id.button3);
		  
		  show.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				  AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.containerBottom);
				  if (additionalDetailsFragment == null) {
				    fragmentTransaction.add(R.id.containerBottom, new AdditionalDetailsFragment());
				    ((Button)v).setText("Hide");
				    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
				  } else {
				    fragmentTransaction.remove(additionalDetailsFragment);
				    ((Button)v).setText("Show");
				    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
				  }
				  fragmentTransaction.commit();
				
			}
		});
		  
		  
	  }

}
