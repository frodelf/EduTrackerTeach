var page = 0
getPageWithFilter(page)

function getPageWithFilter(page) {
    this.page = page
    $.ajax({
        type: "Get",
        url: contextPath + 'course/get-all',
        data: {
            page: page,
            pageSize: 6
        },
        success: function (courses) {
            var content = ``
            for (var i = 0; i < courses.content.length; i++) {
                if (i === 0) content += `<div class="row mt-0 gy-4">`
                else if (i % 2 === 0) content += `</div><div class="row mt-0 gy-4">`
                content += addBlock(courses.content[i])
                if (i === courses.content.length - 1) {
                    content += `</div>`
                    $("#courses").html(content)

                    $('#pagination_container').empty();
                    if (courses.totalPages > 1) updatePagination(page, courses.totalPages, 'pagination_container')
                    return
                }
            }
        },
    })
}

function addBlock(course) {
    return `
        <div class="col-lg-6 col-md-12">
            <div class="card">
                <div class="card-content">
                    <div class="row" style="width: 96%; margin-left: 2%">
                        <div class="col-9">
                            <p class="font-weight-bond fs-3">${course.name}</p>
                            <br/>
                            Ціль курсу: ${course.goal}
                        </div>
                        <div class="col-3" style="padding-right: 0">
                            <img src="${course.image}" width="90%" height="90px" style="margin-top: 10px" class="float-end">
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <button class="btn btn-danger float-end" onclick="remove(${course.id})"><i class="fa-solid fa-trash"></i></button>
                    <button class="btn btn-primary float-end" style="margin-right: 10px" onclick="edit(${course.id})"><i class="fa-solid fa-pencil"></i></button>
                    <button class="btn btn-secondary float-end" style="margin-right: 10px" onclick="view(${course.id})"><i class="fa-regular fa-eye"></i></button>
                </div>
            </div>
        </div>
    `
}
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('redirectToAdd').onclick = function () {
        window.location.href = contextPath+'course/add'
    };
});
function edit(id){
    window.location.href = contextPath+'course/edit/'+id
}
function view(id) {
    var modalBlock = document.createElement('div');
    modalBlock.innerHTML = `
        <div class="modal fade" id="ModalForView" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Статистика</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-3 col-md-6 col-sm-12">
                                <div class="card drag-item  mb-lg-0 mb-6">
                                    <div class="card-body text-center">
                                    <h2>
                                        <i class="fa-solid fa-users" style="color: #B197FC;"></i>                                    
                                    </h2>
                                    <h4>Активні студенти</h4>
                                    <h5>13</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-12">
                                <div class="card drag-item  mb-lg-0 mb-6">
                                    <div class="card-body text-center">
                                    <h2>
                                        <i class="fa-solid fa-book-open" style="color: #B197FC;"></i>
                                    </h2>
                                    <h4>Кількість літератури</h4>
                                    <h5>67</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-12">
                                <div class="card drag-item  mb-lg-0 mb-6">
                                    <div class="card-body text-center">
                                    <h2>
                                        <i class="fa-solid fa-person-chalkboard" style="color: #B197FC;"></i>
                                    </h2>
                                    <h4>Проведено занять</h4>
                                    <h5>14</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-12">
                                <div class="card drag-item mb-lg-0 mb-6">
                                    <div class="card-body text-center">
                                    <h2>
                                        <i class="fa-solid fa-list-check" style="color: #B197FC;"></i>
                                    </h2>
                                    <h4>Кількість задач</h4>
                                    <h5>
                                        <div style="display: flex; justify-content: center; align-items: center">
                                            <div>40   (</div>
                                            <div style="color: green">13</div>
                                            <div>-</div>
                                            <div style="color: red">27</div>
                                            <div>)</div>
                                        </div>
                                    </h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
    if(!($('#ModalForView').html()))document.body.appendChild(modalBlock);
    $('#ModalForView').modal('show');
}
function remove(id){
    $.ajax({
        type: "DELETE",
        url: contextPath + 'course/remove',
        data: {
            id: id,
        },
        success: function (request) {
            getPageWithFilter(page)
            showToastForDelete()
        },
    })
}