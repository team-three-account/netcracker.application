jQuery(function ($) {
    try{
        $('.dateValid').datetimepicker({
            mask: '9999-19-39 29:59',
            format: 'Y-m-d H:i'
        });
    }catch (e){
        console.log("datetimepicker not added in this page")
    }

    $('.subSeconds').each(function () {
        $(this).val($(this).val().substr(0, 16));
        $(this).text($(this).text().substr(0, 16));
    });
});