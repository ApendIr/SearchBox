package ir.apend.searchbox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.apend.searchbox.R;
import ir.apend.searchbox.helper.DataBaseHelper;
import ir.apend.searchbox.listener.OnTextSearchedListener;
import ir.apend.searchbox.model.HistoryModel;
import ir.apend.searchbox.ui.SearchBox;

/**
 * Created by Fatemeh on 2/25/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private List<HistoryModel> historyModels=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private OnTextSearchedListener onTextSearchedListener;

    public HistoryAdapter(List<HistoryModel> historyModels, Context context, OnTextSearchedListener onTextSearchedListener){

        this.historyModels=historyModels;
        this.context=context;
        this.onTextSearchedListener=onTextSearchedListener;

    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.row_search_box_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder holder, final int position) {
        holder.txthistory.setText(historyModels.get(position).getTextHistory());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper.getInstance(context).deleteHistory(historyModels.get(position).getId());
                historyModels.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTextSearchedListener.onTextSearched(historyModels.get(position).getTextHistory());
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       private ImageView btnDelete;
       private TextView txthistory;
       private RelativeLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);

            btnDelete=(ImageView)itemView.findViewById(R.id.btn_history_clear);
            txthistory=(TextView)itemView.findViewById(R.id.txt_search);
            parent=(RelativeLayout)itemView.findViewById(R.id.parent);
        }
    }
}
