package hcmute.edu.vn.fastpen.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Activity.ChiTietSanPhamActivity;
import hcmute.edu.vn.fastpen.Activity.DangNhapActivity;
import hcmute.edu.vn.fastpen.Activity.GioHangActivity;
import hcmute.edu.vn.fastpen.Activity.TimKiemActivity;
import hcmute.edu.vn.fastpen.Adapter.SanPhamTrangChuAdapter;
import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

public class TrangChuFragment extends Fragment
{
    // Array List để lưu trữ dữ liệu dùng để adapt lên recycler view
    private ArrayList<SanPham> arr_SanPham;
    // Recycler View
    private RecyclerView recyclerView_SanPham_TrangChu;
    // Adapter để adapt dữ liệu từ array lên recycler view
    private SanPhamTrangChuAdapter sanPhamTrangChuAdapter;

    public TrangChuFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout cho fragment
        View view =  inflater.inflate(R.layout.fragment_trang_chu, container, false);

        // Click vào linear layout khung tìm kiếm để chuyển sang trang tìm kiếm và lọc sản phẩm
        LinearLayout linearLayout_TimKiem_TrangChu = view.findViewById(R.id.linearLayout_TimKiem_TrangChu);
        linearLayout_TimKiem_TrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimKiemActivity.class);
                startActivity(intent);
            }
        });

        // Click vào hình icon giỏ hàng để chuyển sang trang giỏ hàng
        ImageView imgView_GioHang_TrangChu = view.findViewById(R.id.imgView_GioHang_TrangChu);
        imgView_GioHang_TrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Nếu chưa đăng nhập thì tài khoản là null sẽ chuyển sang trang đăng nhập
                Intent intent;
                if(Global.getTk() == null)
                {
                    intent = new Intent(getActivity(), DangNhapActivity.class);
                }
                // Nếu đã đăng nhập thì chuyển sang trang giỏ hàng
                else
                {
                    intent = new Intent(getActivity(), GioHangActivity.class);
                }
                startActivity(intent);
            }
        });

        // Array list các sản phẩm để set lên recycler view sản phẩm
        arr_SanPham = new ArrayList<>();
        // Chạy hàm lấy dữ liệu sản phẩm trên firebase và dùng adapter set dữ liệu lên recycler view
        GetDataSanPham(view);

        return view;
    }

    private void GetDataSanPham(View view)
    {
        // Khởi tạo recycler view sản phẩm
        recyclerView_SanPham_TrangChu = view.findViewById(R.id.recyclerView_SanPham_TrangChu);
        // Tạo biến với giá trị là 2
        int numberOfColumns = 2;
        // Set LayoutManager cho Recycler View, layout theo kiểu grid view có 2 cột
        recyclerView_SanPham_TrangChu.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        // Kết nối firebase với đường dẫn SanPham
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("SanPham");
        // Sắp xếp dữ liệu lấy về theo số lượng sản phẩm đã bán
        Query query = dbref.orderByChild("soLuongDaBan");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Lấy dữ liệu của các sản phẩm có cùng danh mục với sản phẩm đang được hiển thị thông tin chi tiết
                // Làm trống mảng để khi dữ liệu trên firebase có sự thay đổi thì add dữ liệu vào mảng sẽ không làm dữ liệu trong mảng bị trùng lặp
                arr_SanPham.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    // Lấy object SanPham từ firebase và thêm vào mảng
                    SanPham sp = dataSnapshot.getValue(SanPham.class);
                    arr_SanPham.add(sp);
                }

                // Đảo arr_SanPham lại và thêm vào arr_SanPhamTemp để có được danh sách các sản phẩm được sắp xếp theo số lượng đã bán từ nhiều đến ít
                ArrayList<SanPham> arr_SanPhamTemp = new ArrayList<>();
                for(int i = arr_SanPham.size() - 1; i >= 0; i--)
                {
                    arr_SanPhamTemp.add(arr_SanPham.get(i));
                }

                // Sau khi đã lấy hết dữ liệu và thêm vào mảng thì dùng adapter để đổ dữ liệu lên recycler view
                sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(arr_SanPhamTemp, new SanPhamTrangChuAdapter.OnItemClickListener()
                {
                    @Override
                    // Khi click vào 1 item trong recycler view
                    public void onItemClick(View view, int i) {
                        // Chuyển qua trang Chi tiết sản phẩm
                        Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
                        // Lưu trữ lại id của sản phẩm bằng putExtra
                        intent.putExtra("id", arr_SanPhamTemp.get(i).getIdSanPham());
                        startActivity(intent);
                    }
                });

                recyclerView_SanPham_TrangChu.setAdapter(sanPhamTrangChuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}