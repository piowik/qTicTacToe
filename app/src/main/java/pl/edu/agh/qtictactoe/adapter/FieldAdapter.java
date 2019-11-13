package pl.edu.agh.qtictactoe.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.agh.qtictactoe.R;

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.ViewHolder> {
    private List<Integer> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
        }
    }

    public FieldAdapter(List<Integer> myDataset) {
        dataset = myDataset;
    }

    @Override
    public FieldAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String strFormat;
        Log.e("ab", "onBindViewHolder: " + dataset.get(position));
        if (dataset.get(position) == 0)
            holder.textView.setVisibility(View.INVISIBLE);
        else if (dataset.get(position)%2 == 0) {
            strFormat = "O%d";
//            holder.textView.setTextColor();
            holder.textView.setText(String.format(strFormat, dataset.get(position)));
        }
        else {
            strFormat = "X%d";
            //            holder.textView.setTextColor();
            holder.textView.setText(String.format(strFormat, dataset.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
