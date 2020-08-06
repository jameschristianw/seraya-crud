package com.example.serayacrud.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.serayacrud.databases.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private LiveData<List<Product>> productList;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        productList = productRepository.getProductList();
    }

    public LiveData<List<Product>> getProductList(){
        return this.productList;
    }

    public void insert(Product product){
        productRepository.insert(product);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public void delete(Product product){
        productRepository.delete(product);
    }

    public void update(Product product){
        productRepository.update(product);
    }
}
