package com.endergeek.rookie.fragmentbasiclearn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ArticleFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    TextView articleFragmentText;

//    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        Log.d("ArticleFragment", "onCreateView called");

        // Inflate the layout for this fragment
        articleFragmentText = (TextView) inflater.inflate(R.layout.fragment_article, container, false);

        return articleFragmentText;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        articleFragmentText.setText(ArticleContent.Articles[position]);
        if(position == 0){
            articleFragmentText.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
        } else {
            articleFragmentText.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
        }
        mCurrentPosition = position;
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);

    }
}
