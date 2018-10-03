function format(txt, tiperror){
	try {
	    var txtObj = JSON.parse(txt);
        return JSON.stringify(txtObj, null, 5);
    }catch (e){
	    if (tiperror){
            alert("格式化异常，请检查json格式是否有误" + e);
        }
        return txt;
	}
}

/**
 * 待删除，已经被替换
 * @param txt
 * @param tiperror
 * @param compress
 * @returns {string}
 */
function format_delete(txt, tiperror, compress/*是否为压缩模式*/) {/* 格式化JSON源码(对象转换为JSON文本) */
	var indentChar = '    ';
	if (/^\s*$/.test(txt)) {
		if (tiperror)
			alert('数据为空,无法格式化! ');
		return;
	}
	// 替换\r\n 换行
	txt=txt.replace(/\\r/g,"CRAPAPI_R"); 
    txt=txt.replace(/\\n/g,"CRAPAPI_N"); 
    txt=txt.replace(/\\t/g,"CRAPAPI_T"); 
	var data;
	try {
		data=$.parseJSON(txt);
	} catch (e) {
		if (tiperror)
			alert('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');
		return;
	}
	;
	var draw = [], last = false, This = this, line = compress ? '' : '\n', nodeCount = 0, maxDepth = 0;

	var notify = function(name, value, isLast, indent/*缩进*/, formObj) {
		nodeCount++;/*节点计数*/
		for (var i = 0, tab = ''; i < indent; i++)
			tab += indentChar;/* 缩进HTML */
		tab = compress ? '' : tab;/*压缩模式忽略缩进*/
		maxDepth = ++indent;/*缩进递增并记录*/
		if (value && value.constructor == Array) {/*处理数组*/
			draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line);/*缩进'[' 然后换行*/
			for (var i = 0; i < value.length; i++)
				notify(i, value[i], i == value.length - 1, indent, false);
			draw.push(tab + ']' + (isLast ? line : (',' + line)));/*缩进']'换行,若非尾元素则添加逗号*/
		} else if (value && typeof value == 'object') {/*处理对象*/
			draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);/*缩进'{' 然后换行*/
			var len = 0, i = 0;
			for ( var key in value)
				len++;
			for ( var key in value)
				notify(key, value[key], ++i == len, indent, true);
			draw.push(tab + '}' + (isLast ? line : (',' + line)));/*缩进'}'换行,若非尾元素则添加逗号*/
		} else {
			if (typeof value == 'string') {
				value = value.replace(/\"/gm, '\\"');
				// 替换\r\n 换行
				value=value.replace(/CRAPAPI_R/g,"\\r"); 
  				value=value.replace(/CRAPAPI_N/g,"\\n"); 
  				value=value.replace(/CRAPAPI_T/g,"\\t"); 

				value = '"' + value + '"';
			}
			draw.push(tab + (formObj ? ('"' + name + '":') : '') + value
					+ (isLast ? '' : ',') + line);
		}
		;
	};
	var isLast = true, indent = 0;
	notify('', data, isLast, indent, false);
	return draw.join('');
}

function jsonToDiv(txt) {/* 格式化JSON源码(对象转换为JSON文本) */
	var indentChar = '-';
	if (/^\s*$/.test(txt)) {
		alert('数据为空,无法格式化! ');
		return "";
	}
	try {
		var data = eval('(' + txt + ')');
	} catch (e) {
		alert('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');
		return "";
	}

	var draw = [], line = ',', nodeCount = 0;
    var paramSeparator = "->";
	var notify = function(parentName, name, value, indent) {
		nodeCount++;/*节点计数*/
		
		for (var i = 0, tab = ''; i < indent; i++) {
            tab += indentChar;
            /* 缩进HTML */
        }
		indent ++;
		if (value && value.constructor == Array) {/*处理数组*/
			if( value.length > 0 ){
				// 判断数组类型
				var type = "";
				if( value[0] && typeof value[0] == 'object'){
					type = 'object';
				}else if (typeof value == 'string') {
					type= 'string';
				}else if (typeof value == 'number') {
					type = 'number';
				}else if (typeof value == 'boolean') {
					type = 'boolean';
				}

				if( name != ''){
                    if (parentName && parentName != ''){
                        parentName = parentName + paramSeparator + name;
                    } else {
                        parentName = name;
                    }
					draw.push('{"deep":"'+tab.length+'","name":"'+ parentName +'","remark":"","type":"array['+type+']","necessary":"true"}' + line);
				}else{
					indent = indent -1;
				}
				
				// 数组只需要记录第一个就行
				notify(parentName, "", value[0], indent);
			}
			
		} else if (value && typeof value == 'object') {/*处理对象*/
			// 数组中的元素，没有名称
			if( name != ''){
                if (parentName && parentName != ''){
                    parentName = parentName + paramSeparator + name;
                } else {
                    parentName = name;
                }
				// 将对象名放入队列
				draw.push('{"deep":"'+tab.length+'","name":"'+ parentName +'","remark":"","type":"object","necessary":"true"}' + line);
			}else{
				indent = indent -1;// 名称为空，无需缩进
			}

			for ( var key in value) {
                notify(parentName, key, value[key], indent);
            }
		} else {
			var type;
			if (typeof value == 'string') {
				value = value.replace(/\"/gm, '\\"');
				value = '"' + value + '"';
				type = 'string';
			}else if (typeof value == 'number') {
				type = 'number';
			}else if (typeof value == 'boolean') {
				type = 'boolean';
			}

			if(name != ""){// 数组中的字符等没有名称，基础数组不需要记录
                if (parentName && parentName != ''){
                    parentName = parentName + paramSeparator + name;
                } else {
                    parentName = name;
                }
				draw.push('{"deep":"'+tab.length+'","name":"'+parentName+'","remark":"","type":"'+type+'","necessary":"true"}' + line);
			}
		}
	};
	
	var indent = 0;
	notify('', '', data, indent);
	var result = draw.join('');
	if(result.length > 0){
		result = result.substring(0,result.length-1);
	}
	return "["+result+"]";
}
