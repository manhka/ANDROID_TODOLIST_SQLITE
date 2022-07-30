package manhnguyen.todolist.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private  MainActivity context;
    private int layout;
    private List<CongViec> congViecs;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecs) {
        this.context = context;
        this.layout = layout;
        this.congViecs = congViecs;
    }

    @Override
    public int getCount() {
        return congViecs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView ten;
        ImageView edit,delete;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        if (view==null){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(layout,null);
            viewHolder.ten=(TextView) view.findViewById(R.id.tenCV);
            viewHolder.edit=(ImageView) view.findViewById(R.id.edit);
            viewHolder.delete=(ImageView) view.findViewById(R.id.delete);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
//gan gia tri
        CongViec congViec=congViecs.get(i);
        viewHolder.ten.setText(congViec.getTenCV());
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSuaCv(congViec.getTenCV(),congViec.getId());
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoaCV(congViec.getTenCV(),congViec.getId());
            }
        });

        return view;
    }
}
