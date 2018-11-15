firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    document.getElementById("user_div1").style.display="initial";
    document.getElementById("login_div1").style.display="none";
    peta=sessionStorage.getItem("petaa");  
	var fireref=firebase.database().ref();
	document.getElementById("bulbon").style.display="none";
	document.getElementById("alarmon").style.display="none";	
	fireref.child("users/"+peta+"/light").set(0);
	fireref.child("users/"+peta+"/alarm").set(0);	
	var temcnt = firebase.database().ref('users/'+peta+'/temperature');
	temcnt.on('value', function(snapshot) {
		document.getElementById("tempt").innerHTML = "The temperature in celsius is: "+snapshot.val();
	});
	var humcnt = firebase.database().ref('users/'+peta+'/humidity');
	humcnt.on('value', function(snapshot) {
		document.getElementById("humt").innerHTML = "The Humidity value in gm/cubic meter is: "+snapshot.val();
	});
	var firecnt = firebase.database().ref('users/'+peta+'/fire');
	firecnt.on('value', function(snapshot) {
		if(snapshot.val())
			document.getElementById("desfire").innerHTML = "Alert!!! There is fire in the house ";
		else
			document.getElementById("desfire").innerHTML = "There is no fire in the house ";
	});
	var smokecnt = firebase.database().ref('users/'+peta+'/DigitalSmoke');
	smokecnt.on('value', function(snapshot) {
		if(snapshot.val())
			document.getElementById("dessmoke").innerHTML = "Alert!!! There is Gas/Smoke in the house ";
		else
			document.getElementById("dessmoke").innerHTML = "There is no Gas/Smoke in the house ";
	});
  } else {
    document.getElementById("login_div1").style.display="initial";
    document.getElementById("user_div1").style.display="none";
  }
});
var path;
function bulb(ab){
	var fireref=firebase.database().ref();
	if(ab==0){
		fireref.child("users/"+peta+"/light").set(0);
		document.getElementById("bulboff").style.display="initial";
		document.getElementById("bulbon").style.display="none";
	}
	else{
		fireref.child("users/"+peta+"/light").set(1);
		document.getElementById("bulbon").style.display="initial";
		document.getElementById("bulboff").style.display="none";
	}
}
function alarm(ab){
	var fireref=firebase.database().ref();
	if(ab==0){
		fireref.child("users/"+peta+"/alarm").set(0);
		document.getElementById("alarmoff").style.display="initial";
		document.getElementById("alarmon").style.display="none";
	}
	else{
		fireref.child("users/"+peta+"/alarm").set(1);
		document.getElementById("alarmon").style.display="initial";
		document.getElementById("alarmoff").style.display="none";
	}
}

function login(){
	var userEmail=document.getElementById("email_field").value;
	var userPass=document.getElementById("password_field").value;
	var ih=userEmail.length;
	var tem=userEmail.slice(0,ih-10);
	path=tem+userPass;
	sessionStorage.setItem("petaa", path);
	firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) { 
	  var errorCode = error.code;
	  var errorMessage = error.message;
	  window.alert("Error: "+ errorMessage);
	});
}
function create_account(){
	window.alert("Account created successfully");
	var userEmail=document.getElementById("email_field").value;
	var userPass=document.getElementById("password_field").value;
	var ih=userEmail.length;
	var tem=userEmail.slice(0,ih-10);
	var tim=tem+userPass;
	fireref.child("users/"+tim+"/light").set(0);
	fireref.child("users/"+tim+"/alarm").set(0);
	fireref.child("users/"+tim+"/temperature").set(0);
	fireref.child("users/"+tim+"/fire").set(0);
	fireref.child("users/"+tim+"/AnalogSmoke").set(0);
	fireref.child("users/"+tim+"/DigitalSmoke").set(0);
	fireref.child("users/"+tim+"/humidity").set(0);
	firebase.auth().createUserWithEmailAndPassword(userEmail, userPass).catch(function(error) {
	  var errorCode = error.code;
	  var errorMessage = error.message;
	  window.alert("Error: "+ errorMessage);
	});
}
function forgot_password(){
	var auth = firebase.auth();
	var emailAddress = document.getElementById("email_field").value;
	auth.sendPasswordResetEmail(emailAddress).then(function() {
	  window.alert("Password reset is sent to your email");
	}).catch(function(error) {
	  window.alert("Type correct email and retry");
	});
}
function send_verification(){
	var user = firebase.auth().currentUser;
	user.sendEmailVerification().then(function() {
	  window.alert("Verification email sent");
	}).catch(function(error) {
	  window.alert("Some error happened");
	});
}
function logout(){
  firebase.auth().signOut();
}
