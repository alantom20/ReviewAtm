package com.home.reviewatm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    public boolean logon = false;
    private List<Function> functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!logon){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);
            //startActivity(intent);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //recycler
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //adapter
        functions = new ArrayList<>();
        String[] func = getResources().getStringArray(R.array.functions);
        functions.add(new Function(func[0],R.drawable.func_transaction));
        functions.add(new Function(func[1],R.drawable.func_balance));
        functions.add(new Function(func[2],R.drawable.func_finance));
        functions.add(new Function(func[3],R.drawable.func_contacts));
        functions.add(new Function(func[4],R.drawable.func_exit));
        IconAdapter adapter = new IconAdapter();
        recyclerView.setAdapter(adapter);
    }
    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.iconHolder>{
        @NonNull
        @Override
        public iconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_icon,parent,false );
            return new iconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull iconHolder holder, int position) {
            final Function function = functions.get(position);
            holder.iconImage.setImageResource(function.getIcon());
            holder.nameText.setText(function.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClicked(function);
                }
            });

        }

        @Override
        public int getItemCount() {
            return functions.size();
        }

        public class iconHolder extends RecyclerView.ViewHolder{
            ImageView iconImage;
            TextView nameText;
            public iconHolder(@NonNull View itemView) {
                super(itemView);
                iconImage = itemView.findViewById(R.id.item_icon);
                nameText = itemView.findViewById(R.id.item_name);
            }
        }
    }

    private void itemClicked(Function function) {
        Log.d(TAG, "itemClicked: " + function.getName());
        switch (function.getIcon()) {
            case R.drawable.func_transaction:
                Intent trans = new Intent(this,TransActivity.class);
                startActivity(trans);
                break;
            case R.drawable.func_balance:
                break;
            case R.drawable.func_finance:
                Intent finance = new Intent(this,FinanceActivity.class);
                startActivity(finance);
                break;
            case R.drawable.func_contacts:
                Intent contact = new Intent(this,ContactActivity.class);
                startActivity(contact);
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN)
            if(resultCode != RESULT_OK){
                finish();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}