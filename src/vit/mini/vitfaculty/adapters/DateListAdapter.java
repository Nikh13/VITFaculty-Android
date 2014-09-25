package vit.mini.vitfaculty.adapters;

import java.util.List;

import vit.mini.vitfaculty.R;
import vit.mini.vitfaculty.data.Date;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DateListAdapter extends BaseAdapter {
	
	Context context;
	List<Date> date_list;
	
	public DateListAdapter(Context context, List<Date> date_list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.date_list= date_list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return date_list.size()+2;
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
		// TODO Auto-generated method stub
		LayoutInflater inflater  = LayoutInflater.from(context);
		View item = inflater.inflate(R.layout.date_list_item, null);
		View header = inflater.inflate(R.layout.date_list_header, null);
		header.setClickable(false);
		header.setEnabled(false);
		
		if(position == 0){
			TextView header_text = (TextView)header.findViewById(R.id.header);
			header_text.setText("Most Recent Class");
			return header;
		}
		
		else if(position == 1){
			TextView date  = (TextView)item.findViewById(R.id.date);
			TextView status = (TextView)item.findViewById(R.id.post_status);
			date.setText(date_list.get(0).getDate());
			if(date_list.get(0).getPost_status()){
				status.setText("Posted");
				status.setTextColor(context.getResources().getColor(
						android.R.color.holo_green_dark));
			}
			else{
				status.setText("Pending");
				status.setTextColor(context.getResources().getColor(
						android.R.color.holo_red_dark));
			}
			return item;
		}
		
		else if(position == 2){
			TextView header_text = (TextView)header.findViewById(R.id.header);
			header_text.setText("Past Classes");
			return header;
		}
		
		else{
			TextView date  = (TextView)item.findViewById(R.id.date);
			TextView status = (TextView)item.findViewById(R.id.post_status);
			date.setText(date_list.get(position-2).getDate());
			if(date_list.get(position-2).getPost_status()){
				status.setText("Posted");
				status.setTextColor(context.getResources().getColor(
						android.R.color.holo_green_dark));
			}
			else{
				status.setText("Pending");
				status.setTextColor(context.getResources().getColor(
						android.R.color.holo_red_dark));
			}
			return item;
		}
	}

}
