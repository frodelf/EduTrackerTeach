const snowEditor = new Quill('#goal', {
    bounds: '#goal',
    modules: {
        formula: true,
        toolbar: '#snow-toolbar'
    },
    theme: 'snow'
})

if (course) {
    $("#name").val(course.name)
    $("#maximumMark").val(course.maximumMark)
    setImage(course.image, 'image-preview')
    var delta = snowEditor.clipboard.convert(course.goal)
    snowEditor.setContents(delta)
    $.ajax({
        type: "Get",
        url: contextPath + 'course/statistic',
        data: {
            id: course.id
        },
        success: function (statistic) {
            $("#statistic").html(`
                <div class="row">
                    <div class="col-lg-6 col-sm-12"></div>
                    <div class="col-lg-6 col-sm-12">
                        <div class="card drag-item  mb-lg-0 mb-6">
                            <div class="card-body text-center">
                                <h2>
                                    <i class="fa-solid fa-lock" style="color: #f90101;"></i>
                                </h2>
                                <h4>Закриті завдання</h4>
                                <h5>${statistic.closeTasks}</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row gy-2 mt-1 mb-3">
                    <div class="col-lg-6 col-sm-12"></div>
                    <div class="col-lg-6 col-sm-12">
                        <div class="card drag-item  mb-lg-0 mb-6">
                            <div class="card-body text-center">
                                <h2>
                                    <i class="fa-solid fa-unlock" style="color: #63E6BE;"></i>
                                </h2>
                                <h4>Відкриті завдання</h4>
                                <h5>${statistic.openTasks}</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row gy-2 mt-1 mb-3">
                    <div class="col-lg-6 col-sm-12"></div>
                    <div class="col-lg-6 col-sm-12">
                        <button class="btn btn-danger" style="width: 100%; font-size: 20px">Закрити всі завдання</button>
                    </div>
                </div>
    `)
        }
    })
}
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('cancel').onclick = function () {
        window.location.href = contextPath + 'course'
    }
    document.getElementById('image').onchange = function () {
        previewImage("image-preview", "image")
    }
})

function save() {
    let formData = new FormData();
    formData.append("name", $("#name").val());
    formData.append("goal", $("#goal").val());
    formData.append("maximumMark", $("#maximumMark").val());
    formData.append("image", $("#image")[0].files[0]);

    $.ajax({
        url: contextPath + 'course/add',
        type: 'POST',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: formData,
        contentType: false,
        processData: false,
        success: function (request) {
            showToastForSave()
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                validDataFromResponse(xhr.responseJSON)
            } else {
                console.error('Помилка відправки файлів на сервер:', error);
            }
        }
    })
}