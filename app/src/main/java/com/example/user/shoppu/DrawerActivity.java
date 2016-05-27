package com.example.user.shoppu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.fragments.DoctorsFragment;
import com.example.user.shoppu.fragments.ProfileFragment;
import com.example.user.shoppu.fragments.PurchaseHistoryFragment;
import com.example.user.shoppu.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView = null;
    public DrawerLayout drawer = null;
    private String jsonCurrentUser;

    @Bind(R.id.textView_pacient_name)
    TextView textView_pacient_name;

    @Bind(R.id.textView_pacient_email)
    TextView textView_pacient_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

         jsonCurrentUser = getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND)
                .getString(getString(R.string.current_user_key), "{\n" +
                        "  \"blood_type\": \"B++\",\n" +
                        "  \"birthday\": \"9999-12-12\",\n" +
                        "  \"height\": 1,\n" +
                        "  \"weight\": 69,\n" +
                        "  \"allergies\": \"none\",\n" +
                        "  \"gender\": \"macho\",\n" +
                        "  \"user\": {\n" +
                        "    \"email\": \"test@hotmail.com\",\n" +
                        "    \"name\": \"Test\",\n" +
                        "    \"lastname\": \"Perez\",\n" +
                        "    \"mobile\": \"9993939393\",\n" +
                        "    \"token\": \"22c07aa8cca26a484b707e363dd90f3d\",\n" +
                        "    \"user_type\": null\n" +
                        "  }\n" +
                        "}");

        User currentUser = Utils.toUserAtributtes(jsonCurrentUser);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView_pacient_name.setText(currentUser.getUserAttributes().getFullName());
        textView_pacient_email.setText(currentUser.getUserAttributes().getEmail());

        //Set the mainFragment
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.user_key), jsonCurrentUser);
        PurchaseHistoryFragment fragment = new PurchaseHistoryFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_drawer, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.user_key), jsonCurrentUser);

        if (id == R.id.profile) {
            fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        }else if(id == R.id.purchases){
            fragment = new PurchaseHistoryFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;
        }else if (id == R.id.find_product) {
            fragment = new DoctorsFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.nav_logout){
            SharedPreferences.Editor edit = getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND).edit();
            edit.clear();
            edit.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if (fragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_drawer, fragment)
                    .commit();

            ///item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
