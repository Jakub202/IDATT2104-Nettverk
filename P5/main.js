const codeForm = document.getElementById('code-form');
const codeInput = document.getElementById('code-input');
const outputDiv = document.getElementById('output');

function createTestFile(sourceCode) {
  const folderName = 'sourcecode';
  const fileName = 'test.cpp';

  if (!fs.existsSync(folderName)) {
    fs.mkdirSync(folderName);
  }

  fs.writeFileSync(`${folderName}/${fileName}`, sourceCode);
}

codeForm.addEventListener('submit', (event) => {
  event.preventDefault();

  const code = codeInput.value;

  createTestFile(code);
});
