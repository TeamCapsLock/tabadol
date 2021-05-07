let animationTime = 200; //millisecond
//wait until all html page elements are loaded

$(document).ready(function(){


    //hide the form once the page loaded
    $('.edit-form').hide();

   
     //show edit post form
     $('#edit-post-btn').click(function(){
         $('#edit-form').fadeToggle(animationTime);
     });

      //hide user edit profile form on load
    $('#edit-profile-form').hide();

    //show edit profile form
     $('#edit-profile-btn').click(function(){
        $('#edit-profile-form').slideToggle(animationTime);
    });



    function validatePassword(){
        //confirm password
        let password = $('#password').val();
        let confirmPassword = $('#confirm-password').val();
        if(password != confirmPassword)
            document.getElementById('confirm-password').setCustomValidity("Passwords Don't Match");

        else
            document.getElementById('confirm-password').setCustomValidity("");
    }

    $('#password').change(validatePassword);
    $('#confirm-password').keyup(validatePassword);

    $('#phone').keyup(function(){
        // debugger;
        let pattern = /^07(7|8|9)[0-9]{7}$/g;
        let phoneNumber = $('#phone').val();
        var isValid = pattern.test(phoneNumber);
            if(isValid){
                document.getElementById('phone').setCustomValidity("");
            }
            else{
                document.getElementById('phone').setCustomValidity("Phone number invalid");
            }
    });

    $('#image_url').keyup(function(){
        let imageUrl = $('#image_url').val();
        if(imageUrl.length < 5 || (imageUrl.slice(-4)==='jpeg' && imageUrl.length < 6)){
            document.getElementById('image_url').setCustomValidity("invalid image url");
        }else{
           if(imageUrl.slice(-3)==='jpg'||imageUrl.slice(-3)==='png'||imageUrl.slice(-3)==='gif'||imageUrl.slice(-3)==='bmp' ||imageUrl.slice(-4)==='jpeg'){
            document.getElementById('image_url').setCustomValidity("");
           }
           else{
            document.getElementById('image_url').setCustomValidity("invalid image url");
           }
        }
    });
});

function showOneEditForm(id){
    $(`#${id}`).fadeToggle(animationTime);

}


//  $('#btn-div4').click(showOneForm('make-offer-form4','make-exchange-form4'));
//  $('#exchange-btn').click(showOneForm('make-exchange-form4','make-offer-form4'));

function showOneForm(id, operation){
    if(operation == "add"){
        alert("add")
        $(`#make-offer-form${id}`).fadeToggle(animationTime);
        $(`#make-exchange-form${id}`).hide();
    }else{
        alert("exchange")
        $(`#make-offer-form${id}`).hide();
        $(`#make-exchange-form${id}`).fadeToggle(animationTime);
    }
}

