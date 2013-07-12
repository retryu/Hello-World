package com.example.toshiba.adapter;

import java.util.List;

import com.example.toshiba.ui.HairStyleItem;

import android.R;
import android.content.Context;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-12 下午01:10:24 file declare:
 */
public class HairStyleAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private Context context;
	private List<HairStyleItem> hariStyles;

	public HairStyleAdapter(Context context) {
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		if (hariStyles != null) {
			return hariStyles.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HairStyleItem hiaHairStyleItem = hariStyles.get(position);
		if (convertView == null) {
			convertView = (View) layoutInflater.inflate(
					com.example.toshiba.R.layout.layout_hair_style, null);

		}
		bindView(convertView, hiaHairStyleItem);

		return convertView;
	}

	public void bindView(View view, HairStyleItem hairStyleItem) {
		ImageView img = (ImageView) view
				.findViewById(com.example.toshiba.R.id.Img_HariStyle);
		if (hairStyleItem.getImgRes() != null) {
			img.setBackgroundResource(hairStyleItem.getImgRes());
		}
		
		
		
	}

	public List<HairStyleItem> getHariStyles() {
		return hariStyles;
	}

	public void setHariStyles(List<HairStyleItem> hariStyles) {
		this.hariStyles = hariStyles;
	}

}
