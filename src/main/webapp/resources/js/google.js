
    function onRegistration(googleUser) {
        var profile = googleUser.getBasicProfile();
        var imagurl=profile.getImageUrl();
        var name=profile.getGivenName();
        var surname=profile.getFamilyName();
        var email=profile.getEmail();
        document.getElementById("name").value = name;
        document.getElementById("surname").value = surname;
        document.getElementById("email").value = email;
        document.getElementById("email").readOnly = true;
        }

    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        var email=profile.getEmail();
        document.getElementById("username").value = email;
        document.getElementById("username").readOnly = true;
    }

    function signOut() {
        gapi.auth2.getAuthInstance().disconnect();
        location.reload();
    }

