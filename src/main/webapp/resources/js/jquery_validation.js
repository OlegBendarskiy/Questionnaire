(() => {
var form = $("#signUp");
var passwordIsCorrect;
const email = $('#signUp input[name="email"]');
const password = $('#signUp input[name="password"]');
const confirmPassword = $('#signUp input[name="confirmPassword"]');
const firstName = $('#signUp input[name="firstName"]');
const lastName = $('#signUp input[name="lastName"]');
const phoneNumber = $('#signUp input[name="phoneNumber"]');

function checkName(name){
	 return check(name, /^[A-ZА-ЯЁЪ]{1}[a-zа-яёъ]+$/, 'Use letter only and starting with upper letter');
}

function checkPhoneNumber() {
	 return check(login, /^([+]d{12}|d{10})$/, 'Enter correct phone number);
}

function checkSurname() {
	 return check(surname, /^[A-ZА-ЯЁЪ]{1}[a-zа-яёъ]+$/, 'Use letter only and starting with upper letter');
}

function checkEmail() {
    return check(email, /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/, 'Enter correct email')
}

function checkPassword() {
	 passwordIsCorrect = false;
	 passwordIsCorrect = check(password, /^[a-zA-Z].{5,20}$/, 'Use 6-20 simbols and starting with letter');
	 checkRepeatPassword();
     return passwordIsCorrect;
}

function check(input, regex, message) {
    let messageElement = input.parent().children(".invalid-tooltip");
	if (isNull(input)){
  		return false;
   	}
	 if(regex.test(input.val())){
		 input.removeClass("is-invalid");
         input.addClass("is-valid");
		 return true;
	 } else {
		 input.removeClass("is-valid");
         input.addClass("is-invalid");
		 messageElement.text(message);
		 return false;
	 }
}

function isNull(input){
    if (input.val() === ''){
    	input.removeClass("is-valid");
        input.addClass("is-invalid");
        input.parent().children(".invalid-tooltip").text('Field shouldn\'t be empty!!!!!!!!!!!!!!!!!!!!');
   		return true;
    } else {
        return false;
    }
}

function checkRepeatPassword() {
    let messageElement = repeatPassword.parent().children(".invalid-tooltip");
    if (isNull(repeatPassword)){
      	return false;
    }

	if (passwordIsCorrect){
	    if(password.val() == repeatPassword.val()){
    		 repeatPassword.removeClass("is-invalid");
             repeatPassword.addClass("is-valid");
    		 return true;
    	 } else {
    		 repeatPassword.removeClass("is-valid");
             repeatPassword.addClass("is-invalid");
             messageElement.text('Passwords do not match');
    		 return false;
    	 }
	} else {
	    repeatPassword.removeClass("is-valid");
	    repeatPassword.addClass("is-invalid");
	    messageElement.text('Incorrect password');
	    return false;
	}
}

email.blur(function() {return checkEmail()});
password.blur(function() {return checkPassword()});
confirmPassword.blur(function() {return checkRepeatPassword()});

firstName.blur(function() {return checkName(firstName)});
lastName.blur(function() {return checkName(lastName)});
phoneNumber.blur(function() {return checkPhoneNumber()});





form.submit(function (event) {
	if(!checkName() | !checkSurname() | !checkLogin() | !checkPassword() | !checkEmail() | !checkRepeatPassword()){
		event.preventDefault();
	}
});

})();
