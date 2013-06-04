package com.upcnet.upcmobil;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyJSONArrayAdapter extends BaseAdapter implements OnClickListener
{

	private ViewGroup group;
	private JSONArray items;
	private String key;
	private String ID;
	private Context context;
	private String selectedItemID;
	private ArrayList<Boolean> selectedPositions;
	private ArrayList<String> selectedIDs;

	private int selectedItemPosition;

	public MyJSONArrayAdapter(Context ctx, JSONArray array, String k, String ID)
	{
		super();
		this.items = array;
		this.context = ctx;
		this.key = k;
		this.selectedItemPosition = -1;
		this.ID = ID;

		selectedPositions = new ArrayList<Boolean>();
		selectedIDs = new ArrayList<String>();
	}

	public int getCount()
	{
		return items.length();
	}

	public Object getItem(int position)
	{
		return position;
	}

	public long getItemId(int position)
	{
		return position;
	}

	public ArrayList<String> getSelectedIDs() {
		return selectedIDs;
	}	
	public ArrayList<Boolean> getSelectedPositions() {
		return selectedPositions;
	}

	public boolean isSelected(int pos) {
		if(pos >= selectedPositions.size())
			return false;
		return selectedPositions.get(pos);
	}


	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		group = parent;

		if (view == null)
		{
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.map_search_result_item, null);
			//view.setOnTouchListener(this);
			view.setOnClickListener(this);
		}

		String itemText = null;
		String itemID = null;

		try
		{
			JSONObject jsonObject = items.getJSONObject(position);
			itemText = jsonObject.getString(key);
			itemID = jsonObject.getString(ID);
		}
		catch (JSONException e)
		{

		}

		if (itemText != null)
		{
			TextView name = (TextView) view.findViewById(R.id.list_result_title);
			TextView id = (TextView) view.findViewById(R.id.list_result_id);
			CheckBox button = (CheckBox) view.findViewById(R.id.list_result_checkbox);

			if (name != null)
				name.setText(itemText);

			if (id != null)
			{
				id.setText(itemID);
				id.setHint(position + "");
			}
			if (selectedPositions.size() == position) {
				selectedPositions.add(false);
				button.setSelected(false);
				button.setChecked(false);
				Log.v("TEST", "Position (amplio) "+position+ " to false");


			}
			else {
				if (selectedPositions.get(position) == true )
					//			if (id.getText().toString().equals(selectedItemID))
				{
					button.setSelected(true);
					button.setChecked(true);
					Log.v("TEST", "Position "+position+ " to true");

					try {
						Log.v("TESTT","Pinto: "+ items.getJSONObject(position).getString(SearchMaps.WS_NOM));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					button.setSelected(false);
					button.setChecked(false);
					Log.v("TEST", "Position "+position+ " to false");


				}
			}
		}



		return view;
	}



	@Override
	public void onClick(View v) {
		CheckBox button = (CheckBox) v.findViewById(R.id.list_result_checkbox);
		/*
    if (selectedItemPosition != -1)
    {
        View previousView = group.getChildAt(selectedItemPosition);
        if (previousView != null)
        {
            RadioButton previous = (RadioButton) previousView.findViewById(R.id.spinnerRadioButton);
            previous.setSelected(false);
            previous.setChecked(false);
        }
    }
		 */
		/*if (button.isSelected())  {
			button.setSelected(false);
			selectedPositions.add(Integer.parseInt(((TextView) v.findViewById(R.id.list_result_id)).getHint().toString()), false);

		}
		else {
			button.setSelected(true);
			selectedPositions.add(Integer.parseInt(((TextView) v.findViewById(R.id.list_result_id)).getHint().toString()), true);
		}*/
		int pos = Integer.parseInt(((TextView) v.findViewById(R.id.list_result_id)).getHint().toString());
		if (button.isChecked()) {
			button.setChecked(false);

			Log.v("TEST", "Position "+pos+ " to false");
			//selectedPositions.add(pos, false);
			selectedPositions.set(pos, false);
		}
		else{
			button.setChecked(true);
			//selectedPositions.add(pos, true);
			selectedPositions.set(pos, true);

			Log.v("TEST", "Position "+pos+ " to true");
		}
		//	String id_string = ((TextView) v.findViewById(R.id.list_result_id)).getText().toString();
		//Integer id_ing = Integer.parseInt(((TextView) v.findViewById(R.id.list_result_id)).getHint().toString());

		selectedIDs.add(((TextView) v.findViewById(R.id.list_result_id)).getText().toString());
		//selectedItemPosition = Integer.parseInt(((TextView) v.findViewById(R.id.spinnerListItemID)).getHint().toString());



	}
}