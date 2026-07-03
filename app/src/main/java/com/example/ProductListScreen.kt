package com.example.nazahapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nazahapp.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onAddProductClick: () -> Unit,
    onProductClick: (ProductModel) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    val firestore = FirebaseFirestore.getInstance()

    LaunchedEffect(searchQuery) {
        firestore.collection("products")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener
                val fetchedProducts = snapshot.documents.mapNotNull { it.toObject(ProductModel::class.java) }
                products = if (searchQuery.isEmpty()) {
                    fetchedProducts
                } else {
                    fetchedProducts.filter {
                        it.name.contains(searchQuery, ignoreCase = true) || 
                        it.productCode.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("NAZAH Products") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProductClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Search...") },
                leadingIcon = { Icon(Icons.Default.Search, null) }
            )
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(products) { product ->
                    Card(modifier = Modifier.fillMaxWidth().clickable { onProductClick(product) }) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Column {
                                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Code: ${product.productCode}")
                                Text(text = "৳${product.price}", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}
