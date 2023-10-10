$(document).off('submit', '#form-account-add');
$(document).on('submit', '#form-account-add', function(e) {
    e.preventDefault();

    $.ajax({
        context: this,
        type: $(this).attr('method'),
        url: $(this).attr('action'),
        data: {
            id: null,
            name: $('#form-account-add-name').val(),
            method: $(this).attr('method')
        },
        success: function(response) {
            // reload sidebar account list
            $('#navbar-account-list').load('/app/account/nav');

            // open newly created account in main app view
            $('#main-app-content').load('/app/account/details/' + response['id']);
        },
        error: function(xhr, errmsg) {
            console.log(errmsg);
        }
    });
});
