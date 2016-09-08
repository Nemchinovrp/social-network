$(function () {
    $("#accountName").autocomplete({
        source: function (request, response) {
            $.ajax({
                // url: "<c:url value="/searchByAccounts"/>",
                data: {
                    filter: request.term
                },
                success: function (data) {
                    response($.map(data, function (account, i) {
                        return {value: account.name + ' ' + account.lastName + ' ' + account.id,
                            id: account.id,
                            label: account.name + ' ' + account.lastName}
                    }));
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
            location.href = '<c:url value="/account/acc?id="/>' + ui.item.id;
        }
    });
});