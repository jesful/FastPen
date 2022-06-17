package hcmute.edu.vn.fastpen.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.BinhLuan;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// The adapter class which
// extends RecyclerView Adapter
public class BinhLuanChiTietSanPhamAdapter extends RecyclerView.Adapter<BinhLuanChiTietSanPhamAdapter.MyView>
{
    // List with String type
    private ArrayList<BinhLuan> arr_BinhLuan;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder
    {
        private ImageView imgView_HinhAnhTaiKhoan_BinhLuan;
        private TextView txtView_Ten_BinhLuan, txtView_BaiViet_BinhLuan, txtView_ThoiGianDangBinhLuan_BinhLuan;
        private RatingBar ratingBar_SoSaoSanPham_BinhLuan;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            imgView_HinhAnhTaiKhoan_BinhLuan = view.findViewById(R.id.imgView_HinhAnhTaiKhoan_BinhLuan);
            txtView_Ten_BinhLuan = view.findViewById(R.id.txtView_Ten_BinhLuan);
            txtView_BaiViet_BinhLuan = view.findViewById(R.id.txtView_BaiViet_BinhLuan);
            txtView_ThoiGianDangBinhLuan_BinhLuan = view.findViewById(R.id.txtView_ThoiGianDangBinhLuan_BinhLuan);
            ratingBar_SoSaoSanPham_BinhLuan = view.findViewById(R.id.ratingBar_SoSaoSanPham_BinhLuan);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public BinhLuanChiTietSanPhamAdapter(ArrayList<BinhLuan> arr_BinhLuan)
    {
        this.arr_BinhLuan = arr_BinhLuan;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_binhluan_chitietsanpham, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position)
    {
        // Set the text of each item of
        // Recycler view with the list items
        //holder.txtView_Ten_BinhLuan.setText(arr_BinhLuan.get(position).getTenSanPham());
        holder.txtView_BaiViet_BinhLuan.setText(String.valueOf(arr_BinhLuan.get(position).getBaiViet()));
        holder.txtView_ThoiGianDangBinhLuan_BinhLuan.setText(arr_BinhLuan.get(position).getThoiGianDangBinhLuan());
        holder.ratingBar_SoSaoSanPham_BinhLuan.setRating(arr_BinhLuan.get(position).getSoSao());
    }

    @Override
    public int getItemCount()
    {
        return arr_BinhLuan.size();
    }
}