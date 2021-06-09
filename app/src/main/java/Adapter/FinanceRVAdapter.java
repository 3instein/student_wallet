package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uc.studentwallet.FinanceFragment;
import com.uc.studentwallet.MainActivity;
import com.uc.studentwallet.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Finance;

import static com.uc.studentwallet.HomeFragment.formatter;

public class FinanceRVAdapter extends RecyclerView.Adapter<FinanceRVAdapter.FinanceViewHolder> {

    private static ArrayList<Finance> financeData;

    public FinanceRVAdapter(ArrayList<Finance> financeData) {
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
        holder.finance_id.setText(String.valueOf(financeData.get(position).getFinance_id()));

        holder.finance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.finance_status.getText().equals("Unpaid")) {
                    payFinance(Integer.parseInt((String) holder.finance_id.getText()), v);
                    holder.finance_btn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return financeData.size();
    }

    public class FinanceViewHolder extends RecyclerView.ViewHolder {

        private TextView finance_id, finance_status, finance_amount;
        private Button finance_btn;

        public FinanceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            finance_id = itemView.findViewById(R.id.finance_id);
            finance_status = itemView.findViewById(R.id.finance_status);
            finance_amount = itemView.findViewById(R.id.finance_amount);
            finance_btn = itemView.findViewById(R.id.finance_btn);
        }
    }

    public void payFinance(int id, View v){
        String url = "https://student.hackerexperience.net/pay_finance.php";
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(v.getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                } else if(response.equalsIgnoreCase("balance")){
                    Toast.makeText(v.getContext(), "Not enough balance", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(v.getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("pay", String.valueOf(id));

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}

