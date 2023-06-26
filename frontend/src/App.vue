<script setup>
import { onMounted, ref, unref } from 'vue'
import Modal from './components/ModalWindow.vue'
import axios from "axios";

const newTodo = ref('')
const date = ref('')
const todos = ref([])

const showModal = ref(false)
/**
 * @typedef {Object} Todo
 * @property {string} id
 * @property {string} task
 * @property {string} deadline
 * @property {boolean} done
 */
/** @type {import('vue').Ref<Todo>} */
const modalTodo = ref()

async function showTaskList() {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/tasks')
    todos.value = response.data
  } catch (err) {
    console.log(err)
  }
}
onMounted(showTaskList)

async function addTask() {
  try {
    await axios.post('http://localhost:8080/api/v1/add-task', null,{
      params: {
        task: newTodo.value,
        deadline: date.value
      }
    })
    await showTaskList()
    newTodo.value = ''
    date.value = ''
  } catch (err) {
    console.log(err)
  }
}

/**
 * @param {Todo} todo
 */
function clickedUpdateButton(todo) {
  showModal.value = true

  modalTodo.value = todo
}

/**
 * @param {string} modalId
 * @param {string} modalTask
 * @param {string} modalDeadline
 * @param {boolean} modalDone
 */
async function updateTask(modalId, modalTask, modalDeadline, modalDone) {
  try {
    await axios.post('http://localhost:8080/api/v1/update-task', null, {
      params: {
        id: unref(modalId),
        task: unref(modalTask),
        deadline: unref(modalDeadline),
        done: unref(modalDone)
      }
    })
    await showTaskList()
    showModal.value = false
  } catch (err) {
    console.log(err)
  }
}

async function deleteTask(modalId) {
  try {
    await axios.post('http://localhost:8080/api/v1/delete-task', null, {
      params: {
        id: unref(modalId)
      }
    })
    await showTaskList()
    showModal.value = false
  } catch (err) {
    console.log(err)
  }
}
</script>

<template>
  <h1>ToDoApp</h1>

  <div class="form_area">
    <form @submit.prevent='addTask'>
      <div class="form_input">
        <label>Task: </label>
        <input v-model='newTodo' placeholder="what's your task?">
      </div>
      <div class="form_input">
        <label>Deadline: </label>
        <input v-model='date' type=date>
      </div>
      <button>post</button>
    </form>
  </div>

  <hr />

  <div class='task_list'>
    <table>
      <thead>
      <tr>
        <th class='hidden'>ID</th>
        <th>Task</th>
        <th>Deadline</th>
        <th>Done</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for='todo in todos' :key='todo.id'>
        <td class='hidden'>{{ todo.id }}</td>
        <td>{{ todo.task }}</td>
        <td>{{ todo.deadline }}</td>
        <td>{{ todo.done ? '完了' : '未完了' }}</td>
        <td>
          <button class="update_button" @click='clickedUpdateButton(todo)'>更新</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <!-- モーダル -->
  <div v-if='showModal'>
    <Teleport to='body'>
      <Modal :data="modalTodo"
             @close='showModal = false'
             @update="(
            /** @type {string} */ modalId,
            /** @type {string} */ modalTask,
            /** @type {string} */ modalDeadline,
            /** @type {boolean} */ modalDone) => {
            updateTask(modalId, modalTask, modalDeadline, modalDone)
          }"
             @delete="(
            /** @type {string} */ modalId) => deleteTask(modalId)"/>
    </Teleport>
  </div>
</template>

<style>
body {
  background-color: #4e454a;
}

h1 {
  color: white;
}

.task_list table {
  border-collapse: collapse;
  width: 700px;
}
.task_list table tr {
  border: 1px solid;
  text-align: center;
}
.task_list table th {
  border: 1px solid;
}
.task_list table td {
  border: 1px solid;
}
.update_button {
  color: white;
  background-color: #001e43;
  border: none;
}

.form_area {
  display: flex;
  justify-content: center;
}
form {
  border: 1px solid;
  background-color: #001e43;
  height: 100px;
  width: 800px;
  padding: 20px 10px 10px 10px;
}
.form_input {
  margin-bottom: 15px;
}
.form_input input {
  width: 500px;
}
.form_input label {
  float: left;
  width: 80px;
  color: white;
}
form button {
  border: none;
  width: 100px;
  height: 23px;
  background-color: #4285f4;
  color: white;
  float: right;
  margin-right: 50px;
}

hr {
  margin: 30px 0 30px 0;
}

table {
  color: white;
  border-collapse: collapse;
}
.hidden {
  display: none;
}
</style>