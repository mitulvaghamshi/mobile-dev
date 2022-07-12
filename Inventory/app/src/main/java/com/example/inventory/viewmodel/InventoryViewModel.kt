package com.example.inventory.viewmodel

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private fun insertItem(item: Item) {
        viewModelScope.launch { itemDao.insert(item) }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String) = Item(
        itemName = itemName,
        itemPrice = itemPrice.toDouble(),
        quantityInStock = itemCount.toInt()
    )

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) =
        insertItem(getNewItemEntry(itemName, itemPrice, itemCount))

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String) =
        itemName.isNotBlank() && itemPrice.isNotBlank() && itemCount.isNotBlank()

    fun retrieveItem(id: Int) = itemDao.getItem(id).asLiveData()

    fun updateItem(item: Item) {
        viewModelScope.launch { itemDao.update(item) }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch { itemDao.delete(item) }
    }

    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    fun isStockAvailable(item: Item) = item.quantityInStock > 0
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
