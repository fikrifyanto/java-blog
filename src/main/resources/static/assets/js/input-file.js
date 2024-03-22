const inputFile = document.getElementById('input-file')
const inputFilePreview = document.getElementById('input-file-preview')
const inputFilePreviewImage = document.querySelector('#input-file-preview img')
const inputFileSelect = document.getElementById('input-file-select')
const clearButton = document.getElementById('clear-button')

if (inputFile && clearButton) {
    inputFile.addEventListener('change', (event) => {
        console.log('oke')
        const [file] = event.target.files

        if (file) {
            inputFilePreview.classList.remove('hidden')
            inputFilePreviewImage.src = URL.createObjectURL(file)
            inputFileSelect.classList.add('hidden')
        }
    })

    clearButton.addEventListener('click', () => {
        inputFile.value = null
        inputFilePreviewImage.src = null
        inputFilePreview.classList.add('hidden')
        inputFileSelect.classList.remove('hidden')
    })
}