<html>
    <head>
        <meta charset=utf-8>
        <title>Calculator</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <link rel="stylesheet" href="stylesheet.css">
        <script>
		
            // perform code in selfinvoked anonymous function when document is fully loaded
            (function () {$(function(){
			
                    var 
                    result = $('input'),
                    backspase = $('.bksp'),
                    clear = $('.clear'),
                    num = $('.button.num'),
                    togglesign = $('.togglesign'),
                    adddot = $('.adddot'),
                    op = $('.op'),
                    eq = $('.eq')
                    ;			

                    function CalcHelper()
                    {							
                        this.oppressed = 0;
                        this.isBset = 0;
                        this.a = "notset";
                        this.b = "notset";					
                        this.op = "notset";
                        this.result = "notset";

                        this.curoperand = "a";
                    }

                    var calcHelper = new CalcHelper();

                    function jsTestCalculator()
                    {
                        console.log("dbg performCalc() a: " + calcHelper.a);
                        console.log("dbg performCalc() b: " + calcHelper.b);
                        switch(calcHelper.op)
                        {
                            case "+":
                                {
                                    console.log(Number(calcHelper.a) + Number(calcHelper.b));
                                    return Number(calcHelper.a) + Number(calcHelper.b);							
                                }
                            case "-":
                                {
                                    console.log(Number(calcHelper.a) - Number(calcHelper.b));
                                    return Number(calcHelper.a) - Number(calcHelper.b);	
                                }
                            case "/":
                                {
                                    console.log(Number(calcHelper.a) / Number(calcHelper.b));
                                    return Number(calcHelper.a) / Number(calcHelper.b);
                                }
                            case "*":
                                {
                                    console.log(Number(calcHelper.a) * Number(calcHelper.b));
                                    return Number(calcHelper.a) * Number(calcHelper.b);
                                }
                            default:
                                {
                                    console.log("unknown operation for performCalc supplied: " + calcHelper.op);
                                    return "notset!!!";						
                                }
                        }
                    }                                 
                        
                    //todo: artifex: code is broken in this place: correct it!
                    function externalCalc()
                    {
                        console.log("dbg externalCalc() a: " + calcHelper.a);
                        console.log("dbg externalCalc() b: " + calcHelper.b);
                        console.log("dbg externalCalc() op: " + calcHelper.op);
                        console.log("dbg externalCalc() calcHelper: " + calcHelper);
                        
                        var _result;
                        
                        $.ajax({
                            async: false,
                            type: "POST",
                            url: "<%=request.getContextPath()%>/dec",
                            data: "a=" + calcHelper.a + ";" + "b=" + calcHelper.b + ";" + "op=" + calcHelper.op + ";",
                            contentType: "application/x-www-form-urlencoded",
                            dataType: "text",
                            
                            success: function (data) 
                            {
                                console.log("dbg externalCalc() result: " + data);
                                _result = data;                             
                            },
                            error: function (data) 
                            {
                                console.log("error occured: " + data);
                                result.val("0");
                                //reset object
                                calcHelper = new CalcHelper();
                            },
                            complete: function () 
                            {
                                // Handle the complete event
                                console.log("dbg complete");
                            }
                        }); 
                        
                        return _result;
                    }    
                    function performCalc()
                    {				
                        var _result = externalCalc(); 
                        var resultAsJSONObj = $.parseJSON(_result);
                        console.log("dbg performCalc() _result: " + _result);
                        return resultAsJSONObj.result;                        
                    }

                    function writeToCurrentOperand(result)			
                    {
                        if(calcHelper.curoperand == "b")
                        {
                            calcHelper.b = result;
                        }
                        else
                        {
                            calcHelper.a = result;
                        }
                        console.log("dbg writeToCurrentOperand() result: " + result);
                        console.log("dbg writeToCurrentOperand() a b: " + calcHelper.a + " " + calcHelper.b);				
                    }			

                    function switchToB()
                    {
                        calcHelper.curoperand = "b";	
                    }

			
                    //handle math operation buttons
                    op.on('click', function()
                    {
                        calcHelper.oppressed = 1;
				
                        var resultval;
				
                        if(calcHelper.isBset == 1)
                        {
                            console.log(calcHelper);
                            calcHelper.isBset = 0; //consume
                            resultval = performCalc();										
                            calcHelper.a = resultval;
                            result.val(resultval);
                        }
										
                        calcHelper.op = $(this).text();
                    });


                    //handle backspase
                    backspase.on('click', function()
                    {   				
                        if(calcHelper.oppressed == 1)
                        {
                            return;
                        }

                        var resultval = result.val();   				
                        if(resultval == "0")	
                        {
                            return;
                        }
                        else
                        {
                            var _result = resultval.slice(0, resultval.length - 1);
                            if(_result.length == 0)
                            {
                                _result = 0;
                            }	
                            result.val(_result);
                        }

                        //update CalcHelper
                        writeToCurrentOperand(result.val());
                    });

                    //handle number keys press
                    num.on('click', function() 
                    {
                        var resultval;

                        if(calcHelper.oppressed == 1)
                        {
                            calcHelper.oppressed = 0;
                            resultval = "";					
                            switchToB();
                            calcHelper.isBset = 1;
                        }
                        else
                        {
                            resultval = result.val();
                        }
				
                        thistext = $(this).text();
                        if(resultval == "0")
                        {
                            result.val(thistext);					
                        }
                        else
                        {
                            result.val(resultval + thistext);
                        }
			    
                        // update CalcHelper
                        writeToCurrentOperand(result.val());
                    });	

                    //handle clear
                    clear.on('click', function() 
                    {
                        result.val("0");
                        //reset object
                        calcHelper = new CalcHelper();
                    });	

                    //handle togglesign
                    togglesign.on('click', function() 
                    {
                        if(calcHelper.oppressed == 1)
                        {
                            return;
                        }

                        var resultval = result.val();
                        if(resultval == 0)
                        {
                            return;
                        }			    
                        else if(resultval.charAt(0) == "-")
                        {
                            result.val(resultval.slice(1));
                        }
                        else
                        {
                            result.val("-" + resultval);
                        }
                        // update calcHelper
                        writeToCurrentOperand(result.val());
                    });	

                    //handle adddot
                    adddot.on('click', function()
                    {
                        if(calcHelper.oppressed == 1)
                        {
                            return;
                        }

                        var resultval = result.val();
                        if(resultval.search("\\.") == -1)
                        {
                            result.val(resultval + ".");
                        }
                        else
                        {
                            return;
                        }

                        // update calcHelper
                        writeToCurrentOperand(result.val());					
                    });

                    //handle equals
                    eq.on('click', function()
                    {								
                        var a = result.val();				
                        if(calcHelper.oppressed == 1)
                        {
                            calcHelper.oppressed = 0;
                            switchToB();
                            writeToCurrentOperand(a);
                        }

                        if(calcHelper.a == "notset" || calcHelper.b == "notset")
                        {
                            return;
                        }
                        var resultval = performCalc();	
								
                        calcHelper.a = resultval;
                        calcHelper.result = resultval;

                        result.val(resultval);

                        calcHelper.isBset = 0;
                    });

                })})();
        </script>
    </head>
    <body>

        <table cellPadding = "0">
            <tr>
                <td colspan="4"><input class="result" value=0 type=text readonly="readonly"></td>
            </tr>
            <tr>
                <td colspan="1"><button class="button bksp"><</button></td>
                <td colspan="3"><button class="button clear">C</button></td>    
            </tr>	
            <tr>
                <td><button class="button num">7</button></td>
                <td><button class="button num">8</button></td>
                <td><button class="button num">9</button></td> 
                <td><button class="button op divide">/</button></td>      
            </tr>
            <tr>
                <td><button class="button num">4</button></td>
                <td><button class="button num">5</button></td>
                <td><button class="button num">6</button></td> 
                <td><button class="button op multiply">*</button></td>      
            </tr>
            <tr>
                <td><button class="button num">1</button></td>
                <td><button class="button num">2</button></td>
                <td><button class="button num">3</button></td> 
                <td><button class="button op subtract">-</button></td>      
            </tr>
            <tr>
                <td><button class="button num">0</button></td>
                <td><button class="button togglesign">+/-</button></td>
                <td><button class="button adddot">.</button></td> 
                <td><button class="button op add">+</button></td>      
            </tr>
            <tr>
                <td colspan="4"><button class="button eq">=</button></td>
            </tr>
        </table>

    </body>
</html>