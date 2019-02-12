package yahier.com.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 拼图游戏
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        MainRecycleAdapter mAdapter = new MainRecycleAdapter(this);
        recyclerView.setAdapter(mAdapter);

        //重新开局
        findViewById(R.id.btnRestart).setOnClickListener(v -> {
            mAdapter.init();
        });

    }
}
