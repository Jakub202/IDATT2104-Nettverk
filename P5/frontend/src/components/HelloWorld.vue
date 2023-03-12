<template>
  <div class="container">
    <h2 class="title">Python runner</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="code">Enter your python code:</label>
        <textarea id="code" class="form-control" v-model="code"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Run</button>
    </form>
    <div v-if="output" class="output">
      <h3>Output:</h3>
      <pre>{{ output }}</pre>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Compiler',
  data() {
    return {
      code: '',
      output: '',
    };
  },
  methods: {
    async handleSubmit() {

      try {
        const response = await axios.post('http://localhost:8080/compile', {
          code: this.code,
        });
        this.output = response.data;
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>

<style>
.container {
  max-width: 600px;
  margin: auto;
}

.title {
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.btn {
  display: block;
  margin: auto;
}

.output {
  margin-top: 30px;
}
</style>
