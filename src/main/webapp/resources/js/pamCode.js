var realStateApp = {
    maps: false,
};


// Декодування
(function () {
    function decodingHTML(obj) {
        var inputPlaceText = obj.value;
        if (inputPlaceText.length > 0) {
            var parser = new DOMParser;
            obj.value = parser.parseFromString(inputPlaceText, 'text/html').body.textContent;
        }
    }

    if (document.getElementsByClassName('decodingHtml')) {
        var node = document.getElementsByClassName('decodingHtml');
        for (var i = 0; i < node.length; i++) {
            decodingHTML(node[i]);
        }
    }
})();

// valid date
// (function () {
//     if (document.getElementsByClassName('dateValid')) { // перевірка на наявність інпутів які потрібно валідувати
//         var nodeDateInput = document.getElementsByClassName('dateValid'); // запис їх в змінну
//         for (var i = 0; i < nodeDateInput.length; i++) { // проходження по всіх інпутах та вішання на них обробників подій change
//             nodeDateInput[i].addEventListener('change', function () {
//                 var splitValue = this.value.split('-'); // розділення в масив (yyyy-mm-dd)
//                 if (splitValue[0].length > 4) { // перевірка чи довжина  року перевищує 4 цифри
//                     splitValue[0] = splitValue[0].slice(0, 4); // якщо перевищує обрізаємо до 4 цифр
//                     this.value = splitValue.join('-'); // записуємо назад
//                 }
//             }, false);
//         }
//     }
// })();

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
    var inputSearch = document.getElementById('eventPlaceName');
    if (inputSearch) {
        initSearchBox(inputSearch);
        map.addListener('click', function (e) {//event click коли натискання на карту відбувається виклик функції e а вона в свою чергу кличе placeMarker...
            placeMarkerAndPanTo(e.latLng);
        });
    }
    if (document.getElementById('latitude') && document.getElementById('latitude').value.length > 3) {
        setMarkerFromInput();
    }
}

function initSearchBox(inputSearch) {
    inputSearch.addEventListener('keyup', function () {
        realStateApp.maps = false;
        document.getElementById('eventPlaceName').style.cssText = '';
    }, false);
    // google search
    var autocomplete = new google.maps.places.Autocomplete(inputSearch);
    autocomplete.bindTo('bounds', map);
    var infowindow = new google.maps.InfoWindow();
    autocomplete.addListener('place_changed', function () {
        realStateApp.maps = false;
        infowindow.close();
        if (!!marker) {
            marker.setMap(null)
        }
        marker = new google.maps.Marker({
            animation: google.maps.Animation.DROP,
            // draggable:true,
            map: map,
            draggable: false// setCurrentMap
        });
        marker.setVisible(false);
        var place = autocomplete.getPlace();
        realStateApp.maps = true;
        if (!place.geometry) {
            // User entered the name of a Place that was not suggested and
            // pressed the Enter key, or the Place Details request failed.
            window.alert("No details available for input: '" + place.name + "'");
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        }
        map.setCenter(place.geometry.location);
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);

        var latInput = document.getElementById('latitude');
        var lngInput = document.getElementById('longitude');
        latInput.value = place.geometry.location.lat();
        lngInput.value = place.geometry.location.lng();

        var address = '';
        if (place.address_components) {
            address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
        }

        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
        infowindow.open(map, marker);
    });
}

function placeMarkerAndPanTo(latLng) {
    if (!!marker) {
        marker.setMap(null)
    }
    marker = new google.maps.Marker({
        position: latLng, // по двох параметрах які у нас приходять з eventa - 'click'
        animation: google.maps.Animation.DROP,
        // draggable:true,
        map: map,
        draggable: false// setCurrentMap
    });

    placeEventLocationInfo(latLng);
    map.panTo(latLng);

    function placeEventLocationInfo(latLng) {
        var latInput = document.getElementById('latitude');
        var lngInput = document.getElementById('longitude');
        //  сетимо значення які дістаємо з eventa - 'click'
        if (typeof latLng.lat === 'function') {
            latInput.value = latLng.lat();
            lngInput.value = latLng.lng();
        } else {
            latInput.value = latLng.lat;
            lngInput.value = latLng.lng;
        }
        getAddress();
    }
}

function setMarkerFromInput() {
    var latInput = document.getElementById('latitude');
    var lngInput = document.getElementById('longitude');
    var location = {lat: +latInput.value, lng: +lngInput.value};
    map.setCenter(location);
    placeMarkerAndPanTo(location); //виклик функції placeMarkerAndPanTo  і приводимо  наші змінні до NUMBER за доп.+
}

// для create щоб записати в string eventPlaceAdress
function getAddress() {
    var pos = marker.getPosition(); // отримаємо позишн нашого маркера
    var eventName = document.getElementById('eventPlaceName');
    if (eventName) {
        getFormattedAddress(pos.lat(), pos.lng()) // дана функція створює проміс, а проміс потрібен для того щоб
        // у проміса є парамтери then and catch
        // якщо дана функція повертає хорошення виконання проміса то результат сетиться у then,якщо ж повертається reject то результат сетиться у catch
            .then(function (data) {
                eventName.value = data;
                realStateApp.maps = true;
                document.getElementById('eventPlaceName').style.cssText = '';
            })
            .catch(function (data) {
                console.log('Error: ' + data);
                realStateApp.maps = false;
                document.getElementById('eventPlaceName').style.cssText = '';
            });

    }

}

function getFormattedAddress(latitude, longitude) {
    // якщо проміс виконається вдало то ретарн resolve , якщо невдало то return reject
    return new Promise(function (resolve, reject) {
        var request = new XMLHttpRequest();

        var method = 'GET';
        // запит щоб довготу і ширину зробити String адресою
        var url = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + latitude + ',' + longitude + '&key=AIzaSyAFJb-oxFvvvPRvwubCZwYkPQC0rRUbtOM&language=en';
        var async = true;

        request.open(method, url, async);
        // коли отримали респонс викликається дана функція
        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                if (request.status == 200) {
                    // якщо у нас отримався респонс то ми парсимо нашу строку адреси в string
                    var data = JSON.parse(request.responseText);
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

document.getElementById('draft').addEventListener('click', function () {
    realStateApp.maps = true;
}, false);

document.getElementById('valid_maps').addEventListener('submit', function (eventObj) {
    if (document.getElementById('checkDraft') && location.href.indexOf('convertToEvent') === -1) realStateApp.maps = true;

    if (!realStateApp.maps) {
        console.log('submit false');
        document.getElementById('eventPlaceName').style.cssText = 'border-color: #f00';
        eventObj.preventDefault();
        return false;
    }
}, false);



