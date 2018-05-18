jQuery(function ($) {
    $('.dateValid').datetimepicker({
        mask: '9999-19-39 29:59',
        format: 'Y-m-d H:i'
    });
});

$('.subSeconds').text(function () {
    return $(this).text().substr(0, 24);
});