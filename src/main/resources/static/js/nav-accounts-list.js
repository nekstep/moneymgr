$('.account-nav-link').click(function () {
    $('#main-app-content').load('/app/account/details/' + $(this).data('account-id'));
})