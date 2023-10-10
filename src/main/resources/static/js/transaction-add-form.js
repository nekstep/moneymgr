$(document).off('submit', '#form-transaction-add');
$(document).on('submit', '#form-transaction-add', function(e) {
    e.preventDefault();

    $.ajax({
        context: this,
        type: $(this).attr('method'),
        url: $(this).attr('action'),
        data: {
            id: null,
            date: $('#form-transaction-add-date').val(),
            amount: $('#form-transaction-add-amount').val(),
            accountId: $('#form-transaction-add-accountId').val(),
            method: $(this).attr('method')
        },
        success: function(response) {
            // reload account
            $('#main-app-content').load('/app/account/details/' + response['accountId']);
        },
        error: function(xhr, errmsg) {
            console.log(errmsg);
        }
    });
});
