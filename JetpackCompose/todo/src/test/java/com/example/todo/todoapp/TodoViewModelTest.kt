package com.example.todo.todoapp

import com.example.todo.data.TodoViewModel
import com.example.todo.data.generateRandomTodoItem
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TodoViewModelTest {

    @Test
    fun whenAddItem_updatesList() {
        val subject = TodoViewModel()
        val expected = generateRandomTodoItem()
        subject.addItem(expected)
        assertThat(subject.todoItems).isEqualTo(listOf(expected))
    }

    @Test
    fun whenRemovingItem_updatesList() {
        val subject = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        subject.addItem(item1)
        subject.addItem(item2)
        subject.removeItem(item1)
        assertThat(subject.todoItems).isEqualTo(listOf(item2))
    }

    @Test
    fun whenNotEditing_currentEditItemIsNull() {
        val subject = TodoViewModel()
        val item = generateRandomTodoItem()
        subject.addItem(item)
        assertThat(subject.currentEditItem).isNull()
    }

    @Test
    fun whenEditing_currentEditItemIsCorrectItem() {
        val subject = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        subject.addItem(item1)
        subject.addItem(item2)
        subject.onEditItemSelected(item1)
        assertThat(subject.currentEditItem).isEqualTo(item1)
    }

    @Test
    fun whenEditingDone_currentEditItemIsCorrectItem() {
        val subject = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        subject.addItem(item1)
        subject.addItem(item2)
        subject.onEditItemSelected(item1)
        subject.onEditDone()
        assertThat(subject.currentEditItem).isNull()
    }

    @Test
    fun whenEditingItem_updatesAreShownInItemAndList() {
        val subject = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        subject.addItem(item1)
        subject.addItem(item2)
        subject.onEditItemSelected(item1)
        val expected = item1.copy(task = "Update for test case")
        subject.onEditItemChange(expected)
        assertThat(subject.todoItems).isEqualTo(listOf(expected, item2))
        assertThat(subject.currentEditItem).isEqualTo(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenEditing_wrongItemThrows() {
        val subject = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        subject.addItem(item1)
        subject.addItem(item2)
        subject.onEditItemSelected(item1)
        val expected = item2.copy(task = "Update for test case")
        subject.onEditItemChange(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenNotEditing_onEditItemChangeThrows() {
        val subject = TodoViewModel()
        val item = generateRandomTodoItem()
        subject.onEditItemChange(item)
    }
}
