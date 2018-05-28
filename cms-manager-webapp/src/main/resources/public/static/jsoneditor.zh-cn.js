JSONEditor.defaults.default_language = 'cn';
JSONEditor.defaults.language = JSONEditor.defaults.default_language;
JSONEditor.defaults.languages.cn={
    error_additionalItems: "不能添加一个属性向数组中"||"No additional items allowed in this array",
    error_additional_properties: "不能添加书序,但是可以设置属性{{0}}!"||"No additional properties allowed, but property {{0}} is set",
    error_anyOf: "至少提供一个验证框架"||"Value must validate against at least one of the provided schemas",
    error_dependency:"必须有属性{{0}}"|| "Must have property {{0}}",
    error_disallow: "值必须是类型 {{0}}"||"Value must not be of type {{0}}",
    error_disallow_union: "不是一有效的类型"||"Value must not be one of the provided disallowed types",
    error_enum: "值必须是一个给定枚举值"||"Value must be one of the enumerated values",
    error_maxItems: "选项不能大于{{0}}个"||"Value must have at most {{0}} items",
    error_maxLength: "长度不能超过{{0}}"||"Value must be at most {{0}} characters long",
    error_maxProperties: "对象最多能有{{0}}个属性"||"Object must have at most {{0}} properties",
    error_maximum_excl: "不能大于{{0}}"||"Value must be less than {{0}}",
    error_maximum_incl: "不能大于{{0}}",
    error_minItems: "选项不能少于{{0}}个"||"Value must have at least {{0}} items",
    error_minLength: "不能少于{{0}}个字符"||"Value must be at least {{0}} characters long",
    error_minProperties: "对象属性不能少于{{0}}哥"||"Object must have at least {{0}} properties",
    error_minimum_excl: "不能小于{{0}}"||"Value must be greater than {{0}}",
    error_minimum_incl: "不能小于{{0}}"||"Value must be at least {{0}}",
    error_multipleOf: "{{0}} 最少选择一个"||"Value must be a multiple of {{0}}",
    error_not: "没有一个验证器"||"Value must not validate against the provided schema",
    error_notempty: "不能为空"||"Value required",
    error_notset: "属性不能为空"||"Property must be set",
    error_oneOf: "验证不通过 {{0}}"||"Value must validate against exactly one of the provided schemas. It currently validates against {{0}} of the schemas.",
    error_pattern: "验证不通过"||"Value must match the provided pattern",
    error_required: "{{0}}不能为空"||"Object is missing the required property '{{0}}'",
    error_type: "必须是{{0}}类型"||"Value must be of type {{0}}",
    error_type_union: "不能重复"||"Value must be one of the provided types",
    error_uniqueItems: "含有重复选项"||"Array must have unique items"
};

JSONEditor.prototype.setValue=function(v,i){

    if(!this.ready) throw "JSON Editor not ready yet.  Listen for 'ready' event before setting the value";
    this.root.setValue(v,i);
    return this;
}

JSONEditor.defaults.editors.img = JSONEditor.AbstractEditor.extend({
    getNumColumns: function() {
        return 4;
    },
    build: function() {

        if(!window.jQuery){
            throw "此功能需要 jquery 支持!"
        }

        if(!window.FileReader) {
            throw "该浏览器不支持此功能,请升级到最新的浏览器";
        }

        var self = this;
        this.title = this.header = this.label = this.theme.getFormInputLabel(this.getTitle());
        this.input = this.theme.getFormInputField('text');
        this.preview = this.theme.getFormInputDescription(this.schema.description);
        this.control = this.theme.getFormControl(this.label, this.input, this.preview);
        this.container.appendChild(this.control);


        this.sbutton=this.theme.getFormInputLabel("选择文件")
        this.uploader = this.theme.getFormInputField('file');
        this.container.appendChild(this.uploader);
        this.uploader.addEventListener('change',function(e) {
            e.preventDefault();
            e.stopPropagation();

            if(this.files && this.files.length) {
                var fr = new FileReader();
                fr.onload = function(evt) {

                    var base64Data = evt.target.result;
                    $.post("upload/img",{base64Data:base64Data},function(r){

                        self.value = r.url;
                        self.input.value= self.uploader.value;
                        self.onChange(true);
                        self.refreshPreview(r);
                    },'json');
                    fr = null;
                };
                fr.readAsDataURL(this.files[0]);
            }
        });

        var id=new Date().getTime();
        $(this.label).css("display","block")
        $(this.preview).css("clear","both");
        $(this.sbutton).attr("for",id).addClass("btn btn-info").css("float","right").css("padding","5px 12px");
        $(this.control).find("input").after(this.sbutton);
        $(this.uploader).attr("id",id).hide();
        window.setTimeout(function(){
            $(self.input).width( $(self.input).width()-$(self.sbutton).width()-10-(12+5)*2).prop("readonly",true).css("float","left");
        },0);


    },
    refreshPreview: function(r) {

        this.preview.innerHTML = '';

        if( !r && !this.value) return;


        // {size: 3023, mime: "image/png", url: "https://img.2boss.cn/resource/img/9c318cbe66239105fbce2e3b00a1a475.jpg"}
        if(r.size > 0){
            if(this.last_preview === this.value) return;
            this.last_preview = this.value;
            if(r["url"] ){
                this.preview.innerHTML += '<strong>类型:</strong> '+ r["mime"]+', <strong>大小:</strong> '+ (r["size"]/1024).toFixed(2)+' KB<br/>';
            }else{
                this.preview.innerHTML += '<br/>';
            }
            var img = document.createElement('img');
            img.style.maxWidth = '100%';
            img.style.maxHeight = '100px';
            img.src = this.value;
            this.preview.appendChild(img);
        }else{
            this.preview.innerHTML = '<em>'+ ("上传失败")+'</em>';
        }
    },
    enable: function() {
        if(this.uploader) this.uploader.disabled = false;
        this._super();
    },
    disable: function() {
        if(this.uploader) this.uploader.disabled = true;
        this._super();
    },
    setValue: function(val) {
        if(this.value !== val) {
            this.value = val;
            this.input.value = this.value;
            this.refreshPreview();
            this.onChange();
        }
    },
    destroy: function() {
        if(this.preview && this.preview.parentNode) this.preview.parentNode.removeChild(this.preview);
        if(this.title && this.title.parentNode) this.title.parentNode.removeChild(this.title);
        if(this.input && this.input.parentNode) this.input.parentNode.removeChild(this.input);
        if(this.uploader && this.uploader.parentNode) this.uploader.parentNode.removeChild(this.uploader);
        if(this.sbutton && this.sbutton.parentNode) this.sbutton.parentNode.removeChild(this.sbutton);

        this._super();
    }
});






