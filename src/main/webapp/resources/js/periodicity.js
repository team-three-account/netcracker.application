// $(function () {
//     if ($('#crontabs').length) {
//         $('#crontabs input, #crontabs select').change(cron);
//         cron();
//         changePeriodicity();
//     } else {
//         cron2text();
//     }
// });

function cron2text() {
    var cronExp = String($('#cron').val());
    console.log(cronExp);
    // var regExp = new RegExp(".*\s.*\s.*\s.*\s.*\s.*\s.*");
    //TODO regex make zero-based day
    var firstWhiteSpacePos = cronExp.indexOf(' ');
    cronExp = cronExp.substr(firstWhiteSpacePos);
    console.log(prettyCron.toString(cronExp));
    if (cronExp != '') {
        $("#periodicity").val(prettyCron.toString(cronExp));
        $("#periodicity").text('Periodicity : ' + prettyCron.toString(cronExp));
    } else {
        $("#periodicity").val('');
        $("#periodicity").text('Periodicity : -');
    }
};

function deletePeriodicity(){
    $("#crontabs").hide();
    $('#periodicity').val(null);
    $('#cron').val(null);
}

var isCrontabsHidden = true;

function changePeriodicity() {
    // if (!$("#isPeriodical").prop("checked") || $("#crontabs").is(":hidden")) {
    if (!isCrontabsHidden) {
        $("#crontabs").hide();
        $('#periodicity').val(null);
        $('#cron').val(null);
    } else {
        $("#crontabs").show();
        $('#periodicity').val(cron2text());
    }
    isCrontabsHidden = !isCrontabsHidden;
    cron();
};

function cron() {
    $(this).parents('.cron-option').children('input[type="radio"]').attr("checked", "checked");
    var seconds = getSeconds();
    var minutes = getMinutes();
    var day = getDay();
    var dom = day[0];
    var dow = day[1];
    var hours = getHours();
    var month = getMonth();
    var year = getYear();
    var cronExp = seconds + ' ' + minutes + ' ' + hours + ' ' + dom + ' ' + month + ' ' + dow + ' ' + year;
    // $('.cronResult').text(cronExp);
    $('#cron').val(cronExp);
    cron2text();
    console.log(cronExp);
    return cronExp;
};

function getSeconds() {
    var seconds = '0';
    return seconds;
};

function getMinutes() {
    var minutes = $('#time').val();
    minutes = minutes.substring(minutes.indexOf(":") + 1);
    return minutes;
};

function getHours() {
    var hours = $('#time').val();
    hours = hours.substring(0, hours.indexOf(":"));
    return hours;
};

function getDay() {
    var dow = '';
    var dom = '';
    if ($('#cronEveryDay:checked').length) {
        dow = '*';
        dom = '?';
    } else if ($('#cronDowIncrement:checked').length) {
        dow = $('#cronDowIncrementStart').val();
        dow += '/';
        dow += $('#cronDowIncrementIncrement').val();
        dom = '?';
    } else if ($('#cronDomIncrement:checked').length) {
        dom = $('#cronDomIncrementStart').val();
        dom += '/';
        dom += $('#cronDomIncrementIncrement').val();
        dow = '?';
    } else if ($('#cronDowSpecific:checked').length) {
        dom = '?';
        $('[name="cronDowSpecificSpecific"]:checked').each(function (i, chck) {
            dow += $(chck).val();
            dow += ',';
        });
        if (dow === '') {
            dow = 'SUN';
        } else {
            dow = dow.slice(0, -1);
        }
    } else if ($('#cronDomSpecific:checked').length) {
        dow = '?';
        $('[name="cronDomSpecificSpecific"]:checked').each(function (i, chck) {
            dom += $(chck).val();
            dom += ',';
        });
        if (dom === '') {
            dom = '1';
        } else {
            dom = dom.slice(0, -1);
        }
    }
    return [dom, dow];
    $('#cronResultDom').text(dom);
    $('#cronResultDow').text(dow);
    dayOfMonth = dom;
    dayOfWeek = dow;
};

function getMonth() {
    var month = '';
    if ($('#cronEveryMonth:checked').length) {
        month = '*';
    } else if ($('#cronMonthIncrement:checked').length) {
        month = $('#cronMonthIncrementStart').val();
        month += '/';
        month += $('#cronMonthIncrementIncrement').val();
    } else if ($('#cronMonthSpecific:checked').length) {
        $('[name="cronMonthSpecificSpecific"]:checked').each(function (i, chck) {
            month += $(chck).val();
            month += ',';
        });
        if (month === '') {
            month = '1';
        } else {
            month = month.slice(0, -1);
        }
    }
    return month;
};

function getYear() {
    var year = '2018';
    return year;
};