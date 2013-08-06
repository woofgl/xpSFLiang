;(function() {

	/**
	 * View: Top
	 *
	 */
    (function ($) {
        brite.registerView("Top",  {parent:".MainScreen-header"}, {
            create:function (data, config) {
                var $html = app.render("tmpl-Top");
               	var $e = $($html);
                return $e;
            },
            events:{
            	"btap;.nav li":function(e){
            		var view = this;
            		var $e = view.$el;
            		var $li = $(e.currentTarget);
            		$e.find("li").removeClass("active");
            		$li.addClass("active");
            		var menu = $li.attr("data-nav");
                    console.log(menu);
            		if(menu == "contacts"){
            		  brite.display("SalesForceContacts");
            		}else if(menu == "oauth"){
            			app.oauth.authorize('SalesForce');
            		}

            	}
            }
        });
        
    })(jQuery);
})();