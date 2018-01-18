package ir.fatemehmsp.searchbox.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


import ir.fatemehmsp.searchbox.R;
import ir.fatemehmsp.searchbox.listener.OnBackButtonClickListener;
import ir.fatemehmsp.searchbox.listener.OnTextSearchedListener;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Fatemeh on 1/10/2018.
 */

public class SearchBox extends RelativeLayout implements View.OnClickListener, TextWatcher {

   public ImageView btnClear,btnBack,imgSearch;
   public EditText edtSearch;
   public LinearLayout layoutSearch;

    OnBackButtonClickListener onBackButtonClickListener;
    OnTextSearchedListener onTextSearchedListener;

    boolean isOpen;

    public SearchBox(Context context) {
        super(context);
        init();
    }

    public SearchBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        View rootLayout=inflate(getContext(), R.layout.search_box, null);

        btnBack=(ImageView)rootLayout.findViewById(R.id.btn_search_back);
        btnClear=(ImageView)rootLayout.findViewById(R.id.btn_search_clear);
        imgSearch=(ImageView)rootLayout.findViewById(R.id.img_search);
        layoutSearch=(LinearLayout)rootLayout.findViewById(R.id.layout_search);

        edtSearch=(EditText)rootLayout.findViewById(R.id.edt_search);
        addView(rootLayout);



        btnBack.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        edtSearch.addTextChangedListener(this);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    onTextSearchedListener.onTextSearched(edtSearch.getText().toString());
                    closeSearchBox();
                    return true;
                }
                return false;

            }

        });
    }
    public void backButtonClick(OnBackButtonClickListener onBackButtonClickListener){
        this.onBackButtonClickListener=onBackButtonClickListener;
    }
    public void textSearched(OnTextSearchedListener onTextSearchedListener){
        this.onTextSearchedListener = onTextSearchedListener;
    }

    public void openSearchBox(){
        YoYo.with(Techniques.SlideInLeft).duration(500).playOn(layoutSearch);
        showKeyboard();
        isOpen=true;

    }
    public boolean isOpen(){return isOpen;}

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        edtSearch.requestFocus();
        inputMethodManager.showSoftInput(edtSearch, 0);
    }

    public void closeSearchBox(){
        isOpen=false;
        hideKeyboard();
        edtSearch.getText().clear();
        btnClear.setVisibility(INVISIBLE);
        imgSearch.setVisibility(VISIBLE);

    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtSearch.getApplicationWindowToken(),0);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_search_clear) {
            edtSearch.getText().clear();
            btnClear.setVisibility(INVISIBLE);
            imgSearch.setVisibility(VISIBLE);

        } else if (i == R.id.btn_search_back) {
            hideKeyboard();
            onBackButtonClickListener.onBackButtonClicked(view);

        } else {
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        btnClear.setVisibility(VISIBLE);
        imgSearch.setVisibility(GONE);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setHint(String hint){
        edtSearch.setHint(hint);
    }

}
