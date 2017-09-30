package com.langyuye.library.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.langyuye.library.R;
import com.langyuye.library.dialog.app.listener.OnItemClickListener;
import com.langyuye.library.dialog.view.CheckableLinearLayout;
import com.langyuye.library.dialog.view.RadioButton;
import com.langyuye.library.dialog.view.RippleTextView;

import java.util.List;

public class ListViewChoiceAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> data;
	private OnItemClickListener listener;
	private boolean isSelectColor;
	private float textSixe;
	private int iconSize;
	private int colorAccent;
	public ListViewChoiceAdapter(Context context, List<String> data,int colorAccent,int iconSize,float textSize) {
		this.mContext = context;
		this.data = data;
		this.colorAccent=colorAccent;
		this.isSelectColor=isSelectColor;
		this.textSixe=textSize;
		this.iconSize=iconSize;
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
		View view = LayoutInflater.from(mContext).inflate(R.layout.single_choice_items, parent, false);
		final CheckableLinearLayout layout=(CheckableLinearLayout) view.findViewById(R.id.single_choice_item);
		final RippleTextView textView=(RippleTextView) view.findViewById(R.id.title);
		final RadioButton itemView = (RadioButton) view.findViewById(R.id.single_choice_item_checkbox);
		itemView.setCheckColor(colorAccent);
		itemView.setSize(iconSize);
		textView.setTextSize(textSixe);
		textView.setText((String)getItem(position));
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