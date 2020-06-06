package com.example.ochronasuchu;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
//Obsługuje widget Recyclerview
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<ItemHearingProtector> mRecList;
    private Context mContext;
    private Dialog mDialog;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private ImageView mImageView;
        private TextView mTextView4;
        private TextView mTextView5;
        private TextView mTextView6;

        private RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_view);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView4 = itemView.findViewById(R.id.textView4);
            mTextView5 = itemView.findViewById(R.id.textView5);
            mTextView6 = itemView.findViewById(R.id.textView6);

        }
    }

    public RecyclerAdapter(Context myContext,ArrayList<ItemHearingProtector> recList){
        mContext = myContext;
        mRecList = recList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        final RecyclerViewHolder rvh = new RecyclerViewHolder(V);
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_item);
        rvh.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemHearingProtector clickedItem = mRecList.get(rvh.getAdapterPosition());
                writeDialog(clickedItem);
                mDialog.show();
            }
        });
        return rvh;
    }

    private void writeDialog(final ItemHearingProtector clickedItem){
        TextView dialog_text_1 = mDialog.findViewById(R.id.textView7);
        TextView dialog_text_2 = mDialog.findViewById(R.id.textView8);
        ImageView dialog_image_1 = mDialog.findViewById(R.id.imageView2);
        GraphView dialog_graph = mDialog.findViewById(R.id.graph);
        TextView dialog_text_3 = mDialog.findViewById(R.id.textView9);
        TextView dialog_text_4 = mDialog.findViewById(R.id.textView10);
        TextView dialog_text_5 = mDialog.findViewById(R.id.textView11);
        TextView dialog_text_6 = mDialog.findViewById(R.id.textView12);
        Button dialog_button_delete = mDialog.findViewById(R.id.delete_button);
        //set obrazek i nazwa
        if (clickedItem.getTyp().equals("N")) {
            dialog_image_1.setImageResource(R.drawable.ic_headset_white_24dp);
        } else {
            dialog_image_1.setImageResource(R.drawable.ic_hearing_white_24dp);
        }
        dialog_text_1.setText(clickedItem.getProd());
        dialog_text_2.setText(clickedItem.getModel());
        //graph
        dialog_graph.removeAllSeries();
        dialog_graph.setTitle("Charakterystyka Tłumienia");
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(125, Float.parseFloat(clickedItem.getAPV125())),
                new DataPoint(250, Float.parseFloat(clickedItem.getAPV250())),
                new DataPoint(500, Float.parseFloat(clickedItem.getAPV500())),
                new DataPoint(1000, Float.parseFloat(clickedItem.getAPV1000())),
                new DataPoint(2000, Float.parseFloat(clickedItem.getAPV2000())),
                new DataPoint(4000, Float.parseFloat(clickedItem.getAPV4000())),
                new DataPoint(8000, Float.parseFloat(clickedItem.getAPV8000()))
        });
        series.setColor(Color.RED);
        series.setTitle("APV");
        dialog_graph.addSeries(series);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(125, Float.parseFloat(clickedItem.getMf125())),
                new DataPoint(250, Float.parseFloat(clickedItem.getMf250())),
                new DataPoint(500, Float.parseFloat(clickedItem.getMf500())),
                new DataPoint(1000, Float.parseFloat(clickedItem.getMf1000())),
                new DataPoint(2000, Float.parseFloat(clickedItem.getMf2000())),
                new DataPoint(4000, Float.parseFloat(clickedItem.getMf4000())),
                new DataPoint(8000, Float.parseFloat(clickedItem.getMf8000()))
        });
        series2.setColor(Color.BLUE);
        series2.setTitle("Mf");
        dialog_graph.addSeries(series2);
        dialog_graph.getLegendRenderer().setVisible(true);

        dialog_text_3.setText("L: "+clickedItem.getL());
        dialog_text_4.setText("M: "+clickedItem.getM());
        dialog_text_5.setText("H: "+clickedItem.getH());
        dialog_text_6.setText("SNR: "+clickedItem.getSNR());
        //Możliwość usunięcia z bd ochronnika użytkownika
        if (clickedItem.getCerty().equals("user")){
            dialog_button_delete.setText("Usuń");
        } else {
            dialog_button_delete.setText("Ukryj");
        }
        //Usuń kliknięty
        dialog_button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(clickedItem.getTyp());
                list.add(clickedItem.getProd());
                list.add(clickedItem.getModel());
                list.add(clickedItem.getMf125());
                list.add(clickedItem.getMf250());
                list.add(clickedItem.getMf500());
                list.add(clickedItem.getMf1000());
                list.add(clickedItem.getMf2000());
                list.add(clickedItem.getMf4000());
                list.add(clickedItem.getMf8000());
                list.add(clickedItem.getAPV125());
                list.add(clickedItem.getAPV250());
                list.add(clickedItem.getAPV500());
                list.add(clickedItem.getAPV1000());
                list.add(clickedItem.getAPV2000());
                list.add(clickedItem.getAPV4000());
                list.add(clickedItem.getAPV8000());
                list.add(clickedItem.getH());
                list.add(clickedItem.getM());
                list.add(clickedItem.getL());
                list.add(clickedItem.getSNR());
                list.add(clickedItem.getCerty());
                ((MainActivity)mContext).deleteFromDatabase(list);
                ((MainActivity)mContext).writeRecordsUser();
                ((MainActivity)mContext).writeRecords();
                ((MainActivity)mContext).refreshRecycler();
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ItemHearingProtector currentItem = mRecList.get(position);
        holder.mTextView4.setText(currentItem.getProd());
        holder.mTextView5.setText(currentItem.getModel());
        holder.mTextView6.setText("L: "+currentItem.getL()+" M: "+currentItem.getM()+" H: "+currentItem.getH());
        if (currentItem.getTyp().equals("N")){
            if (currentItem.getCerty().equals("user"))
                holder.mImageView.setImageResource(R.drawable.ic_headset_gold_24dp);
            else
                holder.mImageView.setImageResource(R.drawable.ic_headset_black_24dp);
        } else
            if (currentItem.getCerty().equals("user"))
                holder.mImageView.setImageResource(R.drawable.ic_hearing_gold_24dp);
            else
            holder.mImageView.setImageResource(R.drawable.ic_hearing_black_24dp);


    }
    @Override
    public int getItemCount() {
        return mRecList.size();
    }
}
