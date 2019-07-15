package wm.wastemarche.ui.activities.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import wm.wastemarche.R;
import wm.wastemarche.ui.activities.drawer.ConsultationsFragment;
import wm.wastemarche.ui.activities.drawer.TransportationFragment;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    public static MainActivity shared;
    private Dialog dialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = this;

        final BottomNavigationView tabBar = findViewById(R.id.tab_bar);
        tabBar.setOnNavigationItemSelectedListener(this);

        final NavigationView drawerView = findViewById(R.id.drawer_view);
        drawerView.setNavigationItemSelectedListener(this);

        changeCurrentFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        setSelectedTab(item.getItemId());
        return true;
    }

    public void setSelectedTab(final int id) {
        final android.support.v4.app.Fragment selectedFragment;
        switch (id) {
            case R.id.home_tab:
                selectedFragment = new HomeFragment();
                break;
            case R.id.my_items_tab:
                selectedFragment = new MyItemsFragment();
                break;
            case R.id.sell_tab:
                selectedFragment = new SellFragment();
                break;
            case R.id.my_proposals_tab:
                selectedFragment = new MyProposalsFragment();
                break;
            case R.id.buy_tab:
                selectedFragment = new BuyFragment();
                break;
            case R.id.transportation_item:
                selectedFragment = new TransportationFragment();
                break;
            case R.id.consultation_item:
                selectedFragment = new ConsultationsFragment();
                break;
            case R.id.bidding_item:
                selectedFragment = new BiddingFragment();
                break;
            default:
                selectedFragment = new Fragment();
        }
        changeCurrentFragment(selectedFragment);
        closeDrawer();
    }

    public void changeCurrentFragment(final Fragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.current_tab, fragment);
        transaction.addToBackStack("sdf");
        transaction.commit();

        updateTabBarIcon();
    }

    private void updateTabBarIcon() {
        if (getSupportFragmentManager().getFragments().isEmpty()) {
            return;
        }

        final Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);

        if (currentFragment instanceof HomeFragment) {
            setTabBarSelectedIcon(R.id.home_tab);
        } else if (currentFragment instanceof MyItemsFragment) {
            setTabBarSelectedIcon(R.id.my_items_tab);
        } else if (currentFragment instanceof SellFragment) {
            setTabBarSelectedIcon(R.id.sell_tab);
        } else if (currentFragment instanceof MyProposalsFragment) {
            setTabBarSelectedIcon(R.id.my_proposals_tab);
        } else if (currentFragment instanceof BuyFragment) {
            setTabBarSelectedIcon(R.id.buy_tab);
        }
    }

    private void setTabBarSelectedIcon(final int id) {
        final BottomNavigationView tabBar = findViewById(R.id.tab_bar);
        final Menu menu = tabBar.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            final MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == id);
        }
    }

    @Override
    public void onBackPressed() {
        //getSupportFragmentManager().popBackStack();
        super.onBackPressed();
        //updateTabBarIcon();
    }

    public void popupView(final View view) {
        dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void closePopup() {
        dialog.dismiss();
    }

    public void openDrawer() {
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();
    }
}
