package com.example.serayacrud.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.serayacrud.R;
import com.example.serayacrud.adapter.ProductAdapter;
import com.example.serayacrud.models.Product;
import com.example.serayacrud.models.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvProductList;
    TextView tvNoProduct;
    ProductAdapter adapter;
    List<Product> products;
    Context context;

    ProductViewModel productViewModel;

    public final static int REQUEST_ADD = 1;
    public final static int REQUEST_UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

        getSupportActionBar().setTitle("Seraya CRUD");

        tvNoProduct = findViewById(R.id.tvNoProduct);
//        tvNoProduct.setVisibility(View.VISIBLE);
        tvNoProduct.setVisibility(View.GONE);

        rvProductList = findViewById(R.id.rvProductList);
//        rvProductList.setVisibility(View.GONE);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(context);
        rvProductList.setAdapter(adapter);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getProductList().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products.size() > 0){
//                    rvProductList.setVisibility(View.VISIBLE);
//                    tvNoProduct.setVisibility(View.GONE);
                    adapter.setProductList(products);
                } else {
//                    rvProductList.setVisibility(View.GONE);
//                    tvNoProduct.setVisibility(View.VISIBLE);
                }
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Product product = adapter.getProductAtPos(pos);

                if (direction == ItemTouchHelper.RIGHT){
                    Toast.makeText(context, product.getProductName() + " deleted", Toast.LENGTH_LONG).show();
                    productViewModel.delete(product);
                    adapter.deleteProductFromList(product);
//                    adapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                    intent.putExtra("PRODUCT", product);
                    startActivityForResult(intent, REQUEST_UPDATE);
                }
            }

        });
        helper.attachToRecyclerView(rvProductList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });
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
        if (id == R.id.action_delete_all) {
            productViewModel.deleteAll();
            Toast.makeText(this, "All products deleted", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Product product = (Product) data.getSerializableExtra("PRODUCT");

            if (requestCode == REQUEST_ADD) {
                productViewModel.insert(product);
                Toast.makeText(this, product.getProductName() + " added", Toast.LENGTH_LONG).show();
            } else if (requestCode == REQUEST_UPDATE) {
                productViewModel.update(product);
                Toast.makeText(this, product.getProductName() + " updated", Toast.LENGTH_LONG).show();
            }
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}