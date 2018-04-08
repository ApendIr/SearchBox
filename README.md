# SearchBox
A beautiful and also simple searchbox for android!

## Screenshots

![screenshots](https://user-images.githubusercontent.com/35394143/38458022-cd1f62ca-3aad-11e8-9644-b324544d733b.gif)

[Download Demo](https://github.com/ApendIr/SearchBox/blob/master/demo/release/demo-release.apk?raw=true)

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

#### Gradle
```groovy
1. Add the JitPack repository to your build file
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

2. Add the dependency
dependencies {
	       compile 'com.github.ApendIr:SearchBox:1.0'
}
```
#### Maven

```xml
1. Add the JitPack repository to your build file
<repositories>
     <repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>

2. Add the dependency
<dependency>
	<groupId>com.github.ApendIr</groupId>
	 <artifactId>SearchBox</artifactId>
	<version>1.0</version>
</dependency>
```


## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
