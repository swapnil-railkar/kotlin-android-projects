package com.example.shoppinglist

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


data class ShoppingItem(
    var id : Int,
    var name : String,
    var quantity : Int,
    var isEditing : Boolean = false
)

@Composable
fun ShoppingListApp() {
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialogue by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    var shoppingItem : ShoppingItem
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // Display elements on top of each other.
        Button(
            onClick = { 
                showDialogue = true
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(shoppingItems) {
                item ->
                if (item.isEditing) {
                    EditShoppingItem(
                        item = item,
                        onEditComplete = {
                            editedName, editedQuantity ->
                            shoppingItems = shoppingItems.map {
                                it.copy(isEditing = false)
                            }

                            val editedItem = shoppingItems.find {
                                it.id == item.id
                            }

                            editedItem?.let {
                                it.name = editedName
                                it.quantity = editedQuantity
                            }

                        }
                    )
                } else {
                    ShoppingRow(
                        shoppingItem = item,
                        onEditClick = {
                            shoppingItems = shoppingItems.map{
                                it.copy(isEditing = it.id == item.id)
                            }
                        },
                        onDeleteClick = {
                            shoppingItems = shoppingItems - item
                        }
                    )
                }

            }
        }

    }
    
    if (showDialogue) {
         AlertDialog(onDismissRequest = {
             showDialogue = false
             },
             confirmButton = {
                 Row (
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(8.dp)
                 ) {
                     Button(onClick = {
                         val quantity = itemQuantity.toIntOrNull() ?: 0
                         shoppingItem = ShoppingItem(shoppingItems.size, itemName, quantity)
                         shoppingItems = shoppingItems + shoppingItem
                         showDialogue = false
                         itemName = ""
                         itemQuantity = ""
                         Toast.makeText(context, "New Item added", Toast.LENGTH_LONG)
                             .show()
                         printInfo(shoppingItem)
                     }) {
                         Text(text = "Add")
                     }

                     Spacer(modifier = Modifier.width(8.dp))

                     Button(onClick = {
                         showDialogue = false
                         Toast.makeText(context, "No New Item added", Toast.LENGTH_LONG)
                             .show()
                     }) {
                         Text(text = "Cancel")
                     }
                 }
             },
             title = { Text(text = "Add Shopping Item")},
             text = {
                 Column (
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(8.dp)
                 ) {
                     Spacer(modifier = Modifier.height(4.dp))

                     OutlinedTextField(
                         value = itemName,
                         onValueChange = {
                             itemName = it
                         },
                         label = { Text(text = "Enter Item")}
                     )
                     
                     Spacer(modifier = Modifier.height(4.dp))

                     OutlinedTextField(
                         value = itemQuantity,
                         onValueChange = {
                             itemQuantity = it
                         },
                         label = { Text(text = "Enter Quantity")}
                     )

                 }

             }
         )
    }
}

@Composable
fun EditShoppingItem(
    item: ShoppingItem,
    onEditComplete : (String, Int) -> Unit
) {
    var itemName by remember { mutableStateOf(item.name) }
    var itemQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            BasicTextField(
                value = itemName,
                onValueChange = {itemName = it},
                singleLine = true,
                modifier = Modifier
                    .padding(2.dp)
                    .wrapContentSize()
            )

            BasicTextField(
                value = itemQuantity,
                onValueChange = {itemQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .padding(2.dp)
                    .wrapContentSize(),
            )
        }
        
        Button(
            onClick = {
            isEditing = false
            onEditComplete(itemName, itemQuantity.toIntOrNull() ?: 1)
        }) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ShoppingRow(
    shoppingItem: ShoppingItem,
    onEditClick : () -> Unit,
    onDeleteClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color.Blue),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = shoppingItem.name, modifier = Modifier.padding(8.dp))
        Text(text = "Quantity : ${shoppingItem.quantity}", modifier = Modifier.padding(8.dp))
        
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Item")
            }

            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Item")
            }
        }
    }
}


fun printInfo(shoppingItem: ShoppingItem) {
    print("Item Added : \n" +
            "Id : ${shoppingItem.id} \n" +
            "name : ${shoppingItem.name} \n" +
            "quantity : ${shoppingItem.quantity} \n")
}