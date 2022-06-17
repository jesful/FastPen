package hcmute.edu.vn.fastpen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.SanPham;
import hcmute.edu.vn.fastpen.R;

public class SanPhamTrangChuAdapter extends BaseAdapter
{
    private Context context;
    private int layout;
    private ArrayList<SanPham> arr_SanPham;

    public SanPhamTrangChuAdapter(Context context, int layout, ArrayList<SanPham> arr_SanPham)
    {
        this.context = context;
        this.layout = layout;
        this.arr_SanPham = arr_SanPham;
    }

    @Override
    public int getCount()
    {
        return arr_SanPham.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    private class ViewHolder
    {
        ImageView imgView_HinhAnhSanPham;
        TextView txtView_TenSanPham;
        TextView txtView_SLDaBan;
        TextView txtView_GiaTien;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        SanPhamTrangChuAdapter.ViewHolder holder;
        if (view == null)
        {
            holder = new SanPhamTrangChuAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.imgView_HinhAnhSanPham = view.findViewById(R.id.imgView_HinhAnhSanPham);
            holder.txtView_TenSanPham = view.findViewById(R.id.txtView_TenSanPham);
            holder.txtView_SLDaBan = view.findViewById(R.id.txtView_SLDaBan);
            holder.txtView_GiaTien = view.findViewById(R.id.txtView_GiaTien);
            view.setTag(holder);
        }
        else
        {
            holder = (SanPhamTrangChuAdapter.ViewHolder) view.getTag();
        }

        final SanPham sp = arr_SanPham.get(i);
        //holder.imgView_HinhAnhSanPham.setImageResource(nhaHang.getHinhAnh());
        holder.txtView_TenSanPham.setText(sp.getTenSanPham());
        holder.txtView_SLDaBan.setText(String.valueOf(sp.getSoLuongDaBan()));
        holder.txtView_GiaTien.setText(String.valueOf(sp.getGia()));

        return view;
    }
}