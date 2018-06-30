package sungjunkang.worook;

import android.support.v7.widget.CardView;

public interface CardAdapter {

    public final int MAX_ELEVATION_FACTOR = 10;

    float getBaseElevation();

    CardFragment getCardViewAt(int position);

    int getCount();
}
