package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.FriendListBean;
import com.xiaocool.sugarangel.bean.InfoBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.GlideUtils;

import java.util.List;

/**
 * Created by TpOut on 2016/7/22.
 * Email: 416756910@qq.com
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private List<FriendListBean> mDataList;
    private OnRecyclerViewListener onRecyclerViewListener;
    Context context;
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvPinyin;
        public int vhPosition;

        public ViewHolder(View v) {
            super(v);

            tvName = (TextView) v.findViewById(R.id.circle_name);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onItemClick(vhPosition);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onItemLongClick(vhPosition);
            }
            return false;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactRecyclerAdapter(List<FriendListBean> mDataList,Context context) {
        this.mDataList = mDataList;
        this.context = context;
    }

    @Override
    public ContactRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_health_circle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactRecyclerAdapter.ViewHolder holder, int position) {

        holder.vhPosition = holder.getAdapterPosition();

        holder.tvName.setText(mDataList.get(position).getName());
//        GlideUtils.loadRectangImageView(context, NetConstant.SHOW_IMAGE,holder.);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public long getHeaderId(int position) {
        return getItemSortLetter(position).charAt(0);
    }

    public String getItemSortLetter(int position) {
        return mDataList.get(position).getPinyin().substring(0, 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String showValue = getItemSortLetter(position);

        textView.setText(showValue);
    }

    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = mDataList.get(i).getPinyin().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }
}
