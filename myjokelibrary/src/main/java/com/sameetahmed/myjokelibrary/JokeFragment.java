package com.sameetahmed.myjokelibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JokeFragment extends Fragment {
    String mJoke;


    public JokeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mJoke = bundle.getString(JokeActivity.JOKE_KEY);
        }

        View view = inflater.inflate(R.layout.fragment_joke, container, false);

        TextView jokeText = (TextView) view.findViewById(R.id.tv_joke);
        jokeText.setText(mJoke);


        // Inflate the layout for this fragment
        return view;
    }

}
