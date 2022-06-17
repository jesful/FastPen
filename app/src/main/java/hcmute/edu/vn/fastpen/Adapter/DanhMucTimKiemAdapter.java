package hcmute.edu.vn.fastpen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.DanhMuc;
import hcmute.edu.vn.fastpen.R;

public class DanhMucTimKiemAdapter extends BaseAdapter
{
    private Context context;
    private int layout;
    private ArrayList<DanhMuc> arr_DanhMuc;

    public DanhMucTimKiemAdapter(Context context, int layout, ArrayList<DanhMuc> arr_DanhMuc)
    {
        this.context = context;
        this.layout = layout;
        this.arr_DanhMuc = arr_DanhMuc;
    }

    @Override
    public int getCount()
    {
        return arr_DanhMuc.size();
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
        TextView txtView_Ten;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        DanhMucTimKiemAdapter.ViewHolder holder;
        if (view == null)
        {
            holder = new DanhMucTimKiemAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtView_Ten = view.findViewById(R.id.txtView_Ten);
            view.setTag(holder);
        }
        else
        {
            holder = (DanhMucTimKiemAdapter.ViewHolder) view.getTag();
        }

        final DanhMuc dm = arr_DanhMuc.get(i);
        holder.txtView_Ten.setText(String.valueOf(dm.getTenDanhMuc()));

        return view;
    }
}