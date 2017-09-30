package com.langyuye.library.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.langyuye.library.R;
import com.langyuye.library.dialog.app.listener.OnItemClickListener;
import com.langyuye.library.dialog.view.RippleTextView;

import java.util.List;

public class ListViewClickAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> data;
	private OnItemClickListener listener;
	private float textSize;
	public ListViewClickAdapter(Context context, List<String> data,float textSize) {
		mContext = context;
		this.data = data;
		this.textSize=textSize;
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.single_click_item, parent, false);
		RippleTextView textView = (RippleTextView) view.findViewById(R.id.single_click_item_text);
		textView.setText(data.get(position));
		textView.setTextSize(textSize);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (listener!=null){
					listener.onItemClick(view,position);
				}
			}
		});
		return view;
	}
	public void setOnItemClickListener(OnItemClickListener OnItemClickListener){
		this.listener=OnItemClickListener;
	}
}
