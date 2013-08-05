var app = app || {};

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