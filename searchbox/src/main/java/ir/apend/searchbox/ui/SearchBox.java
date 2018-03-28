package ir.apend.searchbox.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import ir.apend.searchbox.R;
import ir.apend.searchbox.helper.DataBaseHelper;
import ir.apend.searchbox.helper.GeneralHelper;
import ir.apend.searchbox.listener.OnBackButtonClickListener;
import ir.apend.searchbox.listener.OnTextSearchedListener;
import ir.apend.searchbox.model.HistoryModel;
import ir.apend.searchbox.ui.adapter.HistoryAdapter;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Fatemeh on 1/10/2018.
 */

public class SearchBox extends RelativeLayout implements TextWatcher {


    public ImageView btnClear,btnBack,imgSearch;
    public EditText edtSearch;
    public LinearLayout layoutSearch;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private RelativeLayout parent;

    private List<HistoryModel> historyModels=new ArrayList<>();
    OnBackButtonClickListener onBackButtonClickListener;
    OnTextSearchedListener onTextSearchedListener;

    private boolean isOpen;

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
        parent=(RelativeLayout)rootLayout.findViewById(R.id.parent);

        edtSearch=(EditText)rootLayout.findViewById(R.id.edt_search);

        recyclerView=(RecyclerView)rootLayout.findViewById(R.id.history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyModels.clear();
        if(DataBaseHelper.getInstance(getContext()).getCountOfHistory()>0)
            historyModels=DataBaseHelper.getInstance(getContext()).getAllOrderHistory();
        historyAdapter=new HistoryAdapter(historyModels,getContext());
        recyclerView.setAdapter(historyAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonClickListener.onBackButtonClicked(view);
                closeSearchBox();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.getText().clear();
                btnClear.setVisibility(View.INVISIBLE);
                imgSearch.setVisibility(View.VISIBLE);
            }
        });

        edtSearch.addTextChangedListener(this);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(DataBaseHelper.getInstance(getContext()).getCountOfHistory()>4)
                        DataBaseHelper.getInstance(getContext()).deleteHistory
                                (DataBaseHelper.getInstance(getContext()).getAllHistory().get(0).getId());
                    DataBaseHelper.getInstance(getContext()).insertHistory(edtSearch.getText().toString());
                    onTextSearchedListener.onTextSearched(edtSearch.getText().toString());
                    closeSearchBox();
                    return true;
                }
                return false;

            }

        });

        addView(rootLayout);
    }


    public void backButtonClick(OnBackButtonClickListener onBackButtonClickListener){
        this.onBackButtonClickListener=onBackButtonClickListener;
    }
    public void textSearched(OnTextSearchedListener onTextSearchedListener){
        this.onTextSearchedListener = onTextSearchedListener;
    }

    public void openSearchBox(){
        init();
        //YoYo.with(Techniques.SlideInLeft).duration(500).playOn(layoutSearch);
        showSearchBox();
        //showKeyboard();
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
        hideSearchBox();
        btnClear.setVisibility(GONE);
        imgSearch.setVisibility(VISIBLE);

    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtSearch.getApplicationWindowToken(),0);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        btnClear.setVisibility(View.VISIBLE);
        imgSearch.setVisibility(View.GONE);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setHint(String hint){
        edtSearch.setHint(hint);
    }

    private void showSearchBox() {
        if (GeneralHelper.isLollipopOrNewer()) {
            layoutSearch.post(() -> {
                // get the center for the clipping circle
                int cx = getResources().getDimensionPixelSize(R.dimen.toolbar_item_size) / 2;
                int cy = (layoutSearch.getTop() + layoutSearch.getBottom()) / 2;

                // get the final radius for the clipping circle
                int dx = Math.max(cx, layoutSearch.getWidth() - cx);
                int dy = Math.max(cy, layoutSearch.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                // Android native animator
                Animator animator = ViewAnimationUtils.createCircularReveal(parent, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        parent.setVisibility(VISIBLE);
                        layoutSearch.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showKeyboard();
                    }
                });
                animator.setDuration(500);
                animator.start();
            });
        } else {
            YoYo.with(Techniques.SlideInLeft).withListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    parent.setVisibility(VISIBLE);
                    layoutSearch.setVisibility(View.VISIBLE);

                }
            }).duration(500).playOn(parent);
        }
    }

    private void hideSearchBox() {
        if (GeneralHelper.isLollipopOrNewer()) {
            layoutSearch.post(() -> {
                // get the center for the clipping circle
                int cx = getResources().getDimensionPixelSize(R.dimen.toolbar_item_size) / 2;
                int cy = (layoutSearch.getTop() + layoutSearch.getBottom()) / 2;

                // get the final radius for the clipping circle
                float finalRadius = layoutSearch.getWidth();

                // Android native animator
                Animator animator = ViewAnimationUtils.createCircularReveal(parent, cx, cy, finalRadius, 0);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideKeyboard();
                        edtSearch.setText("");
                        parent.setVisibility(View.GONE);
                    }
                });
                animator.setDuration(500);
                animator.start();
            });
        } else {
            YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                     hideKeyboard();
                    edtSearch.setText("");
                    parent.setVisibility(GONE);
                }
            }).duration(500).playOn(parent);
        }
    }



}
