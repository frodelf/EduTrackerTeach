var messageForDelete = "Об'єкт успішно видалено"
var messageForSave = "Об'єкт успішно збережено"

function showSuccessToast(message) {
    toastr.options.progressBar = true;
    toastr.success(message)
}
function showErrorToast(message) {
    toastr.options.progressBar = true;
    toastr.error(message)
}
function showToastForDelete(){
    toastr.options.progressBar = true;
    toastr.success(messageForDelete)
}
function showToastForSave(){
    toastr.options.closeButton = true
    toastr.success(messageForSave)
}