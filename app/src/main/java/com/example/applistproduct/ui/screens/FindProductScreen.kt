package com.example.applistproduct.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.applistproduct.factories.ProductRepositoryFactory
import com.example.applistproduct.model.data.Product
import com.example.applistproduct.ui.screens.components.ProductList

@Composable
fun FindProductScreen() {
    val productName = remember {mutableStateOf("")}
    val numberOfProducts = remember {mutableStateOf("")}
    val products = remember { mutableStateOf(emptyList<Product>()) }

    val productRepository = ProductRepositoryFactory.getProductRepository()

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(productName, "Search product")

            SearchBar(numberOfProducts, "Select product quantity")

            val onShowProductsClick = {
                if (numberOfProducts.value.toInt() > 0) {
                    productRepository.getProducts(productName.value, numberOfProducts.value.toInt()) { response ->
                        products.value = response
                    }
                }
                else {
                    products.value = emptyList()
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onShowProductsClick,
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text("Show products")
            }

            ProductList(products)
        }
    }
}

@Composable
fun SearchBar(input: MutableState<String>, placeholder: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = input.value,
            onValueChange = {
                input.value = it
            },
            placeholder = {
                Text(text = placeholder)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .weight(1f)
        )
    }
}
