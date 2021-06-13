package tl.betapp.view.adaptor;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tl.betapp.R;
import tl.betapp.view.model.SportModelRoom;
import tl.betapp.view.utils.Utility;
import tl.betapp.view.widgets.RegularTextView;

import static android.text.TextUtils.isEmpty;

/**
 * Created by jitendra on 06/02/2021.
 */
public class FavSportListAdapter extends RecyclerView.Adapter<FavSportListAdapter.ViewHolder> {

    private List<SportModelRoom> mList = new ArrayList<>();
    private Activity mActivity;
    private AdapterCallBack mCallBack;
    private Utility utility;

    public FavSportListAdapter(Activity activity, AdapterCallBack callBack) {
        this.mActivity = activity;
        this.mCallBack = callBack;
        this.utility = new Utility();
    }

    public void setList(List<SportModelRoom> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_selected_league, parent, false);

        return new ViewHolder(itemView, new ViewHolder.ItemClicKListener() {
            @Override
            public void onItemListener(View view, int position) {
                mCallBack.itemClickListener(mList.get(position), false, position);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SportModelRoom model = mList.get(position);

        holder.sportNameTV.setText("" + model.getName());
        holder.sportIV.setBackgroundResource(R.drawable.icon_sports_list_1);
        if (position==0)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sports_option_1_bg);
        }
        else if (position==1)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sport_option_2_bg);
        }
       else if (position==2)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sport_option_3_bg);
        }
       else if (position==3)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sport_option_4_bg);
        }
       else if (position==4)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sport_option_5_bg);
        }
       else if (position==5)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sport_option_6_bg);
        }
       else if (position==6)
        {
            holder.relative.setBackgroundResource(R.drawable.favorite_sport_option_7_bg);
        }



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        ImageView sportIV;
        RegularTextView sportNameTV;
        RelativeLayout relative;

        RelativeLayout layoutTop;
        ItemClicKListener mListener;

        ViewHolder(View view, ItemClicKListener listener) {
            super(view);
            image = view.findViewById(R.id.imageIV);
            sportIV = view.findViewById(R.id.sportIV);
            sportNameTV = view.findViewById(R.id.sportNameTV);
            relative = view.findViewById(R.id.relative);

            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemListener(v, getAdapterPosition());
        }

        interface ItemClicKListener {
            void onItemListener(View view, int position);
        }
    }

    public interface AdapterCallBack {
        void itemClickListener(SportModelRoom model, Boolean type, int pos);
    }

    public static String substringAfter(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        final int pos = str.indexOf(separator);
        if (pos == 0) {
            return str;
        }
        return str.substring(pos + separator.length());
    }

}
