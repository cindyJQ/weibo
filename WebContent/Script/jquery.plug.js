/// <reference path="jquery-1.4.2.min.js" />
/*
*   该文件存放各种JQurry插件
*/
(function ($) {
    jQuery.fn.LimitNumber = function () {
        ///<summary>限制只能输入数字</summary>
        var args = arguments;
        var json = typeof (args[0]) == "object";
        var scale = json ? args[0].scale : args[0];
        scale = scale || 0;
        $(this).css("ime-mode", "disabled");
        var keys = new Array(8, 9, 35, 36, 37, 38, 39, 40, 46);
        this.bind("keydown", function (e) {
            e = window.event || e;
            var code = e.which || e.keyCode;
            var idx = Array.indexOf(keys, code);
            if (idx != -1) {
                return true;
            }
            var value = this.value;
            if (code == 190 || code == 110) {
                if (scale == 0 || value.indexOf(".") != -1) {
                    return false;
                }
                return true;

            } else {
                if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105)) {
                    if (scale > 0 && value.indexOf(".") != -1) {
                        var reg = new RegExp("^[0-9]+(\.[0-9]{0," + (scale - 1) + "})?$");
                        if (!reg.test(value)) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        this.bind("blur", function () {
            if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
                this.value = this.value.substr(0, this.value.length - 1);
            } else if (isNaN(this.value)) {
                this.value = "";
            } else {
                var value = this.value;
                if (scale > 0 && value.indexOf(".") != -1) {
                    var reg = new RegExp("^[0-9]+(\.[0-9]{0," + scale + "})?$");
                    if (!reg.test(value)) {
                        this.value = format(value, scale);
                    }
                }
            }
        });
        this.bind("paste", function () {
            var s = window.clipboardData.getData('text');
            if (!/\D/.test(s));
            value = s.replace(/^0*/, '');
            return false;
        });
        this.bind("dragenter", function () {
            return false;
        });
        var format = function (value, scale) {
            return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
        }
        Array.indexOf = function (array, value) {
            for (var i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return i;
                }
            }
            return -1;
        }
    };

    jQuery.fn.LimitDecimal = function () {
        ///<summary>限制只能输入数字和小数点</summary>
        $(this).css("ime-mode", "disabled");
        this.bind("dragenter", function () {
            return false;
        });
        this.bind("keyup", function () {
            //先把非数字的都替换掉，除了数字和.
            this.value = this.value.replace(/[^\d.]/g, "");
            //        //必须保证第一个为数字而不是.
            this.value = this.value.replace(/^\./g, "");
            //        //保证只有出现一个.而没有多个.
            this.value = this.value.replace(/\.{2,}/g, ".");
            //保证.只出现一次，而不能出现两次以上
            this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            if (this.value == ".") {
                this.value = "";
            }
        });
    }

})(jQuery);
jQuery.fn.LimitNumberAndCode = function () {

    $(this).css("ime-mode", "disabled");
    this.bind("dragenter", function () {
        return false;
    });
    this.bind("keyup", function () {

        this.value = this.value.replace(/[^\w\.\/]/ig, '')
    });
}

jQuery.fn.setReadOnly = function () {
    ///<summary>设置一个控件只读</summary>
    this.css({ "background-color": "#F2F2F2","color":"999" });
    this.keydown(function () { return false; })
    this.attr("readOnly", true);
  
}
jQuery.fn.setWrite = function () {
    ///<summary>设置一个控件可写</summary>
    this.css({ "background-color": "#FFFFFF" });
    this.keydown(null)
    this.removeAttr("readOnly");

}

jQuery.SelectBox = function (selectobj, options) {

    var opt = options || {};
    opt.inputClass = opt.inputClass || "selectbox";
    opt.containerClass = opt.containerClass || "selectbox-wrapper";
    opt.hoverClass = opt.hoverClass || "current";
    opt.currentClass = opt.selectedClass || "selected"
    opt.debug = opt.debug || false;
   // opt.datajson = opt.datajson || {}//json格式的数据源
    var elm_id = selectobj.id;
    var active = 0;
    var inFocus = false;
    var hasfocus = 0;
    //jquery object for select element
    var $select = $(selectobj);
    // jquery container object
    var $container = setupContainer(opt);
    //jquery input object 
    var $input = setupInput(opt);
    // hide select and append newly created elements
    $select.hide().before($input).before($container);


    init();

    $input
	.click(function () {
	    if (!inFocus) {
	        $container.toggle();
	    }
	})
	.focus(function () {
	    if ($container.not(':visible')) {
	        inFocus = true;
	        $container.show();
	    }
	})
	.keydown(function (event) {
	    switch (event.keyCode) {
	        case 38: // up
	            event.preventDefault();
	            moveSelect(-1);
	            break;
	        case 40: // down
	            event.preventDefault();
	            moveSelect(1);
	            break;
	        //case 9:  // tab     
	        case 13: // return
	            event.preventDefault(); // seems not working in mac !
	            $('li.' + opt.hoverClass).trigger('click');
	            break;
	        case 27: //escape
	            hideMe();
	            break;
	    }
	})
	.blur(function () {
	    if ($container.is(':visible') && hasfocus > 0) {
	        if (opt.debug) console.log('container visible and has focus')
	    } else {
	        // Workaround for ie scroll - thanks to Bernd Matzner
	        if ($.browser.msie || $.browser.safari) { // check for safari too - workaround for webkit
	            if (document.activeElement.getAttribute('id').indexOf('_container') == -1) {
	                hideMe();
	            } else {
	                $input.focus();
	            }
	        } else {
	            hideMe();
	        }
	    }
	});


    function hideMe() {
        hasfocus = 0;
        $container.hide();
    }

    function init() {
        $container.append(getSelectOptions($input.attr('id'))).hide();
        var width = $input.css('width');

    }

    function setupContainer(options) {
        var container = document.createElement("div");
        $container = $(container);
        $container.attr('id', elm_id + '_container');
        $container.addClass(options.containerClass);

        return $container;
    }

    function setupInput(options) {
        var input = document.createElement("input");
        var $input = $(input);
        $input.attr("id", elm_id + "_input");
        $input.attr("type", "text");
        $input.addClass(options.inputClass);
        $input.attr("autocomplete", "off");
        $input.attr("readonly", "readonly");
        $input.attr("tabIndex", $select.attr("tabindex")); // "I" capital is important for ie

        return $input;
    }

    function moveSelect(step) {
        var lis = $("li", $container);
        if (!lis || lis.length == 0) return false;
        active += step;
        //loop through list
        if (active < 0) {
            active = lis.size();
        } else if (active > lis.size()) {
            active = 0;
        }
        scroll(lis, active);
        lis.removeClass(opt.hoverClass);

        $(lis[active]).addClass(opt.hoverClass);
    }

    function scroll(list, active) {
        var el = $(list[active]).get(0);
        var list = $container.get(0);

        if (el.offsetTop + el.offsetHeight > list.scrollTop + list.clientHeight) {
            list.scrollTop = el.offsetTop + el.offsetHeight - list.clientHeight;
        } else if (el.offsetTop < list.scrollTop) {
            list.scrollTop = el.offsetTop;
        }
    }

    function setCurrent() {
        var li = $("li." + opt.currentClass, $container).get(0);
        var ar = ('' + li.id).split('_');
        var el = ar[ar.length - 1];
        $select.val(el);
        $input.val($(li).html());
        return true;
    }

    // select value
    function getCurrentSelected() {
        return $select.val();
    }

    // input value
    function getCurrentValue() {
        return $input.val();
    }

    function getSelectOptions(parentid) {
        var select_options = new Array();
        var ul = document.createElement('ul');
   
                $select.children('option').each(function () {
                    var li = document.createElement('li');
                    li.setAttribute('id', parentid + '_' + $(this).val());
                    li.innerHTML = $(this).html();
                    if ($(this).is(':selected')) {
                        $input.val($(this).html());
                        $(li).addClass(opt.currentClass);
                    }
                    ul.appendChild(li);
                    $(li)
        			.mouseover(function (event) {
        			    hasfocus = 1;
        			    if (opt.debug) console.log('over on : ' + this.id);
        			    jQuery(event.target, $container).addClass(opt.hoverClass);
        			})
        			.mouseout(function (event) {
        			    hasfocus = -1;
        			    if (opt.debug) console.log('out on : ' + this.id);
        			    jQuery(event.target, $container).removeClass(opt.hoverClass);
        			})
        			.click(function (event) {
        			    var fl = $('li.' + opt.hoverClass, $container).get(0);
        			    if (opt.debug) console.log('click on :' + this.id);
        			    $('#' + elm_id + '_container' + ' li.' + opt.currentClass).removeClass(opt.currentClass);
        			    $(this).addClass(opt.currentClass);
        			    setCurrent();
        			    //$select.change();
        			    $select.get(0).blur();
        			    hideMe();
        			});
                });
        return ul;
    }



};
jQuery.fn.extend({
    selectbox: function (options) {
        return this.each(function () {
            new jQuery.SelectBox(this, options);
        });
    }
});
