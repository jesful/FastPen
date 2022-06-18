package hcmute.edu.vn.fastpen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.fastpen.Model.HoaDon;
import hcmute.edu.vn.fastpen.R;

public class HoaDonAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<HoaDon> hoaDonList;

    public HoaDonAdapter(Context context, int layout, List<HoaDon> hoaDonList) {
        this.context=context;
        this.layout=layout;
        this.hoaDonList=hoaDonList;
    }

    @Override
    public int getCount() {
        return hoaDonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder
    {
        TextView textViewMaHoaDon,textViewNgayDatHang,textViewPhiVanChuyen,textViewTongTien,textViewTrangThai,textViewTienMat;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null)
        {
            // Ánh xạ với các biến trong layout
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.textViewMaHoaDon = (TextView) view.findViewById(R.id.textView_MaHoaDon_HoaDon);
            holder.textViewNgayDatHang = (TextView) view.findViewById(R.id.textView_NgayDatHang_HoaDon);
            holder.textViewPhiVanChuyen = (TextView) view.findViewById(R.id.textView_PhiVanChuyen_HoaDon);
            holder.textViewTongTien = (TextView) view.findViewById(R.id.textView_TongTien_HoaDon);
            holder.textViewTrangThai = (TextView) view.findViewById(R.id.textView_TranThai_HoaDon);
            holder.textViewTienMat = (TextView) view.findViewById(R.id.textView_TienMat_HoaDon);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        // Lấy hóa đơn thứ i
        final HoaDon hoaDon = hoaDonList.get(i);

        // lấy các giá trị của hóa đơn
        String id =String.valueOf(hoaDon.getIdHoaDon());
        String ngay = String.valueOf(hoaDon.getNgay());
        String phi = String.valueOf(hoaDon.getPhiVanChuyen());
        String tong = String.valueOf(hoaDon.getTongTien());
        Integer tt = hoaDon.getTrangThai();
        String trangthai;
        // Kiểm tra trạng thái
        switch (tt) {
            case 0:
                trangthai = "Chờ xác nhận";
                break;
            case 1:
                trangthai = "Chờ xác nhận";
                break;
            case 2:
                trangthai = "Đang vận chuyển";
                break;
            case 3:
                trangthai = "Giao hàng thành công";
                break;
            case 4:
                trangthai = "Đã hủy";
                break;

            default:
                trangthai = "Chờ xác nhận";
                break;
        }
        // Kiểm tra hình thức thanh toán
        boolean tm = hoaDon.getTienMat();
        String tienmat = "Thanh toán online";
        if(tm== true)
        {
            tienmat= "Thanh toán khi nhận hàng";
        }
        holder.textViewMaHoaDon.setText("Mã: "+id);
        holder.textViewNgayDatHang.setText("Ngày: "+ngay);
        holder.textViewPhiVanChuyen.setText("Phí vận chuyển: "+phi);
        holder.textViewTongTien.setText("Tổng tiền: "+tong);
        holder.textViewTienMat.setText(tienmat);
        holder.textViewTrangThai.setText(trangthai);
        return view;
    }
}
