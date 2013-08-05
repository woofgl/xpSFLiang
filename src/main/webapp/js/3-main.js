var app = app || {};

(function(){
	
	app.pathInfo = {};
	
	app.propsFromInputs = function($baseEl, prefix){
		var props = {};
		$baseEl.find("input, select").each(function(){
			var $input = $(this);
			var name = $input.attr("name");
			if (name && (!prefix || name.indexOf(prefix) === 0)){
				props[name] = $input.val(); 
			}
		});
		return props;
	}
	
	app.toServerParams = function(obj){
	  for(var k in obj){
	    var val = obj[k];
	    console.log(typeof val);
	    if(typeof val == "object"){
	      obj[k] = JSON.stringify(val);
	    }
	  }
	  return obj;
	}
	
	app.getValues = function($baseEl, prefix){
	  var props = app.propsFromInputs($baseEl);
	  $baseEl.find("*[data-props-name]").each(function(){
      var $content = $(this);
      var name = $content.attr("data-props-name");
      
      if (name && (!prefix || name.indexOf(prefix) === 0)){
        var type = $content.attr("data-props-type");
        var val;
        if(type == "array"){
          val = [];
          $content.find("*[data-props-type='item']").each(function(){
            var $item = $(this);
            var item = {};
            $.each(this.attributes,function(i,attr){
              var name = attr.name;
              if(attr.name.indexOf("data-props") != 0){
                var value = attr.value;
                var key = name;
                key = key.replace("data-","");
                // get a key name with variable name rule
                var newKey = [];
                for(var k = 0; k < key.length; k++){
                  if(key[k] == "-" && key[k+1]){
                    newKey.push(new String(key[k+1]).toUpperCase());
                    k++;
                  }else if(key[k] != "_"){
                    newKey.push(key[k]);
                  }
                }
                newKey = newKey.join("");
                item[newKey] = value;
              }
            });
            val.push(item);
          });
        }else if(type == "object"){
          val = {};
          if($content[0].tagName.toLowerCase() == "select"){
            var $option = $content.find(":selected");
            var idKey = $content.attr("data-props-id-label") || "id";
            var nameKey = $content.attr("data-props-name-label") || "name";
            val[idKey] = $option.val();
            val[nameKey] = $option.text();
          }
        }
        
        props[name] = val; 
      }
    });
    return props;
	}
	
	app.fillInputs = function($baseEl,values){
		// TODO: for now, just support the select with data-fill-with
		$baseEl.find("input, select").each(function(){
			var tagName = this.tagName;
			var $input = $(this);
			var name = $input.attr("name");
			
			if ("SELECT" === tagName){
				var fillWith = $input.attr("data-fill-with");
				if (fillWith){
					var dao = brite.dao(fillWith);
					dao.list().done(function(list){
						
						var item, i, l = list.length, html = "";
						for (i = 0; i < l; i++){
							item = list[i];
							html += "<option value='" + item.id + "'>";
							html += item.name || item.username;
							html += "</option>\n";  
						}
						$input.append(html);
					});
				}
				
			}
		});
	}
	
	// --------- dao initialization -------- //
	brite.registerDao(new RemoteDaoHandler("User"));
	brite.registerDao(new RemoteDaoHandler("Product"));
	brite.registerDao(new RemoteDaoHandler("Signoff"));
	brite.registerDao(new RemoteDaoHandler("ApproverType"));
	brite.registerDao(new RemoteDaoHandler("FileType"));
	brite.registerDao(new app.ApprovalDaoHandler());
	brite.registerDao(new app.FileDaoHandler());
	// --------- /dao initialization -------- //
	
	// --------- Extra Remote APIs --------- //
	app.myApprovals = function(){
		return RemoteDaoHandler.executeAjax($.Deferred(),"/myApprovals");
	}
	//deprecated use ApprovalDaoHandler updateApproval
	app.approve = function(approvalId){
		return RemoteDaoHandler.executeAjax($.Deferred(),"/approve",{approvalId:approvalId},true);
	}
	// --------- /Extra Remote APIs --------- //
	
	app.formatMonth = function(month){
    var months = ["January","February","March","April","May","June","July","August","September","October","November","December"];
    return months[month];
  }
	
})();

(function(w){  
  w.render = function(templateName,data){
    var tmpl = Handlebars.templates[templateName];
    if (tmpl){
      return tmpl(data);
    }else{
      // obviously, handle this case as you think most appropriate.
      return "<small>Error: could not find template: " + templateName + "</small>";
    }
  }
})(window);  

Handlebars.registerHelper('ifNot', function (v1, options) {
	return (!v1) ? options.fn(this) : options.inverse(this);
});
Handlebars.registerHelper('p', function (v1, options) {
  console.log(v1);
});
	
Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {

    switch (operator) {
    		case '&&':
    				return (v1 == v2) ? options.fn(this) : options.inverse(this);
    				break;    	
    		case '||':
    				return (v1 == v2) ? options.fn(this) : options.inverse(this);
    				break;    	
    		case '!=':
    				return (v1 == v2) ? options.fn(this) : options.inverse(this);
    				break;
        case '==':
            return (v1 == v2) ? options.fn(this) : options.inverse(this);
            break;
        case '===':
            return (v1 === v2) ? options.fn(this) : options.inverse(this);
            break;
        case '<':
            return (v1 < v2) ? options.fn(this) : options.inverse(this);
            break;
        case '<=':
            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
            break;
        case '>':
            return (v1 > v2) ? options.fn(this) : options.inverse(this);
            break;
        case '>=':
            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
            break;
        default:
            return options.inverse(this)
            break;
    }
    //return options.inverse(this);
});