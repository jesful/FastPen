package hcmute.edu.vn.fastpen.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Global;
import hcmute.edu.vn.fastpen.Model.GioHang;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

// The adapter class which
// extends RecyclerView Adapter
public class SanPhamThanhToanAdapter extends RecyclerView.Adapter<SanPhamThanhToanAdapter.MyView>
{
    // List with String type
    private ArrayList<GioHang> arr_GioHang;
    // Database Reference
    private DatabaseReference dbref;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder
    {
        private ImageView imgView_HinhAnhSanPham_ThanhToan;
        private TextView txtView_TenSanPham_ThanhToan, txtView_SoLuongSanPham_ThanhToan, txtView_GiaTien_ThanhToan;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            imgView_HinhAnhSanPham_ThanhToan = view.findViewById(R.id.imgView_HinhAnhSanPham_ThanhToan);
            txtView_TenSanPham_ThanhToan = view.findViewById(R.id.txtView_TenSanPham_ThanhToan);
            txtView_SoLuongSanPham_ThanhToan = view.findViewById(R.id.txtView_SoLuongSanPham_ThanhToan);
            txtView_GiaTien_ThanhToan = view.findViewById(R.id.txtView_GiaTien_ThanhToan);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public SanPhamThanhToanAdapter(ArrayList<GioHang> arr_GioHang)
    {
        this.arr_GioHang = arr_GioHang;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sanpham_thanhtoan, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position)
    {
        // Set the text of each item of
        // Recycler view with the list items
        holder.txtView_SoLuongSanPham_ThanhToan.setText(String.valueOf(arr_GioHang.get(position).getSoLuong()));

        int id = arr_GioHang.get(position).getIdSanPham();
        dbref = FirebaseDatabase.getInstance().getReference("SanPham/" + id);
        // Lấy dữ liệu của sản phẩm có idSanPham bằng id được lưu trữ để lấy thông tin của sản phẩm
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                SanPham sp = snapshot.getValue(SanPham.class);

                if (sp != null)
                {
                    //holder.imgView_HinhAnhSanPham_ThanhToan;
                    holder.txtView_TenSanPham_ThanhToan.setText(sp.getTenSanPham());
                    holder.txtView_GiaTien_ThanhToan.setText(String.valueOf(sp.getGia()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return arr_GioHang.size();
    }
}