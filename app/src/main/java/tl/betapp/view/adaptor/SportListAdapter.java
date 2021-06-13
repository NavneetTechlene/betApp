package tl.betapp.view.adaptor;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


import tl.betapp.R;
import tl.betapp.view.model.SportModel;
import tl.betapp.view.utils.Utility;
import tl.betapp.view.widgets.RegularTextView;

import static android.text.TextUtils.isEmpty;

/**
 * Created by jitendra on 06/02/2021.
 */
public class SportListAdapter extends RecyclerView.Adapter<SportListAdapter.ViewHolder> {

    private ArrayList<SportModel> mList = new ArrayList<>();
    private Activity mActivity;
    private AdapterCallBack mCallBack;
    private Utility utility;




    public SportListAdapter(Activity activity, AdapterCallBack callBack) {
        this.mActivity = activity;
        this.mCallBack = callBack;
        this.utility = new Utility();
    }

    public void setList(ArrayList<SportModel> list ) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_select_favorite, parent, false);

        return new ViewHolder(itemView, new ViewHolder.ItemClicKListener() {
            @Override
            public void onItemListener(View view, int position) {
                mCallBack.itemClickListener(mList.get(position), false,position);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SportModel model = mList.get(position);
        model.setStatus(false);
        holder.nameTV.setText(""+model.getSportName());
        holder.image.setBackgroundResource(model.getImage());

        final ImageView imageCheck=holder.itemView.findViewById(R.id.imageCheck);

        imageCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(model.getStatus()))
                {
                    model.setStatus(true);
                    mList.get(position).setStatus(true);

                    model.setType("click");
                    imageCheck.setBackgroundResource(R.drawable.icon_sports_list_checked);
                    mCallBack.itemClickListener(mList.get(position),true,position);

                }
                else {
                    model.setStatus(false);
                    mList.get(position).setStatus(false);
                    imageCheck.setBackgroundResource(R.drawable.icon_sports_list_unchecked);
                    mCallBack.itemClickListener(mList.get(position),false,0);

                }

                Log.i("TAG", "onBindViewHolder: "+mList.get(position).getStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       ImageView image;
       RegularTextView nameTV;


        RelativeLayout layoutTop;
        ItemClicKListener mListener;
        ViewHolder(View view, ItemClicKListener listener) {
            super(view);
              image=view.findViewById(R.id.imageIV);
              nameTV=view.findViewById(R.id.nameTV);

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
        void itemClickListener(SportModel model, Boolean type,int pos);
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
