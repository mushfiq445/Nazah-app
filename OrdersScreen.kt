package com.example.nazahapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nazahapp.model.OrderModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen() {
    var orders by remember { mutableStateOf<List<OrderModel>>(emptyList()) }
    val firestore = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        firestore.collection("orders")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener
                orders = snapshot.documents.mapNotNull { it.toObject(OrderModel::class.java) }
            }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Orders") }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            items(orders) { order ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Product: ${order.productName}")
                        Text(text = "Customer: ${order.customerName}")
                        Text(text = "Status: ${order.status}")
                        Button(onClick = { 
                            val newStatus = if (order.status == "Pending") "Delivered" else "Pending"
                            firestore.collection("orders").document(order.id).update("status", newStatus)
                        }) {
                            Text("Toggle Status")
                        }
                    }
                }
            }
        }
    }
}
