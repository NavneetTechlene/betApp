package tl.betapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import tl.betapp.BaseCompatActivity;
import tl.betapp.BaseFragment;
import tl.betapp.R;
import tl.betapp.databinding.ActivityDashBoardBinding;
import tl.betapp.view.fragment.HomeFragment;
import tl.betapp.view.utils.MainLayout;

public class DashBoardActivity extends BaseCompatActivity {
    View mHeaderLayout;
    ActivityDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_dash_board);

        mHeaderLayout = findViewById(R.id.headerBar);
        changeFragment(0,getResources().getString(R.string.app_name));





    }
    private void clearBackStack() {

        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            /*FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
            FragmentManager fragmentManager = getSupportFragmentManager();
            // this will clear the back stack and displays no animation on the screen
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }



    }
    private void changeFragment(int position, String title) {
        BaseFragment mBaseFragment;
        FragmentManager mFragmentManager;
        FragmentTransaction mFragmentTransaction;
        Bundle data = new Bundle();
        data.putString("Title", title);
        switch (position) {
            case 0:
                mBaseFragment = HomeFragment.getInstance(new MainLayout(mHeaderLayout), false);
                if (mBaseFragment != null) {
                    clearBackStack();
                    mBaseFragment.setArguments(data);
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.container, mBaseFragment, String.valueOf(position));
                    mFragmentTransaction.commit();
                }
                break;
        }
    }
}