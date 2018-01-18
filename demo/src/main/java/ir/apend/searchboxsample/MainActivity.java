package ir.apend.searchboxsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ir.apend.searchbox.listener.OnBackButtonClickListener;
import ir.apend.searchbox.listener.OnTextSearchedListener;
import ir.apend.searchbox.ui.SearchBox;

public class MainActivity extends AppCompatActivity {

    ImageView btnSearch;
    SearchBox searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox=(SearchBox)findViewById(R.id.search_box);
        btnSearch=(ImageView)findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBox.setVisibility(View.VISIBLE);
                searchBox.openSearchBox();
            }
        });

        searchBox.textSearched(new OnTextSearchedListener() {
            @Override
            public void onTextSearched(String Search) {
                Toast.makeText(MainActivity.this,Search,Toast.LENGTH_LONG).show();
                searchBox.setVisibility(View.GONE);
            }
        });

        searchBox.backButtonClick(new OnBackButtonClickListener() {
            @Override
            public void onBackButtonClicked(View view) {
                searchBox.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(searchBox.isOpen()) {
            searchBox.closeSearchBox();
            searchBox.setVisibility(View.GONE);
        }else
            finish();
    }
}
