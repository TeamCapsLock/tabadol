//wait until all html page elements are loaded

$(document).ready(function(){
    //hide the form once the page loaded
    $('.edit-form').hide();

   
     //show edit post form
     $('#edit-post-btn').click(function(){
         $('#edit-form').fadeToggle(500/*millisecond*/);
     });

      //hide user edit profile form on load
    $('#edit-profile-form').hide();

    //show edit profile form
     $('#edit-profile-btn').click(function(){
        $('#edit-profile-form').slideToggle(200);
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
        let pattern = /^[0-9]{10}$/g;
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
        if(imageUrl.length < 6 || (imageUrl.slice(-4)==='jpeg' && imageUrl.length < 7)){
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
    $(`#${id}`).fadeToggle(500);

 }