package com.se306.youseesoft.Data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.se306.youseesoft.Enums.Category;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.Models.Laptop;
import com.se306.youseesoft.Models.Phone;
import com.se306.youseesoft.Models.Tablet;

import java.util.ArrayList;
import java.util.List;

/**
 * A class providing static methods for accessing the database and populating the data
 */
public class DataUtil {

    private static final String DIV_1 = "Division_1";
    private static final String DIV_2 = "Division_2";
    private static final String DB = "YouSee_Database";
    private static final String CART = "Shopping_Cart";
    private static final String CAT = "category";

    /**
     * Fetch data from a certain category
     *
     * @param cat category to fetch from
     * @param listener the callback to execute when data has been fetched
     */
    static public void fetchFromCategory(Category cat, OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DB).document(DIV_1)
                .collection(cat.toString()).get().addOnCompleteListener(listener);
    }

    /**
     * Fetch data from a certain category with a minimum rating
     *
     * @param cat category to fetch from
     * @param minRating minimum rating that
     * @param listener the callback to execute when data has been fetched
     */
    static public void fetchWithRatingFromCategory(Category cat, double minRating, OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productCollectionRef = db.collection(DB)
                .document(DIV_1).collection(cat.toString());

        // Construct the search query
        Query searchQuery = productCollectionRef.whereGreaterThan("rating", minRating);

        searchQuery.get().addOnCompleteListener(listener);
    }

    /**
     * Delete a product from the database
     *
     * @param product product to be deleted from the database
     */
    static public void deleteProductFromCart(IProduct product) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DB).document(DIV_2)
                .collection(CART).document(product.getName()).delete();
    }

    /**
     * Add a product to the database
     *
     * @param product product to be added to the database
     */
    static public void addProductToCart(IProduct product) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DB).document(DIV_2)
                .collection(CART).document(product.getName()).set(product);
    }

    /**
     * Fetch products from the cart
     *
     * @param listener the callback to execute when data has been fetched
     */
    static public void fetchProductFromCart(OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DB).document(DIV_2)
                .collection(CART).get().addOnCompleteListener(listener);
    }

    /**
     * Convert document snapshot to IProduct
     *
     * @param snapshot a document snapshot representing the product
     * @return
     */
    static public IProduct snapshotToProduct(DocumentSnapshot snapshot) {
        if(snapshot.get(CAT).toString().equals(Category.Tablet.toString())) {
            return snapshot.toObject(Tablet.class);
        }
        if(snapshot.get(CAT).toString().equals(Category.Laptop.toString())) {
            return snapshot.toObject(Laptop.class);
        }
        if(snapshot.get(CAT).toString().equals(Category.Phone.toString())) {
            return snapshot.toObject(Phone.class);
        }

        return null;
    }

    /**
     * Convert query snapshot snapshot to IProduct list
     *
     * @param query a query snapshot representing the list of products
     * @return
     */
    static public List<IProduct> querySnapShotToListProduct(QuerySnapshot query) {
        List<IProduct> products = new ArrayList<>();
        for (DocumentSnapshot product : query) {
            products.add(DataUtil.snapshotToProduct(product));
        }
        return products;
    }
}
