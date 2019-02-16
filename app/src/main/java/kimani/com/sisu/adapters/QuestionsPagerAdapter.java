package kimani.com.sisu.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuestionsPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> pages = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public QuestionsPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public void addFragment(Fragment f,String t){
        pages.add(f);titles.add(t);
    }

    @Override
    public Fragment getItem(int i) {
        return pages.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.pages = new ArrayList<>();
        this.titles = new ArrayList<>();
    }
}