//JSONEditor.defaults.languages.en = {
//    /**
//     * When a property is not set
//     */
//    error_notset: "Property must be set",
//    /**
//     * When a string must not be empty
//     */
//    error_notempty: "Value required",
//    /**
//     * When a value is not one of the enumerated values
//     */
//    error_enum: "Value must be one of the enumerated values",
//    /**
//     * When a value doesn't validate any schema of a 'anyOf' combination
//     */
//    error_anyOf: "Value must validate against at least one of the provided schemas",
//    /**
//     * When a value doesn't validate
//     * @variables This key takes one variable: The number of schemas the value does not validate
//     */
//    error_oneOf: 'Value must validate against exactly one of the provided schemas. It currently validates against {{0}} of the schemas.',
//    /**
//     * When a value does not validate a 'not' schema
//     */
//    error_not: "Value must not validate against the provided schema",
//    /**
//     * When a value does not match any of the provided types
//     */
//    error_type_union: "Value must be one of the provided types",
//    /**
//     * When a value does not match the given type
//     * @variables This key takes one variable: The type the value should be of
//     */
//    error_type: "Value must be of type {{0}}",
//    /**
//     *  When the value validates one of the disallowed types
//     */
//    error_disallow_union: "Value must not be one of the provided disallowed types",
//    /**
//     *  When the value validates a disallowed type
//     * @variables This key takes one variable: The type the value should not be of
//     */
//    error_disallow: "Value must not be of type {{0}}",
//    /**
//     * When a value is not a multiple of or divisible by a given number
//     * @variables This key takes one variable: The number mentioned above
//     */
//    error_multipleOf: "Value must be a multiple of {{0}}",
//    /**
//     * When a value is greater than it's supposed to be (exclusive)
//     * @variables This key takes one variable: The maximum
//     */
//    error_maximum_excl: "Value must be less than {{0}}",
//    /**
//     * When a value is greater than it's supposed to be (inclusive
//     * @variables This key takes one variable: The maximum
//     */
//    error_maximum_incl: "Value must at most {{0}}",
//    /**
//     * When a value is lesser than it's supposed to be (exclusive)
//     * @variables This key takes one variable: The minimum
//     */
//    error_minimum_excl: "Value must be greater than {{0}}",
//    /**
//     * When a value is lesser than it's supposed to be (inclusive)
//     * @variables This key takes one variable: The minimum
//     */
//    error_minimum_incl: "Value must be at least {{0}}",
//    /**
//     * When a value have too many characters
//     * @variables This key takes one variable: The maximum character count
//     */
//    error_maxLength: "Value must be at most {{0}} characters long",
//    /**
//     * When a value does not have enough characters
//     * @variables This key takes one variable: The minimum character count
//     */
//    error_minLength: "Value must be at least {{0}} characters long",
//    /**
//     * When a value does not match a given pattern
//     */
//    error_pattern: "Value must match the provided pattern",
//    /**
//     * When an array has additional items whereas it is not supposed to
//     */
//    error_additionalItems: "No additional items allowed in this array",
//    /**
//     * When there are to many items in an array
//     * @variables This key takes one variable: The maximum item count
//     */
//    error_maxItems: "Value must have at most {{0}} items",
//    /**
//     * When there are not enough items in an array
//     * @variables This key takes one variable: The minimum item count
//     */
//    error_minItems: "Value must have at least {{0}} items",
//    /**
//     * When an array is supposed to have unique items but has duplicates
//     */
//    error_uniqueItems: "Array must have unique items",
//    /**
//     * When there are too many properties in an object
//     * @variables This key takes one variable: The maximum property count
//     */
//    error_maxProperties: "Object must have at most {{0}} properties",
//    /**
//     * When there are not enough properties in an object
//     * @variables This key takes one variable: The minimum property count
//     */
//    error_minProperties: "Object must have at least {{0}} properties",
//    /**
//     * When a required property is not defined
//     * @variables This key takes one variable: The name of the missing property
//     */
//    error_required: "Object is missing the required property '{{0}}'",
//    /**
//     * When there is an additional property is set whereas there should be none
//     * @variables This key takes one variable: The name of the additional property
//     */
//    error_additional_properties: "No additional properties allowed, but property {{0}} is set",
//    /**
//     * When a dependency is not resolved
//     * @variables This key takes one variable: The name of the missing property for the dependency
//     */
//    error_dependency: "Must have property {{0}}"
//};
