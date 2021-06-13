package tl.betapp.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tl.betapp.BaseFragment;
import tl.betapp.R;
import tl.betapp.databinding.FragmentHomeBinding;
import tl.betapp.view.adaptor.SportListAdapter;
import tl.betapp.view.model.SportModel;
import tl.betapp.view.model.SportModelRoom;
import tl.betapp.view.utils.MainLayout;
import tl.betapp.view.utils.UserDatabase;

public class HomeFragment extends BaseFragment {
    private MainLayout mHeaderLayout;
      FragmentHomeBinding binding;
    private FragmentActivity mActivity;
    private SportListAdapter sportListAdapter;
    UserDatabase  userDatabase;
    private ArrayList<SportModel> arrayList= new ArrayList<>();

    public static HomeFragment getInstance(MainLayout headerLayout, boolean backBtn) {
        HomeFragment fragment = new HomeFragment();
        fragment.mHeaderLayout = headerLayout;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        this.mActivity=getActivity();
        userDatabase=UserDatabase.getInstance(mActivity);
        initViews();

    }

    private void initViews() {
        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        binding.sportRecyclerview.setLayoutManager(manager);

        sportListAdapter = new SportListAdapter(mActivity, new SportListAdapter.AdapterCallBack() {
            @Override
            public void itemClickListener(SportModel model, Boolean type,int pos) {
                logConfig.printP("data",model.getStatus()+"type"+type+"--->"+pos);
                      if (type)
                      {
                                  model.setStatus(true);
                                  getList().set(pos,model);
                                  arrayList.set(pos,model);
                                  logConfig.printP("List Status",""+getList().get(pos).getStatus());
                                  logConfig.printP("List Status  arrayList",""+arrayList.get(pos).getStatus());


                      }
                      else
                      {

                          model.setStatus(false);
                          getList().set(pos,model);
                          arrayList.set(pos,model);
                          getList().get(pos).setStatus(false);
                          logConfig.printP(" Elese " + "List Status",""+getList().get(pos).getStatus());
                          logConfig.printP(" arrayList " + "List Status",""+arrayList.get(pos).getStatus());
                      }

            }
        });
        binding.sportRecyclerview.setAdapter(sportListAdapter);
        arrayList.addAll(getList());
        sportListAdapter.setList(getList());

          binding.doneBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  AsyncTask.execute(new Runnable() {
                      @Override
                      public void run() {
                          for (int i=0;i<arrayList.size();i++)
                          {
                              if (arrayList.get(i).getStatus())
                              {
                                  logConfig.printP("List","Status   "+arrayList.get(i).getStatus());

                                  SportModelRoom sportModelRoom = new SportModelRoom(arrayList.get(i).getSportName());
                                  userDatabase.userDao().insertSport(sportModelRoom);
                              }else {
                                  logConfig.printP("List","False Status    "+arrayList.get(i).getStatus());
                              }
                          }
                      }
                  });
              }
          });

          AsyncTask.execute(new Runnable() {
              @Override
              public void run() {
                  logConfig.printP("Room List",""+userDatabase.userDao().getList().size());
              }
          });
    }

    private void saveData(SportModel model) {



    }
}