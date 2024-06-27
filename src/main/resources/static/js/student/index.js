var page = 0
$(document).ready(function () {
    const inputs = document.querySelectorAll('.for-filter');
    let timeout = null;
    inputs.forEach(input => {
        input.addEventListener('input', function () {
            clearTimeout(timeout)
            timeout = setTimeout(() => {
                getPageWithFilter(0)
            }, 1000)
        })
    })
    $('#coursesEl').on('change', function() {
        getPageWithFilter(0)
    })

    forSelect2("#coursesEl", contextPath+"course/get-for-select")

    getPageWithFilter(page)
})
function getPageWithFilter(page) {
    this.page = page
    var filterElements = $('.for-filter');
    $.ajax({
        type: "Get",
        url: contextPath + 'student/get-all',
        data: {
            page: page,
            pageSize: 1,
            group: filterElements[0].value,
            fullName: filterElements[1].value,
            course: filterElements[2].value,
            telegram: filterElements[3].value,
            phone: filterElements[4].value,
        },
        success: function (objects) {
            var table = document.getElementById("courseTable");
            var tbody = table.querySelector("tbody");
            $('#courseTable tbody').empty();
            for (var object of objects.content) {
                var newRow = tbody.insertRow();
                var cell0 = newRow.insertCell(0);
                cell0.innerHTML = `${object.group}`;

                var cell1 = newRow.insertCell(1);
                cell1.innerHTML = `<a href="${contextPath}student/${object.id}">${object.lastName} ${object.name} ${object.middleName}</a>`

                var cell2 = newRow.insertCell(2);
                var courses = ``
                if (object.courses) {
                    const entries = Object.entries(object.courses);
                    const courseLinks = entries.map(([key, value]) => {
                        return `<a href="${contextPath}course/edit/${key}">${value}</a>`
                    })
                    courses = courseLinks.join(', ')
                }
                cell2.innerHTML = courses;

                var cell3 = newRow.insertCell(3);
                cell3.innerHTML = `<a href="https://t.me/${object.telegram.replace("@", "")}">${object.telegram}</a>`;

                var cell4 = newRow.insertCell(4);
                cell4.innerHTML = `${object.phone ? object.phone : '———'}`;

                var cell5 = newRow.insertCell(5);
                cell5.innerHTML = `
<a href="${contextPath}student/${object.id}" class="btn btn-outline-secondary float-end" style="margin-right: 10px"><i class="fa-regular fa-eye"></i></a>
<button onclick="remove(${object.id})" class="btn btn-outline-primary"><i class="fa-solid fa-trash"></i></button>`;
            }
            $('#pagination_container').empty();
            if (objects.totalPages > 1) updatePagination(page, objects.totalPages, 'pagination_container')
        },
    })
}

function showModalForAddStudentStepFirst() {
    if ($('#ModalForAddStudentStepFirst').html()) $('#ModalForAddStudentStepFirst').remove()

    var modalBlock = document.createElement('div');
    modalBlock.innerHTML = `
        <div class="modal fade" id="ModalForAddStudentStepFirst" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Додати студента на курс</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Групи
                        <select id="groupForStudentAdding"></select>
                        Курс
                        <select id="coursesForStudentAdding"></select>
                    </div>
                    <div class="modal-footer">
                        <button class="float-end btn btn-primary">Шукати</button>
                    </div>
                </div>
            </div>
        </div>
    `;
    document.body.appendChild(modalBlock);
    $('#ModalForAddStudentStepFirst').modal('show');
    forSelect2("#coursesForStudentAdding", contextPath+"course/get-for-select")
    forSelect2WithSearchAndPageable("#groupForStudentAdding", contextPath+"student/get-group-for-select")
}

function remove(id) {
    $.ajax({
        url: contextPath + 'student/remove',
        type: 'DELETE',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: {
            id: id,
        },
        success: function (request) {
            getPageWithFilter(page)
            showToastForDelete()
        },
    })
}