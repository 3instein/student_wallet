package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.studentwallet.HomeFragment;
import com.uc.studentwallet.R;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;

import model.History;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder> {

    private static ArrayList<History> historyData;

    public HistoryRVAdapter(ArrayList<History> historyData) {
        this.historyData = historyData;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_card, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryViewHolder holder, int position) {
        holder.historyCard_status.setText(historyData.get(position).getType());
        try {
            holder.historyCard_date.setText(HomeFragment.dateSplitter(historyData.get(position).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.historyCard_transaction.setText(String.valueOf(HomeFragment.formatter((double) historyData.get(position).getAmount())));
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView historyCard_name, historyCard_nim, historyCard_status, historyCard_transaction, historyCard_date;

        public HistoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            historyCard_name = itemView.findViewById(R.id.historyCard_name);
            historyCard_nim = itemView.findViewById(R.id.historyCard_nim);
            historyCard_status = itemView.findViewById(R.id.historyCard_status);
            historyCard_transaction = itemView.findViewById(R.id.historyCard_transaction);
            historyCard_date = itemView.findViewById(R.id.historyCard_date);
        }
    }
}
