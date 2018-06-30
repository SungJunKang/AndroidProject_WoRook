package sungjunkang.worook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


class CardFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<WordList_Item> itemList;

    public CardFragmentPagerAdapter(FragmentManager manager, ArrayList<WordList_Item> arrayList) {
        super(manager);
        this.itemList = arrayList;
        for (int i = 0; i < itemList.size(); i++) {
            addFragment(CardFragment.getInstance(i, itemList.get(i)));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}