/**
 * 
 */


$(document).on("click", "#submitButton", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
    	var code = editor.getValue();					// Get content of text area
    	$("#submitButton")[0].disabled = true;				// Disable submit button
    	$("#resultdiv").text("");							// No response shown
        $.post("AjaxServlet", {cpp_code : code}, function(data, textStatus, request) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
        	var check_robustness = request.getResponseHeader('robustness');
        	var is_sat = request.getResponseHeader('sat');
        	var msg = "";
        	if (is_sat == 1) {
        		if (check_robustness == 0) {
        			//msg = "Assertion fails:";
        		} else {
        			//msg = "Program is not robust:";
        		}
        		msg = '<img src="data:image/png;base64,' + data + '" />';           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.	
        	} else {
        		if (check_robustness == 0) {
        			msg = 'Assertion holds';
        		} else {
        			msg = 'Program is robust';
        		}
        	}
        	$('#resultdiv').html(msg);
            $("#submitButton")[0].disabled = false;		// Enable submit button
        });
    });