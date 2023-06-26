<script setup>
import { ref } from 'vue'

const props = defineProps({
  data: Object
})
const emit = defineEmits(['update', 'delete', 'close'])

/** @type {string} */
const modalId = ref(props.data.id)
/** @type {string} */
const modalTask = ref(props.data.task)
/** @type {string} */
const modalDeadline = ref(props.data.deadline)
/** @type {boolean} */
const modalDone = ref(props.data.done)

function updated() {
  emit('update', modalId, modalTask, modalDeadline, modalDone)
  emit('close')
}

function deleted() {
  emit('delete', modalId)
  emit('close')
}

</script>

<template>
  <div id="overlay">
    <div class='modal'>
      <h3>Update Task</h3>
      <div class="modal_form">
        <div class="modal_content">
          <label>Task: </label>
          <input v-model="modalTask" />
        </div>
        <div class="modal_content">
          <label>Deadline: </label>
          <input type="date" v-model="modalDeadline" />
        </div>
        <div class="modal_content">
          <label>Done: </label>
          <input v-model="modalDone" />
        </div>
      </div>
      <div class="button_area">
        <div class="button_oneline">
          <button class="button_update" @click="updated()">Update</button>
          <button class="button_cancel" @click="$emit('close')">Cancel</button>
        </div>
        <button class="button_delete" @click="deleted()">DELETE THIS TASK</button>
      </div>
    </div>
  </div>
</template>

<style>
#overlay {
  z-index: 1;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.6);
}

.modal {
  position: fixed;
  z-index: 999;
  top: 20%;
  left: 50%;
  width: 480px;
  height: 270px;
  margin-left: -150px;
  background-color: black;
  color: aliceblue;
}
.modal h3 {
  padding: 0px 10px 0px 10px;
}
.modal_form {
  padding: 0px 10px 10px 10px;
}
.modal_content {
  margin-bottom: 10px;
}
.modal_content label {
  width: 100px;
  float: left;
}
.modal_content input {
  width: 200px;
}

.button_area {
  margin-top: 10px;
  margin-right: 5px;
  float: right;
}
.button_oneline {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 13px;
  height: 25px;
}
.button_update {
  background-color: #4285f4;
  color: white;
  width: 100px;
  border: none;
}
.button_cancel {
  background-color: #595959;
  color: white;
  width: 100px;
  border: none;
}
.button_delete {
  background-color: #cc0000ff;
  color: white;
  width: 230px;
  height: 25px;
  border: none;
}
</style>