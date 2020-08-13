package com.example.contentprovider;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.Holder> implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList<Contacts> NNarraylist;

    public AdapterRecycle(Context context, ArrayList itemArrayList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.NNarraylist = itemArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.design, parent, false);
        Holder h = new Holder(v, context);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Contacts ob = (Contacts) NNarraylist.get(position);
        holder.tv1.setText(ob.getName());
        holder.tv2.setText(ob.getNumber());
    }

    @Override
    public int getItemCount() {
        return NNarraylist.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contacts> FilterList = new ArrayList<>();
            if (constraint == null && constraint.length() == 0) {
                FilterList.addAll(NNarraylist);

            } else {
                String filterpatern = constraint.toString().toLowerCase().trim();
                for (Contacts contacts : NNarraylist) {

                    if (contacts.getName().toLowerCase().contains(filterpatern)) {

                        FilterList.add(contacts);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = FilterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            NNarraylist.clear();
            NNarraylist.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    static class Holder extends RecyclerView.ViewHolder {

        TextView tv1, tv2;
        Context context;

        public Holder(View itemView, final Context context) {
            super(itemView);
            this.context = context;
            tv1 = (TextView) itemView.findViewById(R.id.name);
            tv2 = (TextView) itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, tv1 + "", Toast.LENGTH_SHORT).show();


                }
            });


        }


    }

//    public void removeItem(int position) {
//        NNarraylist.remove(position);
//        // notify the item removed by position
//        // to perform recycler view delete animations
//        // NOTE: don't call notifyDataSetChanged()
//        notifyItemRemoved(position);
//    }
//
//    public void restoreItem(Contacts nn, int position) {
//        NNarraylist.add(position, nn);
//        // notify item added by position
//        notifyItemInserted(position);
//    }

}
