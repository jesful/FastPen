package hcmute.edu.vn.fastpen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.fastpen.Model.ThuongHieu;
import hcmute.edu.vn.fastpen.R;

public class ThuongHieuTimKiemAdapter extends BaseAdapter
{
    private final Context context;
    private final int layout;
    private final ArrayList<ThuongHieu> arr_ThuongHieu;

    public ThuongHieuTimKiemAdapter(Context context, int layout, ArrayList<ThuongHieu> arr_ThuongHieu)
    {
        this.context = context;
        this.layout = layout;
        this.arr_ThuongHieu = arr_ThuongHieu;
    }

    @Override
    public int getCount()
    {
        return arr_ThuongHieu.size();
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

    private static class ViewHolder
    {
        TextView txtView_Ten;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ThuongHieuTimKiemAdapter.ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtView_Ten = view.findViewById(R.id.txtView_Ten);
            view.setTag(holder);
        }
        else
        {
            holder = (ThuongHieuTimKiemAdapter.ViewHolder) view.getTag();
        }

        final ThuongHieu th = arr_ThuongHieu.get(i);
        holder.txtView_Ten.setText(String.valueOf(th.getTenThuongHieu()));

        return view;
    }
}