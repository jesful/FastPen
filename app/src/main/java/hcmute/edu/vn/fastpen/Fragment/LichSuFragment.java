package hcmute.edu.vn.fastpen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Activity.ChiTietHoaDonActivity;
import hcmute.edu.vn.fastpen.Adapter.HoaDonAdapter;
import hcmute.edu.vn.fastpen.Model.Global;
import hcmute.edu.vn.fastpen.Model.HoaDon;
import hcmute.edu.vn.fastpen.R;


public class LichSuFragment extends Fragment {

    ListView listView_HoaDon;
    ArrayList<HoaDon> array_HoaDon;
    HoaDonAdapter adapter;
    public LichSuFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(Global.getTk()!=null)
        {
            // Ánh xạ với các biến trong layout
            View rootView = inflater.inflate(R.layout.fragment_lich_su, container, false);
            listView_HoaDon = (ListView)rootView.findViewById(R.id.listView_HoaDon_HoaDonFragment) ;
            array_HoaDon = new ArrayList<>();
            adapter = new HoaDonAdapter(getActivity(), R.layout.dong_hoa_don, array_HoaDon);
            listView_HoaDon.setAdapter(adapter);

            //Lấy giá trị hóa đơn từ firebase
            GetDataHoaDon();
            listView_HoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // chuyển tới trang  chi tiết hóa đơn và gửi các giá trị kèm theo
                    Intent intent = new Intent(getActivity(), ChiTietHoaDonActivity.class);
                    intent.putExtra("id", array_HoaDon.get(i).getIdHoaDon());
                    intent.putExtra("ten", array_HoaDon.get(i).getTen());
                    intent.putExtra("diachi", array_HoaDon.get(i).getDiaChi());
                    intent.putExtra("sdt", array_HoaDon.get(i).getSDT());
                    startActivity(intent);
                }
            });
            return rootView;
        }
        else
        {

            return inflater.inflate(R.layout.fragment_lich_su, container, false);
        }
    }
    private void GetDataHoaDon()
    {
        // Select dữ liệu
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("HoaDon");
        array_HoaDon.clear();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    HoaDon sp = dataSnapshot.getValue(HoaDon.class);
                    // Kiểm tra hóa đơn có phải của tài khoản đang đăng nhập không
                    if(sp.getTenTaiKhoan().equals(Global.getTk().getTenTaiKhoan())){
                        // Nếu đúng thêm vào array
                        array_HoaDon.add(sp);
                    }

                   }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}