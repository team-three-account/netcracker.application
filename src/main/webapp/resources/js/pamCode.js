// Декодування
(function () {
    if (document.getElementById('eventPlaceName')) {
        var inputPlace = document.getElementById('eventPlaceName');
        var inputPlaceText = inputPlace.value;
        if (inputPlaceText.length > 0) {
            var parser = new DOMParser;
            var parserText = parser.parseFromString(inputPlaceText, 'text/html').body.textContent;
            inputPlace.value = parserText;
        }
    }
})();

//оголошення змінних
var marker;
var map;

// це колбек функція яку ми загружаємо коли підгружається карта
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), // init map
        {
            zoom: 11,
            center: {lat: 50.449420, lng: 30.522503}
        });
    map.addListener('click', function (e) {//event click коли натискання на карту відбувається виклик функції e а вона в свою чергу кличе placeMarker...
        placeMarkerAndPanTo(e.latLng);
    });
    setMarkerFromInput();//dlya update
}

function placeMarkerAndPanTo(latLng) {
    if (!!marker) {
        marker.setMap(null)
    }
    marker = new google.maps.Marker({
        position: latLng, // по двох параметрах які у нас приходять з eventa - 'click'
        animation: google.maps.Animation.DROP,
        // draggable:true,
        map: map // setCurrentMap
    });
    placeEventLocationInfo(latLng);
    map.panTo(latLng);

    function placeEventLocationInfo(latLng) {
        var latInput = document.getElementById('latitude');
        var lngInput = document.getElementById('longitude');
        //  сетимо значення які дістаємо з eventa - 'click'
        latInput.value = latLng.lat();
        lngInput.value = latLng.lng();
        getAddress();
    }
}

function setMarkerFromInput() {
    var latInput = document.getElementById('latitude');
    var lngInput = document.getElementById('longitude');
    if (latInput && lngInput) {//якщо вони обоє існтують маємо true
        placeMarkerAndPanTo({lat: +latInput.value, lng: +lngInput.value}); //виклик функції placeMarkerAndPanTo  і приводимо  наші змінні до NUMBER за доп.+
    }
}

// для create щоб записати в string eventPlaceAdress
function getAddress() {
    var pos = marker.getPosition(); // отримаємо позишн нашого маркера
    var eventName = document.getElementById('eventPlaceName');
    getFormattedAddress(pos.lat(), pos.lng()) // дана функція створює проміс, а проміс потрібен для того щоб
    // у проміса є парамтери then and catch
    // якщо дана функція повертає хорошення виконання проміса то результат сетиться у then,якщо ж повертається reject то результат сетиться у catch
        .then(function (data) {
            eventName.value = data
        })
        .catch(function (data) {
            alert(data)
        });

}

function getFormattedAddress(latitude, longitude) {
    // якщо проміс виконається вдало то ретарн resolve , якщо невдало то return reject
    return new Promise(function (resolve, reject) {
        var request = new XMLHttpRequest();

        var method = 'GET';
        // запит щоб довготу і ширину зробити String адресою
        var url = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + latitude + ',' + longitude + '&key=AIzaSyAFJb-oxFvvvPRvwubCZwYkPQC0rRUbtOM';
        var async = true;

        request.open(method, url, async);
        // коли отримали респонс викликається дана функція
        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                if (request.status == 200) {
                    // якщо у нас отримався респонс то ми парсимо нашу строку адреси в string
                    var data = JSON.parse(request.responseText);
                    console.log(data);
                    console.log(request.responseText);
                    // adress це data v then and catch
                    var address = data.results[0].formatted_address;
                    resolve(address);
                }
                else {
                    reject(request.status);
                }
            }
        };
        request.send();
    });
};


// function getMyMYMYadress(latitude, longitude) {
//     var url = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + latitude + ',' + longitude + '&key=AIzaSyAFJb-oxFvvvPRvwubCZwYkPQC0rRUbtOM';
//     $.getJSON(url, function (data) {
//         for (var i = 0; i < data.results.length; i++) {
//             var adress = data.results[i].formatted_address;
//             alert(adress);
//         }
//     });
// }



