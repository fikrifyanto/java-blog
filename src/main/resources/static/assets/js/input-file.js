const inputFile = document.getElementById('input-file')
const inputFilePreview = document.getElementById('input-file-preview')
const inputFilePreviewImage = document.querySelector('#input-file-preview img')
const inputFileSelect = document.getElementById('input-file-select')
const inputFileDefault = document.getElementById('input-file-default')
const clearButton = document.getElementById('clear-button')

document.addEventListener('DOMContentLoaded', function () {
    if (inputFile && clearButton) {
        if(inputFileDefault) {
            inputFilePreview.classList.remove('hidden')
            console.log(inputFileDefault.value)
            inputFilePreviewImage.src = inputFileDefault.value
            inputFileSelect.classList.add('hidden')
        }

        inputFile.addEventListener('change', (event) => {
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
})