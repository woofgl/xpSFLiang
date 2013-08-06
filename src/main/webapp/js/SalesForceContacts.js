;(function() {

	/**
	 * View: SalesForceContacts
	 *
	 */
    (function ($) {
        brite.registerView("SalesForceContacts",  {emptyParent:true,parent:".content"}, {
            create:function (data, config) {
                var dfd = $.Deferred();
                app.sf.listContacts().done(function(data){
                    var html = app.render("tmpl-SalesForceContacts",{contacts:data.result});
                    dfd.resolve(html);
                });
                return dfd.promise();
            },
            postDisplay:function (data, config) {},
            events: {
                "btap; td.addLabel button": function(event){
                    var $tr = $(event.currentTarget).closest("tr");
                    var contactId = $tr.attr("data-url");
                    var label = $tr.find("input").val();
                    app.getJsonData("salesforce/addLabel", {contactId: contactId, lable: label}).done(function(data){
                        console.log($tr.find("td:nth-child(3)"));
                        console.log(data);
                        $tr.find("td:nth-child(3)").append(data.result);
                    });

                }
            }
        });
        
    })(jQuery);


})();
