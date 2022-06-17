package hcmute.edu.vn.fastpen.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// The adapter class which
// extends RecyclerView Adapter
public class SanPhamChiTietSanPhamAdapter extends RecyclerView.Adapter<SanPhamChiTietSanPhamAdapter.MyView>
{
    // List with String type
    private ArrayList<SanPham> arr_SanPham;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder
    {
        private ImageView imgView_HinhAnhSanPham;
        private TextView txtView_TenSanPham, txtView_SLDaBan, txtView_GiaTien;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            imgView_HinhAnhSanPham = view.findViewById(R.id.imgView_HinhAnhSanPham);
            txtView_TenSanPham = view.findViewById(R.id.txtView_TenSanPham);
            txtView_SLDaBan = view.findViewById(R.id.txtView_SLDaBan);
            txtView_GiaTien = view.findViewById(R.id.txtView_GiaTien);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public SanPhamChiTietSanPhamAdapter(ArrayList<SanPham> arr_SanPham)
    {
        this.arr_SanPham = arr_SanPham;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sanpham_chitietsanpham, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position)
    {
        // Set the text of each item of
        // Recycler view with the list items
        holder.txtView_TenSanPham.setText(arr_SanPham.get(position).getTenSanPham());
        holder.txtView_SLDaBan.setText(String.valueOf(arr_SanPham.get(position).getSoLuongDaBan()));
        holder.txtView_GiaTien.setText(String.valueOf(arr_SanPham.get(position).getGia()));
    }

    @Override
    public int getItemCount()
    {
        return arr_SanPham.size();
    }
}