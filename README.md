# SearchBox
A beautiful and also simple searchbox for android!

# Getting Started
First add xml view :

    <ir.apend.searchbox.ui.SearchBox
        android:id="@+id/search_box"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />
then , add searchbox :
        
     SearchBox searchBox=(SearchBox)findViewById(R.id.search_box);
     
      searchBox.textSearched(new OnTextSearchedListener() {
            @Override
            public void onTextSearched(String Search) {
                //text search
                Toast.makeText(MainActivity.this,Search,Toast.LENGTH_LONG).show();
            }
        });
        
         searchBox.backButtonClick(new OnBackButtonClickListener() {
            @Override
            public void onBackButtonClicked(View view) {
                 //do where you want back
            }
        });

# Installing
Add library module to your project and that's it! Gradle support will be added soon.
