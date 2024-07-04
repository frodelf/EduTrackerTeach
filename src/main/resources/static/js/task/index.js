var page = 0
$(document).ready(function () {
    forSelect2("#coursesForFilter", contextPath + "course/get-for-select")
    forSelect2("#statusForFilter", contextPath + "enum/get-task-status")
    getPageWithFilter(page)
})
function getPageWithFilter(page) {
    this.page = page
    var filterElements = $('.for-filter');
    $.ajax({
        type: "Get",
        url: contextPath + 'task/get-all',
        data: {
            page: page,
            pageSize: 1,
            name: filterElements[0].value,
            courseId: filterElements[1].value,
            status: filterElements[2].value
        },
        success: function (objects) {
            var table = document.getElementById("taskTable");
            var tbody = table.querySelector("tbody");
            $('#taskTable tbody').empty();
            for (var object of objects.content) {
                var newRow = tbody.insertRow();
                var cell0 = newRow.insertCell(0);
                cell0.innerHTML = `${object.name}`;

                var cell1 = newRow.insertCell(1);
                var courses = ``
                if (object.course) {
                    const entries = Object.entries(object.course);
                    const courseLinks = entries.map(([key, value]) => {
                        return `<a href="${contextPath}course/edit/${key}">${value}</a>`
                    })
                    courses = courseLinks.join(', ')
                }
                cell1.innerHTML = courses;
                var cell2 = newRow.insertCell(2);
                if(object.status == 'OPEN'){
                    cell2.innerHTML=`<span class="badge bg-label-success">Відкрите</span>`
                }else {
                    cell2.innerHTML=`<span class="badge bg-label-danger">Закрите</span>`
                }
                var cell3 = newRow.insertCell(3);
                cell3.innerHTML = `
<button onclick="modalForRemove(${object.id})" class="btn btn-outline-danger float-end" style="margin-left: 10px"><i class="fa-solid fa-trash"></i></button>
<a href="${contextPath}task/edit/${object.id}" class="btn btn-outline-primary float-end" style="margin-left: 10px"><i class="fa-solid fa-pencil"></i></a>
<a href="${contextPath}task/${object.id}" class="btn btn-outline-secondary float-end"><i class="fa-regular fa-eye"></i></a>
`;
            }
            $('#pagination_container').empty();
            if (objects.totalPages > 1) updatePagination(page, objects.totalPages, 'pagination_container')
        },
    })
}
function modalForRemove(taskId){
    if ($('#modalForRemove').html()) $('#modalForRemove').remove()

    var modalBlock = document.createElement('div');
    modalBlock.innerHTML = `
        <div class="modal fade" id="modalForRemove" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ви впевені що хочете видалити об'єкт</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-footer">
                        <button class="float-end btn btn-danger" onclick="remove(${taskId})">Видалити</button>
                    </div>
                </div>
            </div>
        </div>
    `
    document.body.appendChild(modalBlock)
    $('#modalForRemove').modal('show')
}
function remove(taskId){
    $.ajax({
        url: contextPath + 'task/delete',
        type: 'DELETE',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: {
            taskId: taskId
        },
        success: function (request) {
            getPageWithFilter(page)
            showToastForDelete()
            $('#modalForRemove').modal('hide')
        },
    })
}