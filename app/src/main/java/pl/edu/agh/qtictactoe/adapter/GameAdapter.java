package pl.edu.agh.qtictactoe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.qtictactoe.R;
import pl.edu.agh.qtictactoe.callback.SquareClickInterface;
import pl.edu.agh.qtictactoe.model.GameSquare;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<GameSquare> dataset;
    private SquareClickInterface clickInterface;

    public GameAdapter(List<GameSquare> myDataset, SquareClickInterface squareClickInterface) {
        this.dataset = myDataset;
        this.clickInterface = squareClickInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public List<TextView> textViews = new ArrayList<>();
        public WeakReference<SquareClickInterface> interfaceRef;

        public ViewHolder(View v, SquareClickInterface squareClickInterface) {
            super(v);
            textViews.add(v.findViewById(R.id.textView1));
            textViews.add(v.findViewById(R.id.textView2));
            textViews.add(v.findViewById(R.id.textView3));
            textViews.add(v.findViewById(R.id.textView4));
            textViews.add(v.findViewById(R.id.textView5));
            textViews.add(v.findViewById(R.id.textView6));
            textViews.add(v.findViewById(R.id.textView7));
            textViews.add(v.findViewById(R.id.textView8));
            textViews.add(v.findViewById(R.id.textView9));
            interfaceRef = new WeakReference<>(squareClickInterface);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (interfaceRef != null)
                interfaceRef.get().onSquareClicked(getAdapterPosition());
        }
    }


    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_square, parent, false);

        ViewHolder vh = new ViewHolder(v, clickInterface);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String strFormat;
        List<Integer> dataSet = dataset.get(position).getDataset();
        for (int i = 0; i < 9; i++) {
            TextView textView = holder.textViews.get(i);
            int value = dataSet.get(i);
            if (value == 0) {
                textView.setVisibility(View.INVISIBLE);
                continue;
            }
            textView.setVisibility(View.VISIBLE);
            if (value % 2 == 0) {
                strFormat = "O%d";
//            holder.textView.setTextColor();
                textView.setText(String.format(strFormat, value));
            } else {
                strFormat = "X%d";
                //            holder.textView.setTextColor();
                textView.setText(String.format(strFormat, value));
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
