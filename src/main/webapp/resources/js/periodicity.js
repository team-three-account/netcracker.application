function cron2text() {
    var cronExp = String($('#cron').val());
    console.log(cronExp);
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

function deletePeriodicity() {
    $('#periodicity').val(null);
    $('#cron').val(null);
}

var isCrontabsHidden = true;

function changePeriodicity() {
    if (!isCrontabsHidden) {
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
    var hours = getHours();
    var day = getDay();
    var dom = day[0];
    var dow = day[1];
    var month = getMonth();
    var year = getYear();
    var cronExp = seconds + ' ' + minutes + ' ' + hours + ' ' + dom + ' ' + month + ' ' + dow + ' ' + year;
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
    return $("#dateStart").val().substr(14, 2);
};

function getHours() {
    return $("#dateStart").val().substr(11, 2);
};

function getDay() {
    var dow = '';
    var dom = '';
    if ($('#cronEveryMonth:checked').length) {
        dom = $('#dateStart').val().substr(8, 2);
        dow = '?';
    }
    if ($('#cronEveryYear:checked').length) {
        dom = $('#dateStart').val().substr(8, 2)
        dow = '?';
    }
    if ($('#cronEveryDay:checked').length) {
        dow = '*';
        dom = '?';
    }
    else if ($('#cronDomIncrement:checked').length) {
        dom = $('#cronDomIncrementStart').val();
        dom = $('#dateStart').val().substr(8, 2);
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
            $('#cronDowSun').prop("checked", true);
            dow = 'SUN';
        } else {
            dow = dow.slice(0, -1);
        }
    }
    return [dom, dow];
};

function insertDaysOfMonth() {
    var currentYear = parseInt($("#dateStart").val().substr(0, 4));
    var currentMonth = $("#dateStart").val().substr(5, 2);
    var currentDay = parseInt($("#dateStart").val().substr(8, 2));
    var countOfDays;
    const domJanuary = 31;
    const domFebruary = currentYear % 4 == 0 ? 29 : 28;
    const domMarch = 31;
    const domApril = 30;
    const domMay = 31;
    const domJune = 30;
    const domJuly = 31;
    const domAugust = 31;
    const domSeptember = 30;
    const domOctober = 31;
    const domNovember = 30;
    const domDecember = 31;
    switch (currentMonth) {
        case "01":
            countOfDays = domJanuary;
            break;
        case "02":
            countOfDays = domFebruary;
            break;
        case "03":
            countOfDays = domMarch;
            break;
        case "04":
            countOfDays = domApril;
            break;
        case "05":
            countOfDays = domMay;
            break;
        case "06":
            countOfDays = domJune;
            break;
        case "07":
            countOfDays = domJuly;
            break;
        case "08":
            countOfDays = domAugust;
            break;
        case "09":
            countOfDays = domSeptember;
            break;
        case "10":
            countOfDays = domOctober;
            break;
        case "11":
            countOfDays = domNovember;
            break;
        case "12":
            countOfDays = domDecember;
            break;
    }
    $(".domOption").remove();
    var cronDomIncrement = $("#cronDomIncrementIncrement");
    for (i = 1; i < countOfDays - currentDay + 2; i++) {
        cronDomIncrement.append("<option class=\"domOption\" value=\"" + i + "\">" + i + "</option>");
    }
}

function getMonth() {
    var month = '';
    if ($('#cronEveryYear:checked').length) {
        month = $('#dateStart').val().substr(5, 2);
    } else {
        month = "*";
    }
    return month;
}

function getYear() {
    return "*";
}


function changeDayOfMonth() {
    cron();
    $('#dayOfMonth').text($('#dateStart').val().substr(8, 2));
    insertDaysOfMonth();
};
