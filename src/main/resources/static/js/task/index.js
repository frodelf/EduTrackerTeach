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
                cell1.innerHTML = `<a href="${contextPath}course/${object.id}">${object.name}</a>`

                var cell2 = newRow.insertCell(2);
                cell2.innerHTML = object.status;

                var cell3 = newRow.insertCell(3);
                cell3.innerHTML = `
<a href="${contextPath}student/${object.id}" class="btn btn-outline-secondary float-end"><i class="fa-regular fa-eye"></i></a>
<button onclick="showModalForRemove(${object.id})" class="btn btn-outline-primary"><i class="fa-solid fa-trash"></i></button>`;
            }
            $('#pagination_container').empty();
            if (objects.totalPages > 1) updatePagination(page, objects.totalPages, 'pagination_container')
        },
    })
}