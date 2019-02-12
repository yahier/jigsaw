package yahier.com.jigsaw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by yahier on 16/12/30.
 */

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.ViewHolder> {

    //正确的排版顺序
    private static final Integer[] imgRightRes = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9};

    //操作过程中的图像资源列表
    private List<Integer> listRes;
    //当前空格的索引号 [0-8]
    private int emptyIndex;
    private Context context;
    //拼图是否完成，完成后 不能再移动了
    private boolean isCompleted = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.my_textview);
        }
    }


    public MainRecycleAdapter(Context context) {
        this.context = context;
        init();
    }

    public void init() {
        //方法1
        listRes = new ArrayList<>(imgRightRes.length);
        for (Integer imgRes : imgRightRes) {
            listRes.add(imgRes);
        }
        //方法2 方法2出现破绽，实际上listRes和imgRightRes是同样的引用
        //listRes = Arrays.asList(imgRightRes);

        Collections.shuffle(listRes);
        emptyIndex = new Random().nextInt(imgRightRes.length);
        listRes.set(emptyIndex, 0);
        isCompleted = false;
        notifyDataSetChanged();
    }

    private void checkMove(int clickPosition) {
        if (isCompleted)
            return;
        switch (clickPosition) {
            case 0:
                if (emptyIndex == 1 || emptyIndex == 3) {
                    move(clickPosition);
                }
                break;
            case 1:
                if (emptyIndex == 0 || emptyIndex == 2 || emptyIndex == 4) {
                    move(clickPosition);
                }
                break;
            case 2:
                if (emptyIndex == 1 || emptyIndex == 5) {
                    move(clickPosition);
                }
                break;
            case 3:
                if (emptyIndex == 0 || emptyIndex == 4 || emptyIndex == 6) {
                    move(clickPosition);
                }
                break;

            case 4:
                if (emptyIndex == 1 || emptyIndex == 3 || emptyIndex == 5 || emptyIndex == 7) {
                    move(clickPosition);
                }
                break;
            case 5:
                if (emptyIndex == 2 || emptyIndex == 4 || emptyIndex == 8) {
                    move(clickPosition);
                }
                break;
            case 6:
                if (emptyIndex == 3 || emptyIndex == 7) {
                    move(clickPosition);
                }
                break;
            case 7:
                if (emptyIndex == 4 || emptyIndex == 6 || emptyIndex == 8) {
                    move(clickPosition);
                }
                break;
            case 8:
                if (emptyIndex == 5 || emptyIndex == 7) {
                    move(clickPosition);
                }
                break;
        }
    }

    private void move(int clickPosition) {
        listRes.set(emptyIndex, listRes.get(clickPosition));
        listRes.set(clickPosition, 0);
        emptyIndex = clickPosition;
        notifyDataSetChanged();
        checkIfComplete();
    }

    /**
     * 校验是否拼图完成
     */
    private void checkIfComplete() {
        boolean isComplete = true;
        for (int i = 0; i < imgRightRes.length; i++) {
            if (listRes.get(i) == 0) {
                continue;
            }

            if (imgRightRes[i] != listRes.get(i)) {
                isComplete = false;
            }
        }

        if (isComplete) {
            isCompleted = true;
            Toast.makeText(context, "恭喜你 拼图成功", Toast.LENGTH_SHORT).show();
            listRes.set(emptyIndex, imgRightRes[emptyIndex]);
            notifyDataSetChanged();
        }


    }

    @Override
    public MainRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.img.setImageResource(listRes.get(position));
        holder.img.setOnClickListener(v -> checkMove(position));
    }

    @Override
    public int getItemCount() {
        return imgRightRes.length;
    }

}


