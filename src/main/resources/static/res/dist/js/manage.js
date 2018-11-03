
$(document).ready(function(){
	
	$BODY = $('body');
    $MENU_TOGGLE = $('#menu_toggle');
    $SIDEBAR_MENU = $('#sidebar_menu');
    $SIDEBAR_FOOTER = $('.sidebar-footer');
    $LEFT_COL = $('.left_col');
    $RIGHT_COL = $('.right_col');
    $NAV_MENU = $('.nav_menu');
    $FOOTER = $('footer');
	
	$MENU_TOGGLE.on('click', function() {
		if ($BODY.hasClass('nav-md')) {
			$SIDEBAR_MENU.find('li.active ul').hide();
			$SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
		} else {
			$SIDEBAR_MENU.find('li.active-sm ul').show();
			$SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
		}
		$BODY.toggleClass('nav-md nav-sm');
	});	
	
	$SIDEBAR_MENU.find('a').on('click', function(ev) {
        var $li = $(this).parent();

        if ($li.is('.active')) {
            $li.removeClass('active active-sm');
            $li.find("a > span").removeClass("fa-chevron-down").addClass("fa-chevron-left");
            $('ul:first', $li).slideUp(function() {
//              setContentHeight();
            });
        } else {
        	$li.find("a > span").removeClass("fa-chevron-left").addClass("fa-chevron-down");
            // prevent closing menu if we are on child menu
            if (!$li.parent().is('.child_menu')) {
                $SIDEBAR_MENU.find('li').removeClass('active active-sm');
                $SIDEBAR_MENU.find('li ul').slideUp();
            }else
            {
				if ( $BODY.is( ".nav-sm" ) )
				{
					$SIDEBAR_MENU.find( "li" ).removeClass( "active active-sm" );
					$SIDEBAR_MENU.find( "li ul" ).slideUp();
				}
			}
            $li.addClass('active');

            $('ul:first', $li).slideDown(function() {
//              setContentHeight();
            });
        }
    });


})



function tab(page, tbName, fun){
    page = page + ".html";
    $("#container").empty();
    $("#loading").show();
    setTimeout(function(){
        if(tbName){
            $("#container").load(page, function(){
                $("#loading").fadeOut(1000);
                $("#container").fadeIn(1600);
                initTableData(tbName);
            });
        }else{
            if(typeof fun == "function")
                $("#container").load(page, function(){
                    $("#loading").fadeOut(1000);
                    $("#container").fadeIn(1600);
                    fun();
                });
            else
                $("#container").load(page, function(){
                    $("#loading").fadeOut(1000);
                    $("#container").fadeIn(1600);
                });
        }
    }, 500);

}

