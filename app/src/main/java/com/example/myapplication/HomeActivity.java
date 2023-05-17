package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        showGallery();
        showContentList();
        showBottomNav();
        layoutTab();
        search();
    }

    @Override
    public void onItemClick(int position) {
        Log.d("ImageDemo", "Clicked on image " + (position + 1));
    }

    /**
     * Show a horizontal gallery of images
     */
    public void showGallery() {
        List<Integer> images = Arrays.asList(R.drawable.pic2, R.drawable.pic2, R.drawable.pic2);
        for (int i = 0; i < images.size(); i++) {
            int imageResourceId = images.get(i);
            Log.d("ImageDemo", "Image " + (i + 1) + " resource ID: " + imageResourceId);
        }
        GalleryAdapter adapter = new GalleryAdapter(images, this);
        RecyclerView galleryRecyclerView = findViewById(R.id.galleryRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        galleryRecyclerView.setLayoutManager(layoutManager);
        galleryRecyclerView.setAdapter(adapter);
    }

    /**
     * Show a vertical list of content items
     */
    public void showContentList() {
        DbHandler dbHandler = new DbHandler(this);
        ArrayList<Recipe> recipes = dbHandler.getAllRecipe(" ID ");
//         for (int i = 0; i < posts.size(); i++) {
//            Post post = posts.get(i);
////            Log.d("RecipeDemo", "Recipe " + (i + 1) + " name: " + recipe.getName());
//         }
//        ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
//        recipeSteps.add(new RecipeStep("1. Put a 1.7kg Tesco Finest smoked Wiltshire gammon joint in a large pan. Cover with 1 litre apple juice and top up with cold water to cover. Add 2 bay leaves, 6 peppercorns, 1 star anise, 1 roughly chopped carrot and stick of celery and 1 halved small onion.", R.drawable.pic11));
//        recipeSteps.add(new RecipeStep("2. Bring to the boil, then turn down and simmer for 1 hour 30 minutes, topping up with boiling water (enough to cover), if needed.", R.drawable.pic12));
//        recipeSteps.add(new RecipeStep("3. Preheat the oven to gas 5, 190°C, fan 170°C. To make a glaze, mix 100ml maple syrup, 1 tbsp grainy mustard, 1 tbsp soy sauce and the zest and juice of 1 clementine in a small pan. Bring to the boil, then bubble for a couple of minutes until syrupy.", R.drawable.pic13));
//        recipeSteps.add(new RecipeStep("4. Remove the ham and allow to cool a little. Carefully remove the skin, leaving an even layer of fat. Score the fat all over in a criss-cross pattern – this allows the glaze to soak into the ham, and the fat to get extra crispy. Stud with about 16 cloves and place the ham in a small roasting tray lined with baking paper.", R.drawable.pic14));
//        recipeSteps.add(new RecipeStep("5. Spoon half the glaze over the fat and roast in the oven for 15 minutes. Then spoon over the rest, and return to the oven for a further 25 minutes. Baste the ham with the pan juices and turn the tin every so often so the fat colours evenly.", R.drawable.pic15));
//        recipeSteps.add(new RecipeStep("6. Remove from the oven and allow to rest for 5 minutes before carving.", R.drawable.pic16));
//        ArrayList<RecipeStep> recipeSteps2 = new ArrayList<>();
//        recipeSteps2.add(new RecipeStep("1. In a large, non-metallic bowl, whisk together a marinade for the lamb with 3 crushed garlic cloves, 2 bay leaves, 1 tbsp Dijon mustard and 400ml red wine. Make multiple 1cm deep incisions in the lamb then submerge in the marinade. Cover and chill in the fridge for a minimum of 2 hours or overnight. Remove the lamb from the fridge 1 hour before you’re ready to roast.", R.drawable.pic21));
//        recipeSteps2.add(new RecipeStep("2. Preheat the oven to gas 6, 200°C, fan 180°C. Roughly chop 2 carrots, 3 celery stalks and 1 red onion. Tip into a large roasting tray and spread out in an even layer. Add 3 sprigs of rosemary. Remove the lamb from the marinade (reserving the marinade for later) and place on top of the vegetables. Season and rub with 2 tbsp oil.", R.drawable.pic22));
//        recipeSteps2.add(new RecipeStep("3. Roast for 30 mins then pour the marinade into the roasting tray. Reduce the oven temperature to gas 4, 180°C, fan 160°C and roast for a further 2 hours 30 mins. Remove the lamb to a plate, tightly cover with foil and leave to rest for 15 mins.", R.drawable.pic23));
//        recipeSteps2.add(new RecipeStep("4. Meanwhile, make the gravy. Strain the juices from the roasting tray into a saucepan and add 150ml chicken stock, discarding the veg.", R.drawable.pic24));
//        recipeSteps2.add(new RecipeStep("5. Remove 3 tbsp of the liquid to a small bowl and mix with 3 tbsp plain flour to make a paste. Add this back to the saucepan and simmer for 10 mins. Stir in 2 tbsp redcurrant jelly and any resting juices from the lamb.", R.drawable.pic25));
//        recipeSteps2.add(new RecipeStep("6. Thinly carve the lamb and serve with the red wine gravy and sides of your choice.", R.drawable.pic26));
//        ArrayList<RecipeStep> recipeSteps3 = new ArrayList<>();
//        recipeSteps3.add(new RecipeStep("1. Preheat the oven to gas 4, 180°C, fan 160°C. Line a 2lb loaf tin with 2 large sheets of clingfilm, one across and one lengthways, with plenty overhanging the sides. Taking 2 x 84g packs of prosciutto, use a third of the prosciutto to line the base and one side of the tin, leaving enough overhang to cover the top later. Use the plastic sheets from the prosciutto packet to press it down, then repeat with the remaining prosciutto to cover the other side and ends of the tin, using spare pieces to patch any holes.", R.drawable.pic31));
//        recipeSteps3.add(new RecipeStep("2. Pat dry 190g chicken livers with kitchen paper, then pulse to a coarse paste in a food processor. Squeeze 300g venison sausages from their skins into a bowl. Add the liver, 1 beaten egg, 60g fresh white breadcrumbs, 40g shelled and chopped pistachios, 1 crushed garlic clove, 30g dried cranberries, 1 tbsp chopped thyme leaves and 1 tbsp ruby port; season. Mix well with a spoon or your hands.", R.drawable.pic32));
//        recipeSteps3.add(new RecipeStep("3. Put a third of the filling in the base of the lined loaf tin. Add 150g diced chicken, followed by another third of the filling, another 150g diced chicken, and finally the last of the filling. Fold the prosciutto over the top.", R.drawable.pic33));
//        recipeSteps3.add(new RecipeStep("4. Fold the clingfilm over the prosciutto, cover tightly with foil and place in a roasting tin. Pour enough boiling water into the tin to come ⅔ of the way up the sides to create a bain-marie*. Carefully place in the oven and cook for 1½-2 hrs until it feels firm to the touch and has shrunk away from the sides of the tin. When tested with a meat thermometer, the terrine should have an internal temperature of 75°C. If you don't have a meat thermometer, you can use a metal skewer. Push it through the clingfilm and into the centre of the terrine for 10 secs; it should be very hot. If in any doubt, return to the oven for another 10 mins, then check again.", R.drawable.pic34));
//        recipeSteps3.add(new RecipeStep("5. Once the terrine is cooked, remove the loaf tin from the bain-marie and transfer to a large plate. If you have another loaf tin the same size, place this on top, or cut a piece of cardboard to fit. Top with heavy weights (such as a few tins of beans) and leave to cool to room temperature. Transfer to the fridge and leave overnight with the weights still on top.", R.drawable.pic35));
//        recipeSteps3.add(new RecipeStep("6. When ready to serve, remove the weights and foil, then peel back the clingfilm. Wipe off any jelly, then carefully turn the terrine upside down onto a serving board. Lift the tin off and remove the clingfilm.", R.drawable.pic36));
//        recipeSteps3.add(new RecipeStep("7. Scatter with extra pistachios, cranberries and thyme sprigs, then cut the terrine into chunky slices to serve. Eat within 3 days. ", R.drawable.pic37));
//
//        List<Recipe> recipes = Arrays.asList(
//                new Recipe(R.drawable.pic16, "How to cook a festive gammon", "Traditionally served on Boxing Day, glistening gammon is a Christmas classic.", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
//                new Recipe(R.drawable.pic26, "How to cook a roast shoulder of lamb", "A shoulder of lamb is an Easter classic. Let's do it!", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps2),
//                new Recipe(R.drawable.pic37, "How to make a Christmas terrine", "A game terrine is an impressive centrepiece that can be made ahead for a festive buffet or dinner party starter", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps3)
////                new Recipe(R.drawable.pic2, "Title 4", "Description 4", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 5", "Description 5", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 6", "Description 6", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 7", "Description 7", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 8", "Description 8", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 9", "Description 9", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 10", "Description 10", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 11", "Description 11", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 12", "Description 12", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 13", "Description 13", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 14", "Description 14", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 15", "Description 15", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 16", "Description 16", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title  17", "Description 17", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 18", "Description 18", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 19", "Description 19", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 20", "Description 20", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
////                new Recipe(R.drawable.pic2, "Title 21", "Description 21", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps)
//        );
        HomeAdapter adapter2 = new HomeAdapter(this, recipes);
        RecyclerView contentRecyclerView = findViewById(R.id.content_item_layout);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        contentRecyclerView.setLayoutManager(layoutManager2);
        contentRecyclerView.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new HomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Recipe item) {
                // Navigate to the detail activity
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("item", item);
                Log.d("ITEM", "Title: " + item.getTitle());
                Log.d("ITEM", "Description: " + item.getDescription());
                startActivity(intent);
            }
        });
        adapter2.setOnEditClickListener(new HomeAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Recipe recipe) {
                Intent editIntent = new Intent(HomeActivity.this,RecipeFormActivity.class);
                editIntent.putExtra("recipeId", recipe.getId());
                startActivity(editIntent);
            }
        });
    }

    /**
     * Show bottom navigation bar
     */
    public void showBottomNav() {
        // show bottom navigation, but don't do anything when a menu item is clicked

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_1:
                        // Handle menu item 1 click
                        return true;
                    case R.id.menu_item_2:
                        //Navigate to recipe add activity
                        Intent intent = new Intent(HomeActivity.this, RecipeFormActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_item_3:
                        Toast.makeText(HomeActivity.this, "Please wait to release :)", Toast.LENGTH_SHORT).show();
                        // TODO IF USER IS NOT LOGGED IN, NAVIGATE TO LOGIN ACTIVITY
                        return true;
                    case R.id.menu_item_4:
                        // TODO IF USER IS NOT LOGGED IN, NAVIGATE TO LOGIN ACTIVITY
                        // Navigate to the profile activity
                        Intent intent2 = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        return true;
                    default:
                        return false;
                }
            }

        });
    }

    /**
     * Show top navigation bar
     */
    public void layoutTab(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // Handle click on first tab item
                        Toast.makeText(HomeActivity.this, "Tab 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Handle click on second tab item
                        Toast.makeText(HomeActivity.this, "Tab 2", Toast.LENGTH_SHORT).show();
                        // navigate to category activity
                        Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    public void search(){
        // Get a reference to the SearchView
        SearchView searchView = findViewById(R.id.search_view);
        // Set up a listener for when the user submits a search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO WILL BE IMPLEMENTED IN THE FUTURE
                Toast.makeText(HomeActivity.this, "Search: submit " + query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO WILL BE IMPLEMENTED IN THE FUTURE
                Toast.makeText(HomeActivity.this, "Search: change" + newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
