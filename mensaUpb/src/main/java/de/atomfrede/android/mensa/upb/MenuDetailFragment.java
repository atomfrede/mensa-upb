package de.atomfrede.android.mensa.upb;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.atomfrede.android.mensa.upb.data.Allergene;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends DialogFragment{

    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_LISTING = "ARG_LISTING";
    @ViewById(R.id.title_text)
    TextView textTitle;

    @ViewById(R.id.text_allergene)
    TextView textAllergens;

    public static MenuDetailFragment newInstance(String title, String allergeneList) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_LISTING, allergeneList);

        MenuDetailFragment menuDetailFragment = new MenuDetailFragment_();
        menuDetailFragment.setArguments(args);

        return menuDetailFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Details");
        return dialog;
    }

    @AfterViews
    public void afterViews() {
        final String title = getArguments().getString(ARG_TITLE);
        final String listing = getArguments().getString(ARG_LISTING);

        textTitle.setText(title);

        if(listing != null) {
            textAllergens.setText(Allergene.getExplanation(listing));
        } else {
            textAllergens.setText("");
        }



    }
}
