package com.example.serayacrud.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.serayacrud.R;
import com.example.serayacrud.models.Product;
import com.example.serayacrud.models.ProductViewModel;

public class AddProductActivity extends AppCompatActivity {

    EditText edtName, edtPrice, edtDesc, edtProdId;
    Button btnAdd, btnCancel;

    ProductViewModel viewModel;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setTitle("Add Product");

        btnAdd = findViewById(R.id.btnAdd);
        edtDesc = findViewById(R.id.edtDesc);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtProdId = findViewById(R.id.edtProdID);

        Intent intent = getIntent();

        if (intent.hasExtra("PRODUCT")){
            getSupportActionBar().setTitle("Update Product");
            btnAdd.setText("Update Product");
            Product product = (Product) intent.getSerializableExtra("PRODUCT");
            edtName.setText(product.getProductName());
            edtPrice.setText(String.valueOf(product.getProductPrice()));
            edtDesc.setText(product.getProductDescription());
            edtProdId.setText(product.getProductId());
            edtProdId.setEnabled(false);
        }

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEdtEmpty()){
                    String id = edtProdId.getText().toString();
                    String name = edtName.getText().toString();
                    int price = edtPrice.getText().toString() == "" ? 0: Integer.parseInt( edtPrice.getText().toString());
                    String desc = edtDesc.getText().toString();

                    product = new Product(id, name, desc, price);
                    viewModel.insert(product);
                    Intent intentBack = new Intent();
                    intentBack.putExtra("PRODUCT", product);
                    setResult(RESULT_OK, intentBack);
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public boolean isEdtEmpty() {
        if (!TextUtils.isEmpty(edtName.getText().toString()) && !TextUtils.isEmpty(edtProdId.getText().toString()) &&
                !TextUtils.isEmpty(edtDesc.getText().toString()) && !TextUtils.isEmpty(edtPrice.getText().toString())
        ) {
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();

    }
}