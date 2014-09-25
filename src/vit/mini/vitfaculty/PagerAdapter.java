package vit.mini.vitfaculty;

import vit.mini.vitfaculty.fragments.CoursesPage;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

	public PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		if(pos == 0)
		{
			return new CoursesPage();
		}	
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
