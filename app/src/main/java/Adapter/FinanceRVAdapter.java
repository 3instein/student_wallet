package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.studentwallet.MainActivity;
import com.uc.studentwallet.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import model.FInance;

import static com.uc.studentwallet.HomeFragment.formatter;

public class FinanceRVAdapter extends RecyclerView.Adapter<FinanceRVAdapter.FinanceViewHolder> {

    private static ArrayList<FInance> financeData;

    public FinanceRVAdapter(ArrayList<FInance> financeData) {
        this.financeData = financeData;
    }

    @Override
    public FinanceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.finance_card, parent, false);
        return new FinanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FinanceViewHolder holder, int position) {
        holder.finance_status.setText(financeData.get(position).getStatus());
        holder.finance_amount.setText(String.valueOf(formatter((double) financeData.get(position).getAmount())));
    }

    @Override
    public int getItemCount() {
        return financeData.size();
    }

    public class FinanceViewHolder extends RecyclerView.ViewHolder {

        private TextView finance_status, finance_amount;
        private Button finance_btn;

        public FinanceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            finance_status = itemView.findViewById(R.id.finance_status);
            finance_amount = itemView.findViewById(R.id.finance_amount);
            finance_btn = itemView.findViewById(R.id.finance_btn);
        }
    }
}
