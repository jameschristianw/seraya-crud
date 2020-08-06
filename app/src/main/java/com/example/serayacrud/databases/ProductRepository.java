package com.example.serayacrud.databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.serayacrud.models.Product;
import com.example.serayacrud.models.ProductDAO;

import java.util.List;

public class ProductRepository {
    private ProductDAO daoProduct;

    private LiveData<List<Product>> productList;

    public ProductRepository(Application application){
        ProductRoomDatabase db = ProductRoomDatabase.getDatabase(application);
        daoProduct = db.daoProduct();
        productList = daoProduct.getAllProduct();
    }

    public LiveData<List<Product>> getProductList() {
        return this.productList;
    }

    public void insert(Product product){
        new insertAsyncTask(daoProduct).execute(product);
    }

    public void delete(Product product){
        new deleteAsyncTask(daoProduct).execute(product);
    }

    public void update(Product product){
        new updateAsyncTask(daoProduct).execute(product);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(daoProduct).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncDaoProduct;

        insertAsyncTask(ProductDAO dao){
            this.asyncDaoProduct = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncDaoProduct.insertProduct(products[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncDaoProduct;

        deleteAllAsyncTask(ProductDAO dao){
            this.asyncDaoProduct = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncDaoProduct.deleteAllProduct();
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncDaoProduct;

        deleteAsyncTask(ProductDAO dao){
            this.asyncDaoProduct = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncDaoProduct.deleteAllProduct();
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncDaoProduct;

        updateAsyncTask(ProductDAO dao){
            this.asyncDaoProduct = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncDaoProduct.updateProduct(products[0]);
            return null;
        }
    }
}
