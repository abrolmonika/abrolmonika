$(document).ready(function() {
    
    var $form = $('.form'),
        // Dropdown DOM objects
        $dropdownOne = $('.dropdown-one'),
        $dropdownTwo = $('.dropdown-two');

    var data = $form.data('cars-details');

    // Check if data is valid
    if (typeof data === 'object') { 
        var cars = data.cars;  
        var dropdownOneOPtions = '<option value="">Select</option>';
       
        //Populating First Dropdown Field with Options
        if (Array.isArray(cars) && cars.length > 0) {
        	cars.forEach(function(carObj) {
        		//$dropdownOne.html(''); // Emptying the first dropdown
            	var car = carObj.car;
                dropdownOneOPtions = dropdownOneOPtions + '<option name=' + car + '>' + car + '</option>';
            });
        }

        $dropdownOne.html(dropdownOneOPtions);
    }

    //Populating Second Dropdown Field with Options
   $dropdownOne.on('change', function() {
   		var selectedCar = $(this).val();
   		var selectedObjArr = data.cars.filter(function(carObj) {
      		return (selectedCar === carObj.car);
      	});

       	var dropdownHtml;
      
      	if (selectedObjArr && selectedObjArr.length) {
      		var selectedObj = selectedObjArr[0];
       	    var model = selectedObj.model;

            model.forEach(function(item) {
        		dropdownHtml = dropdownHtml + '<option value="' + item +'">' + item + '</option>';
        	});
      	 }
      $dropdownTwo.html(dropdownHtml);
    });

});