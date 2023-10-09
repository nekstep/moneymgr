/**
 * When account-name edit link is clicked
 */
$('#account-name-a').click(function (e) {
    e.preventDefault();

    // hide readonly field and show input box
    $('#account-name-readonly').hide();
    $('#account-name-edit').show();
});

/**
 * When Enter is pressed inside input box to rename account
 */
$('#account-name-input').keyup(function (e) {
    if (e.key === "Enter") {

        // post Ajax to update account name
        $.ajax({
            context: this,
            method: 'post',
            url: '/app/account/update',
            data: {
                id: $('#account-name-input').data('account-id'),
                name: $('#account-name-input').val(),
                method: 'post'
            },
            success: function (response) {
                // double check that account name is correct from server
                $('#account-name-text').text(response['name']);
                $('#account-name-input').val(response['name']);

                // hide edit box and show readonly label
                $('#account-name-readonly').show();
                $('#account-name-edit').hide();

                // reload sidebar account list
                $('#navbar-account-list').load('/app/account/nav');
            },
            error: function(xhr, errmsg) {
                console.log(errmsg);
            }
        })
    }
});

/**
 * When account-toggle is clicked
 */
$('#transaction-add-toggle').click(function (e) {
    e.preventDefault();

    // show input box
    $('#transaction-add-form').show();
});


/**
 * Fill in transactions
 */
$('#transaction-list-table').load('/app/transaction/account/' + $('#account-name-input').data('account-id'));

/**
 * Initialize the datepicker
 */
$('#form-transaction-add-date').datepicker();
$('#form-transaction-add-date').datepicker("option", "dateFormat", "yy-mm-dd");

